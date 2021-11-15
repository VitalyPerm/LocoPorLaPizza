package com.vitaly.locoporlapizza.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.vitaly.locoporlapizza.R
import com.vitaly.locoporlapizza.presentation.cart.CartFragment
import com.vitaly.locoporlapizza.presentation.main.MainFragment
import com.vitaly.locoporlapizza.presentation.preview.PreviewFragment

class MainActivity : AppCompatActivity() {
    private var fragmentName = ""
    private var fragment = Fragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization(savedInstanceState)
        setTheme(R.style.Theme_LocoPorLaPizza)
        setContentView(R.layout.activity_main)
    }

    private fun initialization(state: Bundle?) {
        if (state != null) {
            fragmentName = state.getString(CURRENT_FRAGMENT)
                ?.substringBefore('{') ?: ""
        }
        fragment = when (fragmentName) {
            PREVIEW -> PreviewFragment()
            CART -> CartFragment()
            END -> EndFragment()
            else -> MainFragment()
        }
        supportFragmentManager
            .beginTransaction()
            .add(R.id.root_container, fragment)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(CURRENT_FRAGMENT, supportFragmentManager.fragments.last().toString())
        super.onSaveInstanceState(outState)
    }

    companion object {
        const val CURRENT_FRAGMENT = "CURRENT_FRAGMENT"
        const val PREVIEW = "PreviewFragment"
        const val CART = "CartFragment"
        const val END = "EndFragment"
    }
}