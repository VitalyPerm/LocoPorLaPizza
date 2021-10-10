package com.vitaly.locoporlapizza

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.vitaly.locoporlapizza.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var toolbar: Toolbar
    private lateinit var binding: FragmentCartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    private fun initialization() {
        binding.btnPlaceOrder.setOnClickListener {
            FMHelper.replaceFragment(EndFragment())
        }
        toolbar = binding.toolbar
        toolbar.title = getString(R.string.cart)
        FMHelper.APP_ACTIVITY.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_cart, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}