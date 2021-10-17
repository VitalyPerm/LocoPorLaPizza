package com.vitaly.locoporlapizza.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.vitaly.locoporlapizza.R


typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {
    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.root_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun addFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .add(R.id.root_container, fragment)
            .commit()
    }

    fun clearBackStack() {
        requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

}

//abstract class BaseFragment: Fragment() {
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val binding =
//    }
//    val fm = requireActivity().supportFragmentManager
//
//    fun addFragment(fragment: Fragment) {
//        fm
//            .beginTransaction()
//            .add(R.id.root_container, fragment)
//            .commit()
//    }
//
//    fun replaceFragment(fragment: Fragment) {
//        fm
//            .beginTransaction()
//            .replace(R.id.root_container, fragment)
//            .addToBackStack(null)
//            .commit()
//    }
//
//    fun clearBackStack() {
//        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//    }
//
//}