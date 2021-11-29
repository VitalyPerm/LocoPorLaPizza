package com.vitaly.domain.interactors


import com.vitaly.domain.PizzaRepository
import com.vitaly.domain.models.Pizza
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailsAndPreviewInteractor(private val repository: PizzaRepository) {
     fun addPizza(pizza: Pizza) {
        repository.update(pizza)
    }

    suspend fun getPizzaById(id: Int) = withContext(Dispatchers.IO){
        repository.getPizzaById(id)
    }
}