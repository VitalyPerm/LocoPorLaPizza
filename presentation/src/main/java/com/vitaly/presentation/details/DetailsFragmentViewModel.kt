package com.vitaly.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.vitaly.domain.interactors.DetailsAndPreviewInteractor
import com.vitaly.domain.models.Pizza
import com.vitaly.presentation.utils.editPizzaQuantity
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsFragmentViewModel @Inject constructor(
    val progressBar: CircularProgressDrawable,
    private val interactor: DetailsAndPreviewInteractor
) : ViewModel() {
    private val disposable = CompositeDisposable()

    //   val selectedPizza: BehaviorSubject<Pizza> = BehaviorSubject.create()
     lateinit var selectedPizza: Pizza

    fun getPizzaById(id: Int): Pizza {
        viewModelScope.launch {
            interactor.getPizzaById(id).collect {
                selectedPizza = it
            }
        }
        return selectedPizza
    }

    fun addPizza(pizza: Pizza) {
        viewModelScope.launch {
            interactor.addPizza(editPizzaQuantity(pizza, true))
        }
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}