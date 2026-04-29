package com.example.ctrlalteliteprog7313.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ctrlalteliteprog7313.R

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setActiveNav(R.id.navProfile)

        val tvBack = findViewById<TextView>(R.id.tvBack)
        val tvProfileName = findViewById<TextView>(R.id.tvProfileName)
        val tvProfileEmail = findViewById<TextView>(R.id.tvProfileEmail)

        val etMinGoal = findViewById<EditText>(R.id.etMinGoal)
        val etMaxGoal = findViewById<EditText>(R.id.etMaxGoal)
        val btnSaveGoals = findViewById<Button>(R.id.btnSaveGoals)
        val tvSavedGoals = findViewById<TextView>(R.id.tvSavedGoals)

        val btnEditProfile = findViewById<LinearLayout>(R.id.btnEditProfile)
        val btnSettings = findViewById<LinearLayout>(R.id.btnSettings)
        val btnHelp = findViewById<LinearLayout>(R.id.btnHelp)
        val btnLogout = findViewById<LinearLayout>(R.id.btnLogout)

        val navHome = findViewById<LinearLayout>(R.id.navHome)
        val navAnalysis = findViewById<LinearLayout>(R.id.navAnalysis)
        val navTransactions = findViewById<LinearLayout>(R.id.navTransactions)
        val navCategories = findViewById<LinearLayout>(R.id.navCategories)
        val navProfile = findViewById<LinearLayout>(R.id.navProfile)

        val name = intent.getStringExtra("userName")
        val email = intent.getStringExtra("userEmail")

        tvProfileName.text = name ?: "User"
        tvProfileEmail.text = email ?: "No email"

        val prefs = getSharedPreferences("goal_prefs", MODE_PRIVATE)

        val savedMin = prefs.getString("minGoal", "")
        val savedMax = prefs.getString("maxGoal", "")

        if (!savedMin.isNullOrEmpty() && !savedMax.isNullOrEmpty()) {
            tvSavedGoals.text = "Saved Goal: Min R$savedMin | Max R$savedMax"
        } else {
            tvSavedGoals.text = "No goals saved yet"
        }

        btnSaveGoals.setOnClickListener {
            val minGoal = etMinGoal.text.toString().trim()
            val maxGoal = etMaxGoal.text.toString().trim()

            val min = minGoal.toDoubleOrNull()
            val max = maxGoal.toDoubleOrNull()

            if (min == null || max == null) {
                Toast.makeText(this, "Enter valid goal amounts", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (min < 0 || max < 0) {
                Toast.makeText(this, "Goals cannot be negative", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (min > max) {
                Toast.makeText(this, "Minimum goal cannot be more than maximum goal", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            prefs.edit()
                .putString("minGoal", minGoal)
                .putString("maxGoal", maxGoal)
                .apply()

            tvSavedGoals.text = "Saved Goal: Min R$minGoal | Max R$maxGoal"

            etMinGoal.text.clear()
            etMaxGoal.text.clear()
        }

        tvBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("userName", name)
            intent.putExtra("userEmail", email)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        btnEditProfile.setOnClickListener {
            Toast.makeText(this, "Edit profile coming soon", Toast.LENGTH_SHORT).show()
        }

        btnSettings.setOnClickListener {
            Toast.makeText(this, "Settings coming soon", Toast.LENGTH_SHORT).show()
        }

        btnHelp.setOnClickListener {
            Toast.makeText(this, "Help coming soon", Toast.LENGTH_SHORT).show()
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("userName", name)
            intent.putExtra("userEmail", email)
            startActivity(intent)
        }

        navAnalysis.setOnClickListener {
            startActivity(Intent(this, CategoryTotalActivity::class.java))
        }

        navTransactions.setOnClickListener {
            startActivity(Intent(this, ExpenseListActivity::class.java))
        }

        navCategories.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }

        navProfile.setOnClickListener { }
    }

    private fun setActiveNav(activeNavId: Int) {
        val navHome = findViewById<LinearLayout>(R.id.navHome)
        val navAnalysis = findViewById<LinearLayout>(R.id.navAnalysis)
        val navTransactions = findViewById<LinearLayout>(R.id.navTransactions)
        val navCategories = findViewById<LinearLayout>(R.id.navCategories)
        val navProfile = findViewById<LinearLayout>(R.id.navProfile)

        val navItems = listOf(navHome, navAnalysis, navTransactions, navCategories, navProfile)

        navItems.forEach {
            it.background = null
        }

        findViewById<LinearLayout>(activeNavId)
            .setBackgroundResource(R.drawable.nav_active_bg)
    }
}