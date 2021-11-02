package com.vitaly.locoporlapizza.presentation.cart

import androidx.lifecycle.ViewModel
import com.vitaly.locoporlapizza.data.db.PizzaDao
import com.vitaly.locoporlapizza.data.db.PizzaEntity
import com.vitaly.locoporlapizza.data.network.PizzaApi
import com.vitaly.locoporlapizza.data.network.PizzaOrderEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class CartFragmentViewModel @Inject constructor(
    private val pizzaDao: PizzaDao,
    private val pizzaApi: PizzaApi
) : ViewModel() {

    var pizzasList = mutableListOf<PizzaOrderEntity>()

    fun initDatabase(): Observable<List<PizzaEntity>> {
        return pizzaDao.getAll()
    }

    fun clear(): Completable {
        return pizzaDao.clear()
    }

    fun update(pizza: PizzaEntity): Completable {
        return pizzaDao.update(pizza)
    }

    fun sendOrder(order: List<PizzaOrderEntity>): Completable {
        return pizzaApi.sendOrder(order)
    }
}