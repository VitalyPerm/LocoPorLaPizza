package com.vitaly.domain.interactors

import com.vitaly.domain.PizzaRepository
import com.vitaly.domain.models.Pizza
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow


class MainInteractor (private val repository: PizzaRepository) {

   suspend fun getAllFromServer(): List<Pizza> {
        return repository.getAllFromServer()
    }

     fun getAllFromDb(): Flow<List<Pizza>> {
        return repository.getAllFromDb()
    }

    suspend fun insert(pizzaEntity: Pizza){
        repository.insert(pizzaEntity)
    }
}