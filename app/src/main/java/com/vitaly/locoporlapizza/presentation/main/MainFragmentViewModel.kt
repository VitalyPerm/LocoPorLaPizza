package com.vitaly.locoporlapizza.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vitaly.locoporlapizza.data.PizzaDao
import com.vitaly.locoporlapizza.data.PizzaDataBase
import com.vitaly.locoporlapizza.data.PizzaEntity
import com.vitaly.locoporlapizza.domain.PizzaResponse
import com.vitaly.locoporlapizza.domain.PizzaService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var pizzaDao: PizzaDao

    fun getPizzaList(): Single<List<PizzaResponse>> {
        return PizzaService().providePizzaApi().getAll()
    }

    fun initDatabase(): Observable<List<PizzaEntity>> {
        pizzaDao = PizzaDataBase.getDatabaseInstance(getApplication()).getPizzaDao()
        return pizzaDao.getAll()
    }
}