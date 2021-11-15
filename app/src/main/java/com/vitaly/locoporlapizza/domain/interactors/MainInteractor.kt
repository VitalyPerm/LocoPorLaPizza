package com.vitaly.locoporlapizza.domain.interactors

import com.vitaly.locoporlapizza.data.RepositoryImpl
import com.vitaly.locoporlapizza.data.network.PizzaResponse
import com.vitaly.locoporlapizza.domain.PizzaEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MainInteractor @Inject constructor(private val repository: RepositoryImpl) {

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