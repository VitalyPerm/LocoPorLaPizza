package com.vitaly.locoporlapizza.presentation.preview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vitaly.locoporlapizza.data.PizzaDao
import com.vitaly.locoporlapizza.data.PizzaDataBase
import com.vitaly.locoporlapizza.data.PizzaEntity
import com.vitaly.locoporlapizza.domain.PizzaResponse
import com.vitaly.locoporlapizza.domain.PizzaService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class PreviewFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var pizzaDao: PizzaDao
    var selectedPizza: PizzaResponse? = null
    var images = mutableListOf<String>()

    fun getData(id: Int): Single<PizzaResponse> {
        return PizzaService().providePizzaApi().getPizzaById(id)
    }

    fun initDatabase() {
        pizzaDao = PizzaDataBase.getDatabaseInstance(getApplication()).getPizzaDao()
    }

    fun insert(pizza: PizzaEntity) {
        Completable.fromAction {
            pizzaDao.insert(pizza)
        }.subscribeOn(Schedulers.io())
            .subscribe()
    }
}