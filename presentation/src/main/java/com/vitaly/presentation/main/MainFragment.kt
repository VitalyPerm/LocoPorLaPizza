package com.vitaly.presentation.main

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.vitaly.domain.models.Pizza
import com.vitaly.presentation.R
import com.vitaly.presentation.databinding.FragmentMainBinding
import com.vitaly.presentation.details.DetailsDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainFragmentViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private var job: Job? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainFragmentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    private fun initialization() {
        adapter = MainFragmentAdapter(viewModel.progressBar) { setUpDialog(it) }
        with(binding) {
            recyclerView = rv
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
        job = lifecycleScope.launch {
            viewModel.pizzaList.collect {
                adapter.pizzaStartList = it
                adapter.setList(it)
                getPriceOfAllPizzas(it)
            }
        }
        binding.btnCheckout.setOnClickListener {
            this.findNavController().navigate(R.id.action_mainFragment_to_cartFragment)

        }
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
                        lifecycleScope.launch {
                            delay(200)
                            withContext(Dispatchers.Main) {
                                binding.rv.scrollToPosition(0)
                            }
                        }
                        showHideToolBar(false)
                    } else requireActivity().finish()
                }
            })
    }

    override fun onDestroyView() {
        job = null
        super.onDestroyView()
    }

    companion object {
        const val PIZZA_ID = "ID"
    }
}