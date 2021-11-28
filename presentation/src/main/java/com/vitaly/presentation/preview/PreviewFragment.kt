package com.vitaly.presentation.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.vitaly.presentation.R
import com.vitaly.presentation.databinding.FragmentPreviewBinding
import com.vitaly.presentation.details.DetailsDialogFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PreviewFragment : Fragment() {
    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!
    private val disposable = CompositeDisposable()
    private val viewModel: PreviewFragmentViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private lateinit var adapter: PreviewFragmentAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPreviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun initialize() {
        viewModel.getPizzaById(arguments?.getInt(PIZZA_ID) ?: 1).let {
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
//        disposable.add(
//            viewModel.selectedPizza.subscribe {
//                adapter =
//                    PreviewFragmentAdapter(viewModel.progressBar, it.imageUrls)
//                with(binding) {
//                    pizzaName.text = it.name
//                    price.text =
//                        getString(R.string.price, it.price.toInt())
//                    vp2.adapter = adapter
//                    vp2.registerOnPageChangeCallback(viewPagerListener)
//                }
//            }
//        )

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
        viewModel.selectedPizza?.let { viewModel.addPizza(it) }
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
                viewModel.selectedPizza.imageUrls.size
            )
            super.onPageSelected(position)
        }
    }

    override fun onDestroyView() {
        disposable.clear()
        binding.vp2.unregisterOnPageChangeCallback(viewPagerListener)
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val PIZZA_ID = "ID"
    }
}