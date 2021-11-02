package com.vitaly.locoporlapizza.presentation.main

import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.vitaly.locoporlapizza.data.db.PizzaDao
import com.vitaly.locoporlapizza.data.db.PizzaEntity
import com.vitaly.locoporlapizza.data.network.PizzaApi
import com.vitaly.locoporlapizza.data.network.PizzaResponse
import com.vitaly.locoporlapizza.utils.pizzaMapper
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class MainFragmentViewModel @Inject constructor(
    val pizzaApi: PizzaApi,
    val pizzaDao: PizzaDao, val progressBar: CircularProgressDrawable
) : ViewModel() {


    fun getPizzaList(): Single<List<PizzaResponse>> {
        return pizzaApi.getAll()
    }

    fun initDatabase(): Observable<List<PizzaEntity>> {
        return pizzaDao.getAll()
    }

    fun insert(pizza: PizzaResponse): Completable {
        return pizzaDao.insert(pizzaMapper(pizza))
    }
}