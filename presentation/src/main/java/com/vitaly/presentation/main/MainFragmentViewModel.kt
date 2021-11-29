package com.vitaly.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.vitaly.domain.interactors.MainInteractor
import com.vitaly.domain.models.Pizza
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val interactor: MainInteractor, val progressBar: CircularProgressDrawable
) : ViewModel() {
    private var job: Job? = null
    private val _pizzaList = MutableStateFlow<List<Pizza>>(emptyList())
    val pizzaList = _pizzaList.asSharedFlow()

    fun initDatabase() {
        job = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val list = interactor.getAllFromServer()
                for (i in list) {
                    interactor.insert(i)
                }
                interactor.getAllFromDb().collect {
                    _pizzaList.emit(it)
                }
            }
        }
    }

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }
}