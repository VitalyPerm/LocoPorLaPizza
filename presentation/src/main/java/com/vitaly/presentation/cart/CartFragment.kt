package com.vitaly.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.vitaly.domain.models.Pizza
import com.vitaly.domain.models.PizzaOrder
import com.vitaly.presentation.R
import com.vitaly.presentation.databinding.FragmentCartBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartFragmentViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private val disposable = CompositeDisposable()
    private lateinit var adapter: CartFragmentAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun initialization() {
        adapter = CartFragmentAdapter { viewModel.update(it) }
        recyclerView = binding.rv
        recyclerView.adapter = adapter
        viewModel.initDatabase()
        disposable.add(
            viewModel.pizzasListFromDb.subscribe { pizzaList ->
                val filteredList = pizzaList.filter { it.quantity > 0 }
                if (filteredList.isEmpty()) {
                    findNavController().navigate(R.id.action_cartFragment_to_mainFragment)
                }
                adapter.setList(filteredList)
                getPriceOfAllPizzas(filteredList)
                for (i in filteredList) {
                    viewModel.pizzaListToSend.add(PizzaOrder(i.id, i.quantity))
                }
            }
        )
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

    override fun onDestroyView() {
        _binding = null
        disposable.clear()
        super.onDestroyView()
    }
}