package com.vitaly.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.vitaly.domain.interactors.MainInteractor
import com.vitaly.domain.models.Pizza
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val interactor: MainInteractor, val progressBar: CircularProgressDrawable
) : ViewModel() {
    private val _pizzaList = MutableStateFlow<List<Pizza>>(emptyList())
    val pizzaList = _pizzaList.asSharedFlow()

    fun initDatabase() {
        getPizzaList()
        viewModelScope.launch {
            interactor.getAllFromDb().collect {
                _pizzaList.emit(it)
            }
        }
    }

    private fun getPizzaList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val list = interactor.getAllFromServer()
                withContext(Dispatchers.Main) {
                    for (i in list) {
                        insert(i)
                    }
                }
            }
        }
    }

    private fun insert(pizza: Pizza) {
        interactor.insert(pizza)
    }
}