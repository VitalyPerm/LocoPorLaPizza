package com.vitaly.data

import android.util.Log
import com.vitaly.data.db.PizzaDao
import com.vitaly.data.network.PizzaApi
import com.vitaly.domain.PizzaRepository
import com.vitaly.domain.models.Pizza
import com.vitaly.domain.models.PizzaOrder
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val pizzaApi: PizzaApi,
    private val pizzaDao: PizzaDao,
) : PizzaRepository {
    override suspend fun getAllFromServer(): List<Pizza> {
        return mapResponseToPizza(pizzaApi.getAll())
    }

    override suspend fun sendOrder(pizzas: List<PizzaOrder>) {
        pizzaApi.sendOrder(mapOrder(pizzas))
    }

    override fun getAllFromDb(): Flow<List<Pizza>> {
        return pizzaDao.getAll().map { mapEntityToPizza(it) }
    }

    override fun getPizzaById(id: Int): Flow<Pizza> {
        return pizzaDao.getPizzaById(id).map { it.toPizza() }
    }

    override suspend fun insert(pizza: Pizza) {
        pizzaDao.insert(mapSinglePizza(pizza))
    }

    override suspend fun clear() {
        pizzaDao.clear()
    }

    override suspend fun update(pizza: Pizza) {
        pizzaDao.update(mapSinglePizza(pizza))
    }

    companion object {
        const val TAG = "REPOSITORY_LOG"
    }
}