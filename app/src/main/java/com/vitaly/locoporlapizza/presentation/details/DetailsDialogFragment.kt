package com.vitaly.locoporlapizza.presentation.details

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vitaly.locoporlapizza.R
import com.vitaly.locoporlapizza.databinding.FragmentDetailsDialogBinding
import com.vitaly.locoporlapizza.domain.PizzaResponse
import com.vitaly.locoporlapizza.presentation.preview.PreviewFragment
import com.vitaly.locoporlapizza.utils.loadPicture
import com.vitaly.locoporlapizza.utils.pizzaMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailsDialogFragment : BottomSheetDialogFragment() {
    private lateinit var viewModel: DetailsFragmentViewModel
    private var pizzaId: Int = 1
    private var _binding: FragmentDetailsDialogBinding? = null
    private val binding get() = _binding!!
    private var pizza: PizzaResponse? = null

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
        pizzaId = arguments?.getInt(PIZZA_ID) ?: 1
        viewModel = ViewModelProvider(this).get(DetailsFragmentViewModel::class.java)
        viewModel.initDatabase()

        with(binding) {
            ivPizza.setOnClickListener {
                val previewFragment = PreviewFragment()
                val bundle = Bundle(1)
                bundle.putInt(PIZZA_ID, pizzaId)
                previewFragment.arguments = bundle
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.root_container, previewFragment)
                    .addToBackStack(null)
                    .commit()
                dismiss()
            }
            viewModel.getData(pizzaId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    ivPizza.loadPicture(it.imageUrls[0])
                    tvPizzaName.text = it.name
                    tvPizzaDesc.text = it.description
                    price.text = getString(R.string.price, it.price.toInt())
                    pizza = it
                }, { it.printStackTrace() })

            checkout.setOnClickListener {
                if (pizza != null) {
                    viewModel.insert(pizzaMapper(pizza!!))
                }
                dismiss()
            }
        }
    }

    private fun setupBottomSheet(dialog: DialogInterface) {
        val bottomSheet =
            (dialog as BottomSheetDialog).findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.setBackgroundResource(R.drawable.bottom_sheet_dialog_bg)
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet!!)
        behavior.skipCollapsed = true
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheet.layoutParams.height = ((requireActivity().windowManager.defaultDisplay.height) * 0.8).toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val PIZZA_ID = "ID"
    }
}