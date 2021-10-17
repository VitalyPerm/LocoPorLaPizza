package com.vitaly.locoporlapizza.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vitaly.locoporlapizza.model.PizzaDatabase
import com.vitaly.locoporlapizza.model.PizzaEntity

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    var pizzaList: MutableLiveData<List<PizzaEntity>> = MutableLiveData()


    fun getAll() {
        pizzaList.value = PizzaDatabase.pizzaDao.getAll()
    }
}