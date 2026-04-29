package com.example.ctrlalteliteprog7313.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ctrlalteliteprog7313.R
import com.example.ctrlalteliteprog7313.database.AppDatabase
import kotlinx.coroutines.launch

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Back button
        findViewById<TextView>(R.id.tvBack).setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }

        val etEmail = findViewById<EditText>(R.id.etForgotEmail)
        val etNewPassword = findViewById<EditText>(R.id.etNewPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmNewPassword)
        val btnResetPassword = findViewById<Button>(R.id.btnResetPassword)

        val db = AppDatabase.getDatabase(this)

        btnResetPassword.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val newPassword = etNewPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val user = db.userDao().getUserByEmail(email)

                runOnUiThread {
                    if (user == null) {
                        Toast.makeText(this@ForgotPasswordActivity, "Email not found", Toast.LENGTH_SHORT).show()
                    }
                }

                if (user != null) {
                    db.userDao().updatePassword(email, newPassword)

                    runOnUiThread {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            "Password updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }
            }
        }
    }
}