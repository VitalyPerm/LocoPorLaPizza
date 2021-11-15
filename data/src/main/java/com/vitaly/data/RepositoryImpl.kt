package com.vitaly.data

import android.util.Log
import com.vitaly.data.db.PizzaDao
import com.vitaly.data.network.PizzaApi
import com.vitaly.domain.PizzaRepository
import com.vitaly.domain.models.PizzaEntity
import com.vitaly.domain.models.PizzaOrderEntity
import com.vitaly.domain.models.PizzaResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val pizzaApi: PizzaApi,
    private val pizzaDao: PizzaDao,
) : PizzaRepository {
    override fun getAllFromServer(): Single<List<PizzaResponse>> {
        return pizzaApi.getAll()
    }

    override fun sendOrder(pizzas: List<PizzaOrderEntity>) {
        pizzaApi.sendOrder(pizzas).observeOn(Schedulers.io()).subscribe({
            Log.d(TAG, "Order sent $pizzas")
        }, { it.printStackTrace() })
    }

    override fun getAllFromDb(): Observable<List<PizzaEntity>> {
        return pizzaDao.getAll()
    }

    override fun getPizzaById(id: Int): Single<PizzaEntity> {
        return pizzaDao.getPizzaById(id)
    }

    override fun insert(pizza: PizzaEntity) {
        pizzaDao.insert(pizza).subscribeOn(Schedulers.io())
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

    override fun update(pizza: PizzaEntity) {
        pizzaDao.update(pizza).subscribeOn(Schedulers.io())
            .subscribe({
                Log.d(TAG, "Pizza added")
            }, { it.printStackTrace() })
    }

    companion object {
        const val TAG = "REPOSITORY_LOG"
    }
}