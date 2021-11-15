package com.vitaly.domain.interactors


import com.vitaly.domain.models.PizzaEntity
import com.vitaly.domain.PizzaRepository
import com.vitaly.domain.models.PizzaResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


class MainInteractor (private val repository: PizzaRepository) {

    fun getAllFromServer(): Single<List<PizzaResponse>> {
        return repository.getAllFromServer()
    }

     fun getAllFromDb(): Observable<List<PizzaEntity>> {
        return repository.getAllFromDb()
    }

    fun insert(pizzaEntity: PizzaEntity){
        repository.insert(pizzaEntity)
    }
}