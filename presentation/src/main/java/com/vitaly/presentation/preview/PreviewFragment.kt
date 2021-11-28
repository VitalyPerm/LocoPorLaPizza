package com.vitaly.presentation.preview

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vitaly.presentation.R
import com.vitaly.presentation.databinding.FragmentPreviewBinding
import com.vitaly.presentation.details.DetailsDialogFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PreviewFragment : Fragment(R.layout.fragment_preview) {
    private val viewModel: PreviewFragmentViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private var job: Job? = null
    private lateinit var adapter: PreviewFragmentAdapter
    private val binding by viewBinding(FragmentPreviewBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }


    private fun initialize() {
        viewModel.getPizzaById(arguments?.getInt(DetailsDialogFragment.PIZZA_ID) ?: 1)
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.pizza.collect {
                with(binding) {
                    pizzaName.text = it.name
                    price.text = getString(R.string.price, it.price.toInt())
                    adapter = PreviewFragmentAdapter(viewModel.progressBar, it.imageUrls)
                    vp2.adapter = adapter
                    price.text = getString(R.string.price, it.price.toInt())
                    binding.vp2.registerOnPageChangeCallback(viewPagerListener)
                   pizzaCount.text = getString(R.string.preview_pizza_count, binding.vp2.currentItem + 1, it.imageUrls.size)
                }
            }
        }

        binding.btnCheckout.setOnClickListener {
            setUpMainFragment()
        }
        binding.buttonBack.setOnClickListener {
            setUpDetailsDialog()
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    setUpDetailsDialog()
                }
            })
    }

    private fun setUpMainFragment() {
        viewModel.addPizza()
        findNavController().navigate(R.id.action_previewFragment_to_mainFragment)
    }

    private fun setUpDetailsDialog() {
        findNavController().navigate(R.id.action_previewFragment_to_mainFragment)
        val detailsDialogFragment = DetailsDialogFragment()
        detailsDialogFragment.arguments = Bundle(1).apply {
            putInt(PIZZA_ID, arguments?.getInt(PIZZA_ID) ?: 1)
        }
        detailsDialogFragment.show(parentFragmentManager, "")
    }

    private val viewPagerListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding.pizzaCount.text = getString(
                R.string.preview_pizza_count,
                binding.vp2.currentItem + 1,
                viewModel.pizza1.imageUrls.size
            )
            super.onPageSelected(position)
        }
    }

    override fun onDestroyView() {
        binding.vp2.unregisterOnPageChangeCallback(viewPagerListener)
        job?.cancel()
        super.onDestroyView()
    }

    companion object {
        const val PIZZA_ID = "ID"
    }
}