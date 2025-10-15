package com.example.ecommerceapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            delay(2000)
            proceedWithNextScreen()
        }
    }


    fun proceedWithNextScreen() {
        val preference = getSharedPreferences(Constants.SETTINGS, MODE_PRIVATE)
        val isLoggedIn = preference.getBoolean(Constants.LOGGED_IN, false)
        if (isLoggedIn) {
            startActivity(Intent(this@MainActivity, DashboardActivity::class.java))

        } else {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))

        }
        finish()
    }
}