package com.vitaly.locoporlapizza.domain.interactors

import com.vitaly.locoporlapizza.data.RepositoryImpl
import com.vitaly.locoporlapizza.data.network.PizzaOrderEntity
import com.vitaly.locoporlapizza.domain.PizzaEntity
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class CartInteractor @Inject constructor(private val repository: RepositoryImpl) {

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