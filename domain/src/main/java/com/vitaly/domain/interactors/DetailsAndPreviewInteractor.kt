package com.vitaly.domain.interactors


import android.util.Log
import com.vitaly.domain.PizzaRepository
import com.vitaly.domain.models.Pizza
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class DetailsAndPreviewInteractor(private val repository: PizzaRepository) {
     fun addPizza(pizza: Pizza) {
        repository.update(pizza)
        Log.d("CHECK___", "${pizza}")
    }

    suspend fun getPizzaById(id: Int): Pizza  = withContext(Dispatchers.IO){
        repository.getPizzaById(id)
    }

}