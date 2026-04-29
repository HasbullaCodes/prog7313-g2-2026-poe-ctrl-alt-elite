package com.example.ctrlalteliteprog7313.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ctrlalteliteprog7313.R
import com.example.ctrlalteliteprog7313.database.AppDatabase
import com.example.ctrlalteliteprog7313.database.ExpenseWithCategory
import kotlinx.coroutines.launch
import java.util.Calendar

class ExpenseListActivity : AppCompatActivity() {

    private var currentExpenses: List<ExpenseWithCategory> = emptyList()

    private var startDateDb: String? = null
    private var endDateDb: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_list)
        setActiveNav(R.id.navTransactions)

        // Back button
        findViewById<TextView>(R.id.tvBack).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        val etStart = findViewById<EditText>(R.id.etStartDate)
        val etEnd = findViewById<EditText>(R.id.etEndDate)
        val btnFilter = findViewById<Button>(R.id.btnFilter)
        val listView = findViewById<ListView>(R.id.listViewExpenses)

        val navHome = findViewById<LinearLayout>(R.id.navHome)
        val navAnalysis = findViewById<LinearLayout>(R.id.navAnalysis)
        val navTransactions = findViewById<LinearLayout>(R.id.navTransactions)
        val navCategories = findViewById<LinearLayout>(R.id.navCategories)
        val navProfile = findViewById<LinearLayout>(R.id.navProfile)

        val db = AppDatabase.getDatabase(this)

        etStart.setOnClickListener {
            showDatePicker(etStart, true)
        }

        etEnd.setOnClickListener {
            showDatePicker(etEnd, false)
        }

        fun display(expenses: List<ExpenseWithCategory>) {
            currentExpenses = expenses

            val adapter = object : ArrayAdapter<ExpenseWithCategory>(
                this, R.layout.item_expense, expenses
            ) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = layoutInflater.inflate(R.layout.item_expense, parent, false)
                    val item = getItem(position)

                    view.findViewById<TextView>(R.id.tvCategory).text = item?.categoryName
                    view.findViewById<TextView>(R.id.tvAmount).text = "R%.2f".format(item?.amount ?: 0.0)
                    view.findViewById<TextView>(R.id.tvDescription).text = item?.description
                    view.findViewById<TextView>(R.id.tvDate).text =
                        "${item?.date} ${item?.startTime}-${item?.endTime}"

                    return view
                }
            }

            listView.adapter = adapter
        }

        //Code Attribution
        //Title: Kotlin Coroutines on Android
        //Author: Android Developers
        //Date: 2024
        //Version: Latest
        //Availability: https://developer.android.com/kotlin/coroutines
        lifecycleScope.launch {
            val data = db.expenseDao().getAllExpensesWithCategory()
            runOnUiThread { display(data) }
        }

        btnFilter.setOnClickListener {
            val start = startDateDb
            val end = endDateDb

            if (start == null || end == null) {
                Toast.makeText(this, "Please select start and end dates", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val data = db.expenseDao().getExpensesByDateWithCategory(start, end)

                runOnUiThread {
                    if (data.isEmpty()) {
                        Toast.makeText(
                            this@ExpenseListActivity,
                            "No expenses found for this period",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    display(data)
                }
            }
        }

        navHome.setOnClickListener { startActivity(Intent(this, HomeActivity::class.java)) }
        navAnalysis.setOnClickListener { startActivity(Intent(this, CategoryTotalActivity::class.java)) }
        navTransactions.setOnClickListener { }
        navCategories.setOnClickListener { startActivity(Intent(this, CategoryActivity::class.java)) }
        navProfile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
    }

    private fun showDatePicker(editText: EditText, isStartDate: Boolean) {
        val calendar = Calendar.getInstance()

        val dialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val displayDate = "%02d/%02d/%04d".format(dayOfMonth, month + 1, year)
                val dbDate = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)

                editText.setText(displayDate)

                if (isStartDate) {
                    startDateDb = dbDate
                } else {
                    endDateDb = dbDate
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        dialog.show()
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