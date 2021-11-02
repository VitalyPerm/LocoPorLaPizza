package com.vitaly.locoporlapizza.presentation

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.vitaly.locoporlapizza.R
import com.vitaly.locoporlapizza.presentation.main.MainFragment
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_LocoPorLaPizza)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.root_container, MainFragment())
            .commit()
    }
}