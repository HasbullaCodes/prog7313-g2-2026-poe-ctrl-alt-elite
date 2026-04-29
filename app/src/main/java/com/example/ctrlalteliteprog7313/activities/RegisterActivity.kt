package com.example.ctrlalteliteprog7313.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ctrlalteliteprog7313.R
import com.example.ctrlalteliteprog7313.database.AppDatabase
import com.example.ctrlalteliteprog7313.database.UserEntity
import kotlinx.coroutines.launch

/**
 * RegisterActivity
 *
 * Handles user registration by collecting user details
 * and storing them in the local Room database.
 */
class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val tvLoginLink = findViewById<TextView>(R.id.tvLoginLink)
        tvLoginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        // UI Components
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPasswordRegister)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        // Initialize database
        val db = AppDatabase.getDatabase(this)
        /**
         * Register Button Click Listener
         * Validates input and saves user to database
         */
        btnRegister.setOnClickListener {
            // Get and clean user input
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            // Input validation
            var isValid = true

            // Clear previous errors
            etName.error = null
            etEmail.error = null
            etPassword.error = null

            //Code Attribution
            //Title: Validate input as the user types
            //Author: Android Developers
            //Date: 2024
            //Version: Latest
            //Availability: https://developer.android.com/develop/ui/compose/quick-guides/content/validate-input

            if (name.isEmpty()) {
                etName.error = "Enter your full name"
                isValid = false
            }

            if (!email.contains("@") || !email.contains(".")) {
                etEmail.error = "Enter a valid email"
                isValid = false
            }

            if (password.length < 7) {
                etPassword.error = "At least 7 characters"
                isValid = false
            }

            if (!password.any { it.isUpperCase() }) {
                etPassword.error = "Must contain a capital letter"
                isValid = false
            }

            if (!password.any { it.isDigit() }) {
                etPassword.error = "Must contain a number"
                isValid = false
            }

            if (!isValid) return@setOnClickListener else {
                // Create user object
                val user = UserEntity(
                    name = name,
                    email = email,
                    password = password
                )

                //Code Attribution
                //Title: Kotlin Coroutines on Android
                //Author: Android Developers
                //Date: 2024
                //Version: Latest
                //Availability: https://developer.android.com/kotlin/coroutines

                // Run database operation on background thread
                lifecycleScope.launch {
                    // Insert user into database
                    db.userDao().insertUser(user)
                    // Switch to main thread for UI updates
                    runOnUiThread {
                        Log.d("APP_DEBUG", "User registered: $email")
                        Toast.makeText(this@RegisterActivity, "Registered successfully", Toast.LENGTH_SHORT).show()
                        // Close activity and return to login screen
                        finish()
                    }
                }
            }
        }
    }
}