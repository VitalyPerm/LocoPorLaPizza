package com.vitaly.locoporlapizza.presentation.preview

import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.vitaly.locoporlapizza.data.db.PizzaDao
import com.vitaly.locoporlapizza.domain.PizzaEntity
import com.vitaly.locoporlapizza.domain.interactors.DetailsAndPreviewInteractor
import com.vitaly.locoporlapizza.utils.addPizzaMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class PreviewFragmentViewModel @Inject constructor(
    private val interactor: DetailsAndPreviewInteractor,
    val progressBar: CircularProgressDrawable
) : ViewModel() {
    private val disposable = CompositeDisposable()
    val selectedPizza: BehaviorSubject<PizzaEntity> = BehaviorSubject.create()

    fun getPizzaById(id: Int) {
        disposable.add(
            interactor.getPizzaById(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        selectedPizza.onNext(it)
                    }, { it.printStackTrace() }
                )
        )
    }

    fun addPizza(pizza: PizzaEntity) {
        interactor.addPizza(addPizzaMapper(pizza))
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}