package com.vitaly.locoporlapizza

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.vitaly.locoporlapizza.databinding.FragmentMainBinding
import android.view.MenuInflater

class MainFragment : Fragment() {
    private lateinit var toolbar: Toolbar
    private lateinit var binding: FragmentMainBinding
    private lateinit var detailsDialogFragment: DetailsDialogFragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    private fun initialization() {
        binding.btnCheckout.setOnClickListener {
            FMHelper.replaceFragment(CartFragment())
        }
        binding.btnDetails.setOnClickListener {
            detailsDialogFragment = DetailsDialogFragment()
            detailsDialogFragment.show(FMHelper.APP_ACTIVITY.supportFragmentManager, "")
        }
        toolbar = binding.toolbar
        toolbar.title = getString(R.string.menu)
        FMHelper.APP_ACTIVITY.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}