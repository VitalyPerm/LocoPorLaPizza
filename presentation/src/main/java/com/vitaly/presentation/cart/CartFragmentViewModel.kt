package com.vitaly.presentation.cart

import androidx.lifecycle.ViewModel
import com.vitaly.data.network.PizzaOrderEntity
import com.vitaly.data.db.PizzaEntity
import com.vitaly.domain.interactors.CartInteractor
import com.vitaly.domain.models.Pizza
import com.vitaly.domain.models.PizzaOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject
@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    private val interactor: CartInteractor,
) : ViewModel() {
    private val disposable = CompositeDisposable()
    val pizzasListFromDb: PublishSubject<List<Pizza>> = PublishSubject.create()
    var pizzaListToSend = mutableListOf<PizzaOrder>()

    fun initDatabase() {
        disposable.add(
            interactor.getAllFromDb().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { pizzasListFromDb.onNext(it) }
                .doOnComplete { pizzasListFromDb.onComplete() }
                .doOnError { it.printStackTrace() }
                .subscribe()
        )
    }

    fun clear() {
        interactor.clear()
    }

    fun update(pizza: Pizza) {
        interactor.addPizza(pizza)
    }

    fun sendOrder() {
        interactor.sendOrder(pizzaListToSend)
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}