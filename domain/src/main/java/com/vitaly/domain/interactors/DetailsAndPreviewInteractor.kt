package com.vitaly.domain.interactors


import com.vitaly.domain.models.PizzaEntity
import com.vitaly.domain.PizzaRepository
import io.reactivex.rxjava3.core.Single

class DetailsAndPreviewInteractor (private val repository: PizzaRepository) {
    fun addPizza(pizza: PizzaEntity) {
        repository.update(pizza)
    }

    fun getPizzaById(id: Int): Single<PizzaEntity> {
        return repository.getPizzaById(id)
    }
}