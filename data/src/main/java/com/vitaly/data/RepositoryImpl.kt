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
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val pizzaApi: PizzaApi,
    private val pizzaDao: PizzaDao,
) : PizzaRepository {
    override fun getAllFromServer(): Single<List<Pizza>> {
        return pizzaApi.getAll().map { mapResponseToPizza(it) }
    }

    override fun sendOrder(pizzas: List<PizzaOrder>) {
        pizzaApi.sendOrder(mapOrder(pizzas)).observeOn(Schedulers.io()).subscribe({
            Log.d(TAG, "Order sent $pizzas")
        }, { it.printStackTrace() })
    }

    override fun getAllFromDb(): Observable<List<Pizza>> {
        return pizzaDao.getAll().map { mapEntityToPizza(it) }
    }

    override fun getPizzaById(id: Int): Single<Pizza> {
        return pizzaDao.getPizzaById(id).map { it.toPizza() }
    }

    override fun insert(pizza: Pizza) {
        pizzaDao.insert(mapSinglePizza(pizza)).subscribeOn(Schedulers.io())
            .subscribe({
                Log.d(TAG, "Pizza inserted")
            }, { it.printStackTrace() })
    }

    override fun clear() {
        pizzaDao.clear().subscribeOn(Schedulers.io())
            .subscribe({
                Log.d(TAG, "cart cleared")
            }, { it.printStackTrace() })
    }

    override fun update(pizza: Pizza) {
        pizzaDao.update(mapSinglePizza(pizza)).subscribeOn(Schedulers.io())
            .subscribe({
                Log.d(TAG, "Pizza added")
            }, { it.printStackTrace() })
    }

    companion object {
        const val TAG = "REPOSITORY_LOG"
    }
}