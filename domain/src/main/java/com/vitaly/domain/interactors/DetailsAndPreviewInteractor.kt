package com.vitaly.domain.interactors


import com.vitaly.domain.PizzaRepository
import com.vitaly.domain.models.Pizza
import io.reactivex.rxjava3.core.Single

class DetailsAndPreviewInteractor (private val repository: PizzaRepository) {
    fun addPizza(pizza: Pizza) {
        repository.update(pizza)
    }

    fun getPizzaById(id: Int): Single<Pizza> {
        return repository.getPizzaById(id)
    }
}