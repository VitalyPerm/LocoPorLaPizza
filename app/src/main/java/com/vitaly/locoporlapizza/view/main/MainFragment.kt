package com.vitaly.locoporlapizza.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.vitaly.locoporlapizza.R
import com.vitaly.locoporlapizza.databinding.FragmentMainBinding
import com.vitaly.locoporlapizza.utils.DESCRIPTION
import com.vitaly.locoporlapizza.utils.NAME
import com.vitaly.locoporlapizza.utils.PRICE
import com.vitaly.locoporlapizza.utils.URL
import com.vitaly.locoporlapizza.view.BaseFragment
import com.vitaly.locoporlapizza.view.CartFragment
import com.vitaly.locoporlapizza.view.DetailsDialogFragment
import com.vitaly.locoporlapizza.view.MainActivity
import com.vitaly.locoporlapizza.viewmodel.MainFragmentViewModel

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate), MainFragmentAdapter.OnItemClickListener {
    private lateinit var toolbar: Toolbar
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
        adapter = MainFragmentAdapter(this)
        recyclerView = binding.rv
        recyclerView.adapter = adapter
        viewModel.getAll()
        viewModel.pizzaList.observe(viewLifecycleOwner, {
            adapter.setList(it)
        })
        binding.btnCheckout.setOnClickListener {
            replaceFragment(CartFragment())
        }
        toolbar = binding.toolbar
        toolbar.title = getString(R.string.menu)
        (activity as MainActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onItemClick(position: Int) {
        detailsDialogFragment = DetailsDialogFragment()
        val bundle = Bundle()
        bundle.putString(URL, adapter.pizzaList[position].imageUrl)
        bundle.putString(NAME, adapter.pizzaList[position].name)
        bundle.putString(DESCRIPTION, adapter.pizzaList[position].description)
        bundle.putString(PRICE, adapter.pizzaList[position].price.toString())
        detailsDialogFragment.arguments = bundle
        detailsDialogFragment.show(requireActivity().supportFragmentManager, "")
    }
}