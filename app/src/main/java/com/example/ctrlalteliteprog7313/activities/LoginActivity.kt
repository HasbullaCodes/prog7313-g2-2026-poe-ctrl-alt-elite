package com.example.ctrlalteliteprog7313.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ctrlalteliteprog7313.R
import com.example.ctrlalteliteprog7313.database.AppDatabase
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // UI Components
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        // Database
        val db = AppDatabase.getDatabase(this)

        // LOGIN BUTTON
        btnLogin.setOnClickListener {
            val email = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            } else {
                //Code Attribution
                //Title: Kotlin Coroutines on Android
                //Author: Android Developers
                //Date: 2024
                //Version: Latest
                //Availability: https://developer.android.com/kotlin/coroutines
                lifecycleScope.launch {
                    val user = db.userDao().loginUser(email, password)

                    runOnUiThread {
                        if (user != null) {
                            Log.d("APP_DEBUG", "Login successful: $email")

                            Toast.makeText(
                                this@LoginActivity,
                                "Login successful",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this@LoginActivity, OnboardingOneActivity::class.java)
                            intent.putExtra("userName", user.name)
                            intent.putExtra("userEmail", user.email)
                            startActivity(intent)
                            finish()

                        } else {
                            Log.d("APP_DEBUG", "Login failed: $email")

                            Toast.makeText(
                                this@LoginActivity,
                                "Invalid credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        // SIGN UP BUTTON
        btnSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // FORGOT PASSWORD (FIXED)
        tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }
}