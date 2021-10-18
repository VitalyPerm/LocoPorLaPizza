package com.vitaly.locoporlapizza.view

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vitaly.locoporlapizza.R
import com.vitaly.locoporlapizza.databinding.FragmentDetailsDialogBinding
import com.vitaly.locoporlapizza.utils.*

class DetailsDialogFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentDetailsDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.root_container, PreviewFragment())
                    .addToBackStack(null)
                    .commit()
                dismiss()
            }
            ivPizza.loadPicture(arguments?.getString(URL))
            tvPizzaName.text = arguments?.getString(NAME)
            tvPizzaDesc.text = arguments?.getString(DESCRIPTION)
            price.text = getString(R.string.price, arguments?.getInt(PRICE))
        }
    }

    private fun setupBottomSheet(dialog: DialogInterface) {
        val bottomSheet =
            (dialog as BottomSheetDialog).findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.setBackgroundResource(R.drawable.bottom_sheet_dialog_bg)
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet!!)
        behavior.skipCollapsed = true
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        val displayHeight = requireActivity().windowManager.defaultDisplay.height
        bottomSheet.layoutParams.height = displayHeight - (displayHeight / HEIGHT_PERCENT_OFFSET_TOP)
    }

    companion object {
        const val HEIGHT_PERCENT_OFFSET_TOP = 5
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}