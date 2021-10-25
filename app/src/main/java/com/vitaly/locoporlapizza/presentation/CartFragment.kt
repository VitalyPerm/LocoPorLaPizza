package com.vitaly.locoporlapizza.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.vitaly.locoporlapizza.R
import com.vitaly.locoporlapizza.databinding.FragmentCartBinding

class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {
    private lateinit var toolbar: Toolbar
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    private fun initialization() {
        binding.btnPlaceOrder.setOnClickListener {
            replaceFragment(EndFragment())
        }
        toolbar = binding.toolbar
        toolbar.title = getString(R.string.cart)
        (activity as MainActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_cart, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}