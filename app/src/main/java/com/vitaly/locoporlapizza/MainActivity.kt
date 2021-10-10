package com.vitaly.locoporlapizza

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_LocoPorLaPizza)
        setContentView(R.layout.activity_main)
        FMHelper.APP_ACTIVITY = this
        FMHelper.addFragment(MainFragment())
    }
}