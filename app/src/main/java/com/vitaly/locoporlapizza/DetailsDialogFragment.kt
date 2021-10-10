package com.vitaly.locoporlapizza

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vitaly.locoporlapizza.databinding.FragmentDetailsDialogBinding

class DetailsDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDetailsDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bDialog.setOnShowListener { dialog: DialogInterface ->
            val bottomSheet =
                (dialog as BottomSheetDialog).findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.setBackgroundResource(R.drawable.bottom_sheet_dialog_bg)
            val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet!!)
            behavior.skipCollapsed = true
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }
            })
            setupFullHeight(bottomSheet)
        }
        return bDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        binding.btnPreview.setOnClickListener {
            FMHelper.replaceFragment(PreviewFragment())
            dismiss()
        }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height =
            displayMetrics.heightPixels - (displayMetrics.heightPixels / HEIGHT_PERCENT_OFFSET_TOP)

        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = height
        bottomSheet.layoutParams = layoutParams
    }

    companion object {
        const val HEIGHT_PERCENT_OFFSET_TOP = 5
    }
}