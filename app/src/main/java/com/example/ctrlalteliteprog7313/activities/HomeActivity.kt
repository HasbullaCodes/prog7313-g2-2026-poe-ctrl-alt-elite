package com.example.ctrlalteliteprog7313.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ctrlalteliteprog7313.R
import com.example.ctrlalteliteprog7313.database.AppDatabase
import kotlinx.coroutines.launch

// Main dashboard activity of the application
class HomeActivity : AppCompatActivity() {

    // Database reference
    private lateinit var db: AppDatabase

    private var userName: String? = null
    private var userEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userName = intent.getStringExtra("userName")
        userEmail = intent.getStringExtra("userEmail")

        // Set layout for home screen
        setContentView(R.layout.activity_home)

        // Highlight active nav
        setActiveNav(R.id.navHome)

        // Get database instance
        db = AppDatabase.getDatabase(this)

        // Open Add Expense screen
        findViewById<Button>(R.id.btnAddExpense).setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        // Open Expense List screen
        findViewById<Button>(R.id.btnViewExpenses).setOnClickListener {
            startActivity(Intent(this, ExpenseListActivity::class.java))
        }

        // Current page - Home screen
        findViewById<LinearLayout>(R.id.navHome).setOnClickListener { }

        // Navigate to Analysis screen
        findViewById<LinearLayout>(R.id.navAnalysis).setOnClickListener {
            startActivity(Intent(this, CategoryTotalActivity::class.java))
        }

        // Navigate to Transactions screen
        findViewById<LinearLayout>(R.id.navTransactions).setOnClickListener {
            startActivity(Intent(this, ExpenseListActivity::class.java))
        }

        // Navigate to Categories screen
        findViewById<LinearLayout>(R.id.navCategories).setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }

        // Navigate to Goal/Profile screen
        findViewById<LinearLayout>(R.id.navProfile).setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("userName", userName)
            intent.putExtra("userEmail", userEmail)
            startActivity(intent)
        }

        // Load dashboard values
        loadDashboardData()
    }

    override fun onResume() {
        super.onResume()

        // Refresh dashboard when returning to screen
        loadDashboardData()
    }

    // Loads dashboard statistics and recent transactions
    private fun loadDashboardData() {
        val tvBalanceAmount = findViewById<TextView>(R.id.tvBalanceAmount)
        val tvExpenseAmount = findViewById<TextView>(R.id.tvExpenseAmount)
        val tvGoalPercent = findViewById<TextView>(R.id.tvGoalPercent)
        val tvGoalMax = findViewById<TextView>(R.id.tvGoalMax)
        val tvProgressText = findViewById<TextView>(R.id.tvProgressText)
        val tvRevenueLastWeek = findViewById<TextView>(R.id.tvRevenueLastWeek)
        val tvFoodLastWeek = findViewById<TextView>(R.id.tvFoodLastWeek)
        val recentContainer = findViewById<LinearLayout>(R.id.recentTransactionsContainer)

        //Code Attribution
        //Title: Kotlin Coroutines on Android
        //Author: Android Developers
        //Date: 2024
        //Version: Latest
        //Availability: https://developer.android.com/kotlin/coroutines
        lifecycleScope.launch {
            val expenses = db.expenseDao().getAllExpensesWithCategory()
            val totalExpense = expenses.sumOf { it.amount }
            val recentExpenses = expenses.take(3)

            runOnUiThread {
                tvBalanceAmount.text = "R%.2f".format(totalExpense)
                tvExpenseAmount.text = "-R%.2f".format(totalExpense)

                tvGoalMax.text = "R20,000.00"

                val percent =
                    if (totalExpense > 0) ((totalExpense / 20000.0) * 100).toInt()
                    else 0

                tvGoalPercent.text = "$percent%"
                tvProgressText.text = "☑ $percent% Of Your Expenses, Looks Good."

                tvRevenueLastWeek.text = "R0.00"
                tvFoodLastWeek.text = "-R%.2f".format(totalExpense)

                recentContainer.removeAllViews()

                if (recentExpenses.isEmpty()) {
                    val emptyText = TextView(this@HomeActivity)
                    emptyText.text = "No transactions yet"
                    emptyText.textSize = 14f
                    emptyText.setTextColor(Color.parseColor("#344B4C"))
                    recentContainer.addView(emptyText)
                } else {
                    recentExpenses.forEach { expense ->
                        val row = TextView(this@HomeActivity)
                        row.text =
                            "${expense.categoryName}\n${expense.startTime} - ${expense.date}     ${expense.description}     R%.2f"
                                .format(expense.amount)
                        row.textSize = 13f
                        row.setTextColor(Color.parseColor("#17323A"))
                        row.setPadding(8, 12, 8, 12)
                        recentContainer.addView(row)
                    }
                }

                Log.d("APP_DEBUG", "Dashboard total expense loaded: $totalExpense")
            }
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