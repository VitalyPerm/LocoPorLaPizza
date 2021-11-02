package com.vitaly.locoporlapizza.presentation.cart

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.vitaly.locoporlapizza.R
import com.vitaly.locoporlapizza.data.db.PizzaEntity
import com.vitaly.locoporlapizza.databinding.FragmentCartBinding
import com.vitaly.locoporlapizza.presentation.BaseFragment
import com.vitaly.locoporlapizza.presentation.EndFragment
import com.vitaly.locoporlapizza.utils.prepareOrderEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: CartFragmentViewModel by viewModels { viewModelFactory }
    private lateinit var disposable: CompositeDisposable
    private lateinit var adapter: CartFragmentAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    private fun initialization() {
        disposable = CompositeDisposable()
        adapter = CartFragmentAdapter {
            disposable.add(
                viewModel.update(it).subscribeOn(Schedulers.io())
                    .subscribe({}, { it.printStackTrace() })
            )
        }
        recyclerView = binding.rv
        recyclerView.adapter = adapter
        disposable.add(
            viewModel.initDatabase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ pizzaList ->
                    val filteredList = pizzaList.filter { it.quantity > 0 }
                    adapter.setList(filteredList)
                    getPriceOfAllPizzas(filteredList)
                    for (i in filteredList) {
                        viewModel.pizzasList.add(prepareOrderEntity(i))
                    }
                }, { it.printStackTrace() })
        )

        binding.buttonClear.setOnClickListener {
            disposable.add(
                viewModel.clear().subscribeOn(Schedulers.io())
                    .subscribe({}, { it.printStackTrace() })
            )
        }
        binding.checkout.setOnClickListener {
            disposable.add(
                viewModel.sendOrder(viewModel.pizzasList).observeOn(Schedulers.io()).subscribe({
                    Log.d("Check_order_sent", "Order sent")
                }, { it.printStackTrace() })
            )
            disposable.add(
                viewModel.clear().subscribeOn(Schedulers.io())
                    .subscribe({}, { it.printStackTrace() })
            )
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