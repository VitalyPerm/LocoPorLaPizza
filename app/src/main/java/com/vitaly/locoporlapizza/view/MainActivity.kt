package com.vitaly.locoporlapizza.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vitaly.locoporlapizza.R
import com.vitaly.locoporlapizza.view.main.MainFragment

class MainActivity : AppCompatActivity() {
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