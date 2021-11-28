package com.vitaly.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.vitaly.domain.interactors.MainInteractor
import com.vitaly.domain.models.Pizza
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val interactor: MainInteractor, val progressBar: CircularProgressDrawable
) : ViewModel() {
    var allDataFromDb = MutableStateFlow<List<Pizza>>(emptyList())


    fun initDatabase() {
        getPizzaList()
        viewModelScope.launch {
            interactor.getAllFromDb().collect {
                allDataFromDb.emit(it)
            }

            }
        }

    private fun getPizzaList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val list = interactor.getAllFromServer()
                withContext(Dispatchers.Main){
                    for (i in list){
                        insert(i)
                    }
                }
            }
        }
    }

    private fun insert(pizza: Pizza) {
        viewModelScope.launch {
            interactor.insert(pizza)
        }
    }
}