package com.vitaly.locoporlapizza.presentation.main

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.vitaly.locoporlapizza.R
import com.vitaly.locoporlapizza.data.PizzaEntity
import com.vitaly.locoporlapizza.databinding.FragmentMainBinding
import com.vitaly.locoporlapizza.presentation.BaseFragment
import com.vitaly.locoporlapizza.presentation.CartFragment
import com.vitaly.locoporlapizza.presentation.details.DetailsDialogFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private lateinit var detailsDialogFragment: DetailsDialogFragment
    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainFragmentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    private fun initialization() {
        viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        adapter = MainFragmentAdapter { setUpDialog(it) }
        with(binding) {
            recyclerView = rv
            btnCheckout.setOnClickListener { replaceFragment(CartFragment()) }
            buttonOpenSearch.setOnClickListener {
                showHideToolBar(true)
                binding.etSearch.requestFocus()
                setKeyboardVisibility(true)
            }
            etSearch.addTextChangedListener(rvFilter)
        }
        recyclerView.adapter = adapter
        setUpOnBackPressed()
        viewModel.initDatabase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getPriceOfAllPizzas(it)
            }, { it.printStackTrace() })

        viewModel.getPizzaList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.pizzaStartList = it
                adapter.setList(it)
            }, { it.printStackTrace() })
    }

    private fun getPriceOfAllPizzas(list: List<PizzaEntity>) {
        var price = 0
        for (i in list.indices) {
            price += list[i].price.toInt()
        }
        if (price > 0) {
            with(binding) {
                checkout.visibility = View.VISIBLE
                tvPrice.text = getString(R.string.price, price)
            }
        }
    }

    private fun setUpDialog(pizzaId: Int) {
        detailsDialogFragment = DetailsDialogFragment()
        val bundle = Bundle(1)
        bundle.putInt(PIZZA_ID, pizzaId)
        detailsDialogFragment.arguments = bundle
        detailsDialogFragment.show(parentFragmentManager, "")
        setKeyboardVisibility(false)
    }

    private fun showHideToolBar(boolean: Boolean) {
        if (boolean) {
            with(binding) {
                cvSearchBar.visibility = View.VISIBLE
                buttonOpenSearch.visibility = View.GONE
                toolbarTitle.visibility = View.GONE
            }
        } else {
            with(binding) {
                cvSearchBar.visibility = View.GONE
                buttonOpenSearch.visibility = View.VISIBLE
                toolbarTitle.visibility = View.VISIBLE
            }
        }
    }

    private fun setKeyboardVisibility(show: Boolean) {
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (show) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        } else {
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }

    private fun setUpOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.cvSearchBar.visibility == View.VISIBLE) {
                    showHideToolBar(false)
                } else requireActivity().onBackPressed()
            }
        })
    }

    private val rvFilter = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {
            adapter.filter.filter(p0)
            if (p0?.length == 0) {
                showHideToolBar(false)
                setKeyboardVisibility(false)
            }
        }
    }


    companion object {
        const val PIZZA_ID = "ID"
    }
}