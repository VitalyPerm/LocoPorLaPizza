package com.vitaly.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vitaly.presentation.databinding.FragmentEndBinding

class EndFragment : Fragment() {
    private var _binding: FragmentEndBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEndBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun initialization() {
        binding.tvBackToMenu.setOnClickListener {
            findNavController().navigate(R.id.action_endFragment_to_mainFragment)
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_endFragment_to_mainFragment)
                }
            })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}