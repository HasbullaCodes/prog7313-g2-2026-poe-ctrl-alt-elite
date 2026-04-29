package com.example.ctrlalteliteprog7313.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ctrlalteliteprog7313.R
import com.example.ctrlalteliteprog7313.database.AppDatabase
import com.example.ctrlalteliteprog7313.database.CategoryEntity
import kotlinx.coroutines.launch

// Activity used to manage and add new categories
class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set layout for category screen
        setContentView(R.layout.activity_category)

        // Highlight active nav
        setActiveNav(R.id.navCategories)

        // Link UI components to variables
        val etName = findViewById<EditText>(R.id.etCategoryName)
        val btnAdd = findViewById<Button>(R.id.btnAddCategory)
        val categoryListContainer = findViewById<LinearLayout>(R.id.categoryListContainer)

        // Back button
        findViewById<TextView>(R.id.tvBack).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        // Bottom navigation textviews
        val navHome = findViewById<LinearLayout>(R.id.navHome)
        val navAnalysis = findViewById<LinearLayout>(R.id.navAnalysis)
        val navTransactions = findViewById<LinearLayout>(R.id.navTransactions)
        val navCategories = findViewById<LinearLayout>(R.id.navCategories)
        val navProfile = findViewById<LinearLayout>(R.id.navProfile)

        // Get database instance
        val db = AppDatabase.getDatabase(this)

        fun loadCategories() {
            lifecycleScope.launch {
                val categories = db.categoryDao().getAllCategories()

                runOnUiThread {
                    categoryListContainer.removeAllViews()

                    if (categories.isEmpty()) {
                        val emptyText = TextView(this@CategoryActivity)
                        emptyText.text = "No categories added yet"
                        emptyText.textSize = 13f
                        emptyText.setTextColor(android.graphics.Color.parseColor("#6B8B80"))
                        categoryListContainer.addView(emptyText)
                    } else {
                        categories.forEach { category ->

                            val row = LinearLayout(this@CategoryActivity)
                            row.orientation = LinearLayout.HORIZONTAL
                            row.gravity = android.view.Gravity.CENTER_VERTICAL
                            row.setPadding(18, 14, 18, 14)
                            row.setBackgroundResource(R.drawable.category_item_bg)

                            val icon = TextView(this@CategoryActivity)
                            icon.text = "•"
                            icon.textSize = 28f
                            icon.setTextColor(android.graphics.Color.parseColor("#7DB8FF"))

                            val name = TextView(this@CategoryActivity)
                            name.text = category.name
                            name.textSize = 15f
                            name.setTextColor(android.graphics.Color.parseColor("#17323A"))
                            name.setTypeface(null, android.graphics.Typeface.BOLD)
                            name.setPadding(14, 0, 0, 0)

                            row.addView(icon)
                            row.addView(name)

                            val params = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            params.setMargins(0, 0, 0, 12)

                            categoryListContainer.addView(row, params)
                        }
                    }
                }
            }
        }

        loadCategories()

        // Add category button click event
        btnAdd.setOnClickListener {

            // Read category name from input field
            val name = etName.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(this, "Enter a category name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Code Attribution
            //Title: Kotlin Coroutines on Android
            //Author: Android Developers
            //Date: 2024
            //Version: Latest
            //Availability: https://developer.android.com/kotlin/coroutines

            // Insert category into database
            lifecycleScope.launch {
                db.categoryDao().insertCategory(CategoryEntity(name = name))

                runOnUiThread {
                    Toast.makeText(
                        this@CategoryActivity,
                        "Added",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Clear input after saving
                    etName.text.clear()

                    // Refresh category list
                    loadCategories()
                }
            }
        }

        // Navigate to Home screen
        navHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        // Navigate to Analysis screen
        navAnalysis.setOnClickListener {
            startActivity(Intent(this, CategoryTotalActivity::class.java))
        }

        // Navigate to Transactions screen
        navTransactions.setOnClickListener {
            startActivity(Intent(this, ExpenseListActivity::class.java))
        }

        // Current page - Categories screen
        navCategories.setOnClickListener { }

        // Navigate to Profile screen
        navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    // Handles active bottom nav highlight
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