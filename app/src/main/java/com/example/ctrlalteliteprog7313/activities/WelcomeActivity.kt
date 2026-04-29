package com.example.ctrlalteliteprog7313.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ctrlalteliteprog7313.R

/**
 * WelcomeActivity
 *
 * Entry screen after splash.
 * Provides navigation to Login, Registration,
 * and password recovery.
 */
class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
// UI Components
        val btnGoLogin = findViewById<Button>(R.id.btnGoLogin)
        val btnGoSignUp = findViewById<Button>(R.id.btnGoSignUp)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)
        /**
         * Navigate to Login screen
         */
        btnGoLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        /**
         * Navigate to Registration screen
         */
        btnGoSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        /**
         * Navigate to Forgot Password
         */
        tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
        }
    }
