package com.vitaly.locoporlapizza.presentation.details

import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.vitaly.locoporlapizza.data.db.PizzaDao
import com.vitaly.locoporlapizza.data.db.PizzaEntity
import com.vitaly.locoporlapizza.utils.addPizzaMapper
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DetailsFragmentViewModel @Inject constructor(
    val progressBar: CircularProgressDrawable,
    val pizzaDao: PizzaDao
) : ViewModel() {

    lateinit var selectedPizza: PizzaEntity


    fun getPizzaById(id: Int): Single<PizzaEntity> {
        return pizzaDao.getPizzaById(id)
    }

    fun addPizza(pizza: PizzaEntity): Completable {
        return pizzaDao.update(addPizzaMapper(pizza))
    }

    companion object {
        const val TAG = "DETAILS_FRAGMENT"
    }
}