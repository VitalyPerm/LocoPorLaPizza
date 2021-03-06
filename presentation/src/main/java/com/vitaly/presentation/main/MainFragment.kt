package com.vitaly.presentation.main

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.vitaly.domain.models.Pizza
import com.vitaly.presentation.BaseFragment
import com.vitaly.presentation.R
import com.vitaly.presentation.cart.CartFragment
import com.vitaly.presentation.databinding.FragmentMainBinding
import com.vitaly.presentation.details.DetailsDialogFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val disposable = CompositeDisposable()
    private val viewModel: MainFragmentViewModel by viewModels { viewModelFactory }
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainFragmentAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    private fun initialization() {
        adapter = MainFragmentAdapter(viewModel.progressBar) { setUpDialog(it) }
        with(binding) {
            recyclerView = rv
            btnCheckout.setOnClickListener { replaceFragment(CartFragment()) }
            buttonOpenSearch.setOnClickListener {
                showHideToolBar(true)
                binding.etSearch.requestFocus()
                setKeyboardVisibility(true)
            }
            etSearch.doAfterTextChanged {
                adapter.filter.filter(it)
                if (it?.length == 0) {
                    showHideToolBar(false)
                    setKeyboardVisibility(false)
                }
            }
        }
        recyclerView.adapter = adapter
        setUpOnBackPressed()
        viewModel.initDatabase()
        disposable.add(
            viewModel.pizzasListFromDb.subscribe {
                adapter.pizzaStartList = it
                adapter.setList(it)
                getPriceOfAllPizzas(it)
            }
        )
    }

    private fun getPriceOfAllPizzas(list: List<Pizza>) {
        var price = 0
        for (i in list.indices) {
            price += (list[i].price.toInt() * list[i].quantity)
        }
        if (price > 0) {
            with(binding) {
                checkout.visibility = View.VISIBLE
                tvPrice.text = getString(R.string.price, price)
            }
        }
    }

    private fun setUpDialog(pizzaId: Int) {
        val detailsDialogFragment = DetailsDialogFragment()
        detailsDialogFragment.arguments = Bundle(1).apply {
            putInt(PIZZA_ID, pizzaId)
        }
        detailsDialogFragment.show(parentFragmentManager, "")
        setKeyboardVisibility(false)
    }

    private fun showHideToolBar(boolean: Boolean) = with(binding) {
        if (boolean) {
            cvSearchBar.visibility = View.VISIBLE
            buttonOpenSearch.visibility = View.GONE
            toolbarTitle.visibility = View.GONE
        } else {
            cvSearchBar.visibility = View.GONE
            buttonOpenSearch.visibility = View.VISIBLE
            toolbarTitle.visibility = View.VISIBLE
        }
    }


    private fun setKeyboardVisibility(show: Boolean) {
        val imm: InputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (show) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        } else {
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }

    private fun setUpOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.cvSearchBar.visibility == View.VISIBLE) {
                        binding.etSearch.text.clear()
                        disposable.add(
                            Completable.timer(200, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                                .subscribe { binding.rv.scrollToPosition(0) }
                        )
                        showHideToolBar(false)
                    } else requireActivity().onBackPressed()
                }
            })
    }

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
    }

    companion object {
        const val PIZZA_ID = "ID"
    }
}