package com.vitaly.locoporlapizza

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.vitaly.locoporlapizza.databinding.FragmentEndBinding

class EndFragment : Fragment() {
    private lateinit var binding: FragmentEndBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEndBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    private fun initialization() {
        binding.tvBackToMenu.setOnClickListener {
            FMHelper.replaceFragment(MainFragment())
            FMHelper.clearBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                FMHelper.replaceFragment(MainFragment())
                FMHelper.clearBackStack()
            }
        })
    }
}