package com.vitaly.locoporlapizza.presentation.preview

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.vitaly.locoporlapizza.R
import com.vitaly.locoporlapizza.databinding.FragmentPreviewBinding
import com.vitaly.locoporlapizza.presentation.BaseFragment
import com.vitaly.locoporlapizza.utils.pizzaMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class PreviewFragment : BaseFragment<FragmentPreviewBinding>(FragmentPreviewBinding::inflate) {
    private lateinit var viewModel: PreviewFragmentViewModel
    private lateinit var adapter: PreviewFragmentAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        viewModel = ViewModelProvider(this).get(PreviewFragmentViewModel::class.java)
        viewModel.initDatabase()
        viewModel.getData(arguments?.getInt(PIZZA_ID) ?: 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                for (i in it.imageUrls) {
                    viewModel.images.add(i)
                }
                adapter = PreviewFragmentAdapter(viewModel.images)
                with(binding) {
                    pizzaCount.text = getString(R.string.preview_pizza_count, binding.vp2.currentItem + 1, viewModel.images.size)
                    pizzaName.text = it.name
                    price.text = getString(R.string.price, it.price.toInt())
                    viewModel.selectedPizza = it
                    vp2.adapter = adapter
                }
            }, { it.printStackTrace() })
        binding.btnCheckout.setOnClickListener {
            if (viewModel.selectedPizza != null) {
                viewModel.insert(pizzaMapper(viewModel.selectedPizza!!))
            }
            requireActivity().onBackPressed()
        }
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    companion object {
        const val PIZZA_ID = "ID"
    }
}