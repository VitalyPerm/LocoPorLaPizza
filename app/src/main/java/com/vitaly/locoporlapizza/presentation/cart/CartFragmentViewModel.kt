package com.vitaly.locoporlapizza.presentation.cart

import androidx.lifecycle.ViewModel
import com.vitaly.locoporlapizza.data.db.PizzaDao
import com.vitaly.locoporlapizza.domain.PizzaEntity
import com.vitaly.locoporlapizza.data.network.PizzaApi
import com.vitaly.locoporlapizza.data.network.PizzaOrderEntity
import com.vitaly.locoporlapizza.domain.interactors.CartInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class CartFragmentViewModel @Inject constructor(
    private val interactor: CartInteractor,
) : ViewModel() {
    private val disposable = CompositeDisposable()
    val pizzasListFromDb: PublishSubject<List<PizzaEntity>> = PublishSubject.create()
    var pizzaListToSend = mutableListOf<PizzaOrderEntity>()

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

    fun update(pizza: PizzaEntity) {
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