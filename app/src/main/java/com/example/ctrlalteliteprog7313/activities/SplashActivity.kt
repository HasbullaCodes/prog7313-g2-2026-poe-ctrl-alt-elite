package com.example.ctrlalteliteprog7313.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.ctrlalteliteprog7313.R

/**
 * SplashActivity
 *
 * Displays a splash screen for a short duration
 * before navigating to the Welcome screen.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set splash screen layout
        setContentView(R.layout.activity_splash)
        /**
         * Start delay timer for splash screen
         */
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }, 3000)
    }
}