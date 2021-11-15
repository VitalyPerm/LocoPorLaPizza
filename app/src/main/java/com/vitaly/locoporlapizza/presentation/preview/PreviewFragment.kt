package com.vitaly.locoporlapizza.presentation.preview

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.vitaly.locoporlapizza.R
import com.vitaly.locoporlapizza.databinding.FragmentPreviewBinding
import com.vitaly.locoporlapizza.presentation.BaseFragment
import com.vitaly.locoporlapizza.presentation.details.DetailsDialogFragment
import com.vitaly.locoporlapizza.presentation.main.MainFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class PreviewFragment : BaseFragment<FragmentPreviewBinding>(FragmentPreviewBinding::inflate) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val disposable = CompositeDisposable()
    private val viewModel: PreviewFragmentViewModel by viewModels { viewModelFactory }
    private lateinit var adapter: PreviewFragmentAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        viewModel.getPizzaById(arguments?.getInt(PIZZA_ID) ?: 1)
        disposable.add(
            viewModel.selectedPizza.subscribe {
                adapter =
                    PreviewFragmentAdapter(viewModel.progressBar, it.imageUrls)
                with(binding) {
                    pizzaName.text = it.name
                    price.text =
                        getString(R.string.price, it.price.toInt())
                    vp2.adapter = adapter
                    vp2.registerOnPageChangeCallback(viewPagerListener)
                }
            }
        )

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
        viewModel.addPizza(viewModel.selectedPizza.value)
        replaceFragment(MainFragment())
    }

    private fun setUpDetailsDialog() {
        replaceFragment(MainFragment())
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
                viewModel.selectedPizza.value.imageUrls.size
            )
            super.onPageSelected(position)
        }
    }

    override fun onDestroyView() {
        disposable.clear()
        binding.vp2.unregisterOnPageChangeCallback(viewPagerListener)
        super.onDestroyView()
    }

    companion object {
        const val PIZZA_ID = "ID"
    }
}