package com.vitaly.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.vitaly.domain.interactors.DetailsAndPreviewInteractor
import com.vitaly.domain.models.Pizza
import com.vitaly.presentation.utils.editPizzaQuantity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsFragmentViewModel @Inject constructor(
    val progressBar: CircularProgressDrawable,
    private val interactor: DetailsAndPreviewInteractor
) : ViewModel() {
    private var _pizza = MutableSharedFlow<Pizza>()
    val pizza = _pizza.asSharedFlow()
    private lateinit var pizza1: Pizza

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