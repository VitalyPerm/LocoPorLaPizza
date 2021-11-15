package com.vitaly.presentation.main

import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.vitaly.domain.models.PizzaResponse
import com.vitaly.domain.models.PizzaEntity
import com.vitaly.domain.interactors.MainInteractor
import com.vitaly.presentation.utils.pizzaMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject


class MainFragmentViewModel @Inject constructor(
    private val interactor: MainInteractor, val progressBar: CircularProgressDrawable
) : ViewModel() {
    private val disposable = CompositeDisposable()
    val pizzasListFromDb: PublishSubject<List<PizzaEntity>> = PublishSubject.create()

    fun initDatabase() {
        getPizzaList()
        disposable.add(
            interactor.getAllFromDb().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { pizzasListFromDb.onNext(it) }
                .doOnComplete { pizzasListFromDb.onComplete() }
                .doOnError { it.printStackTrace() }
                .subscribe()
        )
    }

    private fun getPizzaList() {
        interactor.getAllFromServer().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    for (i in it) {
                        insert(i)
                    }
                }, { it.printStackTrace() }
            )
    }

    private fun insert(pizza: PizzaResponse) {
        interactor.insert(pizzaMapper(pizza))
    }
}