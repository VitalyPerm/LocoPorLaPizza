package com.vitaly.locoporlapizza

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object FMHelper {
    lateinit var APP_ACTIVITY: MainActivity

    fun addFragment(fragment: Fragment) {
        APP_ACTIVITY.supportFragmentManager
            .beginTransaction()
            .add(R.id.root_container, fragment)
            .commit()
    }

    fun replaceFragment(fragment: Fragment) {
        APP_ACTIVITY.supportFragmentManager
            .beginTransaction()
            .replace(R.id.root_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun clearBackStack() {
        APP_ACTIVITY.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}