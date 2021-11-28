package com.vitaly.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitaly.data.network.PizzaOrderEntity
import com.vitaly.data.db.PizzaEntity
import com.vitaly.domain.interactors.CartInteractor
import com.vitaly.domain.models.Pizza
import com.vitaly.domain.models.PizzaOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    private val interactor: CartInteractor,
) : ViewModel() {
    var pizzaListToSend = mutableListOf<PizzaOrder>()
    var allDataFromDb = MutableStateFlow<List<Pizza>>(emptyList())

    fun initDatabase() {
        viewModelScope.launch {
            interactor.getAllFromDb().collect {
                allDataFromDb.emit(it)
            }
        }

    }

    fun clear() {
        interactor.clear()
    }

    fun update(pizza: Pizza) {
        interactor.addPizza(pizza)
    }

    fun sendOrder() {
        viewModelScope.launch {
            interactor.sendOrder(pizzaListToSend)
        }
    }
}