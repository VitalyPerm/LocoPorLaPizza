package com.vitaly.domain.interactors

import com.vitaly.domain.models.PizzaEntity
import com.vitaly.domain.models.PizzaOrderEntity
import com.vitaly.domain.PizzaRepository
import io.reactivex.rxjava3.core.Observable

class CartInteractor (private val repository: PizzaRepository) {

    fun getAllFromDb(): Observable<List<PizzaEntity>> {
        return repository.getAllFromDb()
    }

    fun clear() {
        repository.clear()
    }

    fun addPizza(pizza: PizzaEntity) {
        repository.update(pizza)
    }

    fun sendOrder(pizzas: List<PizzaOrderEntity>) {
        repository.sendOrder(pizzas)
    }
}