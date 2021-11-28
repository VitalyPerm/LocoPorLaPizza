package com.vitaly.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    //    private var fragmentName = ""
//    private var fragment:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_LocoPorLaPizza)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        initialization(savedInstanceState)
    }

    private fun initialization(state: Bundle?) {
//        if (state != null) {
//            fragmentName = state.getString(CURRENT_FRAGMENT)
//                ?.substringBefore('{') ?: ""
//        }
//        fragment = when (fragmentName) {
//            PREVIEW -> PreviewFragment()
//            CART -> CartFragment()
//            END -> EndFragment()
//            else -> MainFragment()
//        }
//        supportFragmentManager
//            .beginTransaction()
//            .add(R.id.root_container, fragment)
//            .commit()
        navController.navigate(R.id.mainFragment)
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        outState.putString(CURRENT_FRAGMENT, supportFragmentManager.fragments.last().toString())
//        super.onSaveInstanceState(outState)
//    }

    companion object {
        const val CURRENT_FRAGMENT = "CURRENT_FRAGMENT"
        const val PREVIEW = "PreviewFragment"
        const val CART = "CartFragment"
        const val END = "EndFragment"
    }
}