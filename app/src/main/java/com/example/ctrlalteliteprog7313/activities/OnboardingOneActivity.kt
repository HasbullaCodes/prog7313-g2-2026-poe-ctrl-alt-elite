package com.example.ctrlalteliteprog7313.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ctrlalteliteprog7313.R

class OnboardingOneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        val tvNext = findViewById<TextView>(R.id.tvNext)

        tvNext.setOnClickListener {
            val intent = Intent(this, OnboardingTwoActivity::class.java)
            intent.putExtra("userName", getIntent().getStringExtra("userName"))
            intent.putExtra("userEmail", getIntent().getStringExtra("userEmail"))
            startActivity(intent)
        }
    }
}