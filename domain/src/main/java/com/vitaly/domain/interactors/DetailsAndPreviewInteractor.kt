package com.vitaly.domain.interactors


import com.vitaly.domain.PizzaRepository
import com.vitaly.domain.models.Pizza
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

class DetailsAndPreviewInteractor(private val repository: PizzaRepository) {
    suspend fun addPizza(pizza: Pizza) {
        repository.update(pizza)
    }

    suspend fun getPizzaById(id: Int): Flow<Pizza> {
        return repository.getPizzaById(id)
    }
}