package com.vitaly.locoporlapizza.presentation.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.vitaly.locoporlapizza.R
import com.vitaly.locoporlapizza.databinding.FragmentCartBinding
import com.vitaly.locoporlapizza.domain.PizzaEntity
import com.vitaly.locoporlapizza.presentation.BaseFragment
import com.vitaly.locoporlapizza.presentation.EndFragment
import com.vitaly.locoporlapizza.utils.prepareOrderEntity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: CartFragmentViewModel by viewModels { viewModelFactory }
    private val disposable = CompositeDisposable()
    private lateinit var adapter: CartFragmentAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    private fun initialization() {
        adapter = CartFragmentAdapter { viewModel.update(it) }
        recyclerView = binding.rv
        recyclerView.adapter = adapter
        viewModel.initDatabase()
        disposable.add(
            viewModel.pizzasListFromDb.subscribe { pizzaList ->
                val filteredList = pizzaList.filter { it.quantity > 0 }
                adapter.setList(filteredList)
                getPriceOfAllPizzas(filteredList)
                for(i in filteredList){
                    viewModel.pizzaListToSend.add(prepareOrderEntity(i))
                }

            }
        )
        binding.buttonClear.setOnClickListener {
            viewModel.clear()

        }
        binding.checkout.setOnClickListener {
            viewModel.sendOrder()
                viewModel.clear()
            replaceFragment(EndFragment())
        }
    }

    private fun getPriceOfAllPizzas(list: List<PizzaEntity>) {
        var price = 0
        for (i in list.indices) {
            price += (list[i].price.toInt() * list[i].quantity)
        }
        binding.tvPrice.text = getString(R.string.price, price)
    }

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
    }
}