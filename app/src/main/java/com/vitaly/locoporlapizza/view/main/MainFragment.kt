package com.vitaly.locoporlapizza.view.main

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.vitaly.locoporlapizza.R
import com.vitaly.locoporlapizza.databinding.FragmentMainBinding
import com.vitaly.locoporlapizza.model.PizzaEntity
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
    private lateinit var tempPizzaList: MutableList<PizzaEntity>
    private lateinit var detailsDialogFragment: DetailsDialogFragment
    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainFragmentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    override fun onResume() {
        viewModel.pizzaList.observe(viewLifecycleOwner, {
            adapter.setList(it)
            tempPizzaList = it.toMutableList()
        })
        super.onResume()
    }

    override fun onStop() {
        viewModel.pizzaList.removeObservers(viewLifecycleOwner)
        super.onStop()
    }

    private fun initialization() {
        viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        adapter = MainFragmentAdapter(this)
        recyclerView = binding.rv
        recyclerView.adapter = adapter
        viewModel.getAll()
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
        val manager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        val searchEditText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText.setTextColor(Color.BLACK)
        searchEditText.setHintTextColor(Color.BLACK)
        searchView.setSearchableInfo(manager.getSearchableInfo(requireActivity().componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                tempPizzaList.clear()
                val searchText = newText?.lowercase()
                if (searchText != null) {
                    viewModel.pizzaList.value?.forEach {
                        if (it.name.lowercase().contains(searchText)) {
                            tempPizzaList.add(it)
                        }
                    }
                    adapter.setList(tempPizzaList)
                    adapter.notifyDataSetChanged()
                } else {
                    tempPizzaList.clear()
                    viewModel.pizzaList.value?.let { adapter.setList(it) }
                }
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onItemClick(position: Int) {
        detailsDialogFragment = DetailsDialogFragment()
        val bundle = Bundle()
        bundle.putString(URL, adapter.pizzaList[position].imageUrl)
        bundle.putString(NAME, adapter.pizzaList[position].name)
        bundle.putString(DESCRIPTION, adapter.pizzaList[position].description)
        bundle.putInt(PRICE, adapter.pizzaList[position].price.toInt())
        detailsDialogFragment.arguments = bundle
        detailsDialogFragment.show(parentFragmentManager, "")
    }
}