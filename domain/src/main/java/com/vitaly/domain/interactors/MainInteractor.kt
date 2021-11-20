package com.vitaly.domain.interactors

import com.vitaly.domain.PizzaRepository
import com.vitaly.domain.models.Pizza
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


class MainInteractor (private val repository: PizzaRepository) {

    fun getAllFromServer(): Single<List<Pizza>> {
        return repository.getAllFromServer()
    }

     fun getAllFromDb(): Observable<List<Pizza>> {
        return repository.getAllFromDb()
    }

    fun insert(pizzaEntity: Pizza){
        repository.insert(pizzaEntity)
    }
}