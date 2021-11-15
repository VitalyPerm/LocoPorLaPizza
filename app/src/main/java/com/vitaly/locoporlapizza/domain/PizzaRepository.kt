package com.vitaly.locoporlapizza.domain

import com.vitaly.locoporlapizza.data.network.PizzaOrderEntity
import com.vitaly.locoporlapizza.data.network.PizzaResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface PizzaRepository {
    fun getAllFromServer(): Single<List<PizzaResponse>>
    fun sendOrder(pizzas: List<PizzaOrderEntity>)
    fun getAllFromDb(): Observable<List<PizzaEntity>>
    fun getPizzaById(id: Int): Single<PizzaEntity>
    fun insert(pizza: PizzaEntity)
    fun clear()
    fun update(pizza: PizzaEntity)
}