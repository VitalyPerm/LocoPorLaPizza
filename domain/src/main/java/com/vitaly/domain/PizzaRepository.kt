package com.vitaly.domain

import com.vitaly.domain.models.Pizza
import com.vitaly.domain.models.PizzaOrder
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface PizzaRepository {
    suspend fun getAllFromServer(): List<Pizza>
    suspend fun sendOrder(pizzas: List<PizzaOrder>)
    fun getAllFromDb(): Flow<List<Pizza>>
    suspend fun getPizzaById(id: Int): Pizza
    fun insert(pizza: Pizza)
    fun clear()
    fun update(pizza: Pizza)
}