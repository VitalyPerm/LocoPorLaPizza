package com.vitaly.domain.interactors

import com.vitaly.domain.PizzaRepository
import com.vitaly.domain.models.Pizza
import com.vitaly.domain.models.PizzaOrder
import io.reactivex.rxjava3.core.Observable

class CartInteractor (private val repository: PizzaRepository) {

    fun getAllFromDb(): Observable<List<Pizza>> {
        return repository.getAllFromDb()
    }

    fun clear() {
        repository.clear()
    }

    fun addPizza(pizza: Pizza) {
        repository.update(pizza)
    }

    fun sendOrder(pizzas: List<PizzaOrder>) {
        repository.sendOrder(pizzas)
    }
}