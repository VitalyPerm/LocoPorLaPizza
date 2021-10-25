package com.vitaly.locoporlapizza.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.vitaly.locoporlapizza.databinding.FragmentEndBinding

import com.vitaly.locoporlapizza.presentation.main.MainFragment

class EndFragment : BaseFragment<FragmentEndBinding>(FragmentEndBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    private fun initialization() {
        binding.tvBackToMenu.setOnClickListener {
            replaceFragment(MainFragment())
            clearBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                replaceFragment(MainFragment())
                clearBackStack()
            }
        })
    }
}