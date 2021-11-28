package com.vitaly.presentation.details

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vitaly.presentation.R
import com.vitaly.presentation.databinding.FragmentDetailsDialogBinding
import com.vitaly.presentation.utils.loadPicture
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable

@AndroidEntryPoint
class DetailsDialogFragment : BottomSheetDialogFragment() {
    private val disposable = CompositeDisposable()
    private val viewModel: DetailsFragmentViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private var _binding: FragmentDetailsDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bDialog.setOnShowListener {
            setupBottomSheet(it)
        }
        return bDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        with(binding) {
            ivPizza.setOnClickListener {
                preparePreviewFragment()
            }
            viewModel.getPizzaById(arguments?.getInt(PIZZA_ID) ?: 1).let {
                ivPizza.loadPicture(it.imageUrls[0], viewModel.progressBar)
                tvPizzaName.text = it.name
                tvPizzaDesc.text = it.description
                price.text = getString(R.string.price, it.price.toInt())
            }
            checkout.setOnClickListener {
                viewModel.addPizza(viewModel.selectedPizza)
                dismiss()
            }
        }
    }

    private fun preparePreviewFragment() {
        val bundle =  Bundle(1).apply {
            putInt(PIZZA_ID, arguments?.getInt(PIZZA_ID) ?: 1)
        }
        findNavController().navigate(R.id.action_mainFragment_to_previewFragment, bundle)
        dismiss()
    }


    private fun setupBottomSheet(dialog: DialogInterface) {
        val bottomSheet =
            (dialog as BottomSheetDialog).findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.setBackgroundResource(R.drawable.bottom_sheet_dialog_bg)
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet!!)
        behavior.skipCollapsed = true
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheet.layoutParams.height =
            ((requireActivity().windowManager.defaultDisplay.height) * 0.8).toInt()
    }

    override fun onDestroy() {
        disposable.clear()
        _binding = null
        super.onDestroy()
    }

    companion object {
        const val PIZZA_ID = "ID"
    }
}