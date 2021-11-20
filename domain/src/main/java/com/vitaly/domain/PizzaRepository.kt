package com.vitaly.domain

import com.vitaly.domain.models.Pizza
import com.vitaly.domain.models.PizzaOrder
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface PizzaRepository {
    fun getAllFromServer(): Single<List<Pizza>>
    fun sendOrder(pizzas: List<PizzaOrder>)
    fun getAllFromDb(): Observable<List<Pizza>>
    fun getPizzaById(id: Int): Single<Pizza>
    fun insert(pizza: Pizza)
    fun clear()
    fun update(pizza: Pizza)
}