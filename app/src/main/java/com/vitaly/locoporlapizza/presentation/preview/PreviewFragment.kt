package com.vitaly.locoporlapizza.presentation.preview

import android.os.Bundle
import android.util.Log
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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class PreviewFragment : BaseFragment<FragmentPreviewBinding>(FragmentPreviewBinding::inflate) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PreviewFragmentViewModel by viewModels { viewModelFactory }
    private lateinit var disposable: CompositeDisposable
    private lateinit var adapter: PreviewFragmentAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        disposable = CompositeDisposable()
        disposable.add(
            viewModel.getPizzaById(arguments?.getInt(PIZZA_ID) ?: 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewModel.selectedPizza = it
                    adapter = PreviewFragmentAdapter(
                        viewModel.progressBar,
                        viewModel.selectedPizza.imageUrls
                    )
                    with(binding) {
                        pizzaName.text = viewModel.selectedPizza.name
                        price.text =
                            getString(R.string.price, viewModel.selectedPizza.price.toInt())
                        vp2.adapter = adapter
                        vp2.registerOnPageChangeCallback(viewPagerListener)
                    }
                }, { it.printStackTrace() })
        )


        binding.btnCheckout.setOnClickListener {
            disposable.add(
                viewModel.addPizza(viewModel.selectedPizza).subscribeOn(Schedulers.io()).subscribe({
                    Log.d(PreviewFragmentViewModel.TAG, "Pizza added")
                }, { it.printStackTrace() })
            )
            replaceFragment(MainFragment())
        }
        binding.buttonBack.setOnClickListener {
            setUpDialog()
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    setUpDialog()
                }
            })
    }

    private fun setUpDialog() {
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
                viewModel.selectedPizza.imageUrls.size
            )
            super.onPageSelected(position)
        }
    }

    override fun onDestroyView() {
        binding.vp2.unregisterOnPageChangeCallback(viewPagerListener)
        disposable.clear()
        super.onDestroyView()
    }

    companion object {
        const val PIZZA_ID = "ID"
    }
}