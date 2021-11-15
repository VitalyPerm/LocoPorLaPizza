package com.vitaly.locoporlapizza.domain.interactors

import com.vitaly.locoporlapizza.data.RepositoryImpl
import com.vitaly.locoporlapizza.domain.PizzaEntity
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DetailsAndPreviewInteractor @Inject constructor(private val repository: RepositoryImpl) {
    fun addPizza(pizza: PizzaEntity) {
        repository.update(pizza)
    }

    fun getPizzaById(id: Int): Single<PizzaEntity> {
        return repository.getPizzaById(id)
    }
}