package com.vitaly.presentation.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vitaly.domain.models.Pizza
import com.vitaly.domain.models.PizzaOrder
import com.vitaly.presentation.R
import com.vitaly.presentation.databinding.FragmentCartBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CartFragment : Fragment(R.layout.fragment_cart) {
    private val binding by viewBinding(FragmentCartBinding::bind)
    private val viewModel: CartFragmentViewModel by hiltNavGraphViewModels(R.id.nav_graph)
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
        lifecycleScope.launch {
            viewModel.allDataFromDb.collect {
                val filteredList = it.filter { it.quantity > 0 }
                adapter.setList(filteredList)
                getPriceOfAllPizzas(filteredList)
                for (i in filteredList) {
                    viewModel.pizzaListToSend.add(PizzaOrder(i.id, i.quantity))
                }
            }
        }
        binding.buttonClear.setOnClickListener { viewModel.clear() }
        binding.checkout.setOnClickListener {
            viewModel.sendOrder()
            viewModel.clear()
            findNavController().navigate(R.id.action_cartFragment_to_endFragment)
        }
    }

    private fun getPriceOfAllPizzas(list: List<Pizza>) {
        var price = 0
        for (i in list.indices) {
            price += (list[i].price.toInt() * list[i].quantity)
        }
        binding.tvPrice.text = getString(R.string.price, price)
    }
}