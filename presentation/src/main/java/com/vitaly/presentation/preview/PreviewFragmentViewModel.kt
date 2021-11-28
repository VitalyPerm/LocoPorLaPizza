package com.vitaly.presentation.preview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.vitaly.domain.interactors.DetailsAndPreviewInteractor
import com.vitaly.domain.models.Pizza
import com.vitaly.presentation.utils.editPizzaQuantity
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviewFragmentViewModel @Inject constructor(
    private val interactor: DetailsAndPreviewInteractor,
    val progressBar: CircularProgressDrawable
) : ViewModel() {
    private var _pizza = MutableSharedFlow<Pizza>()
    val pizza = _pizza.asSharedFlow()
    lateinit var pizza1: Pizza
    fun getPizzaById(id: Int) {
        viewModelScope.launch {
            interactor.getPizzaById(id).let {
                _pizza.emit(it)
                pizza1 = it
            }
        }
    }

    fun addPizza() {
        viewModelScope.launch {
            interactor.addPizza(editPizzaQuantity(pizza1, true))
        }
    }
}