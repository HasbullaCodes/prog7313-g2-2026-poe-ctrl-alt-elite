package com.example.ctrlalteliteprog7313.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ctrlalteliteprog7313.R
import com.example.ctrlalteliteprog7313.database.AppDatabase
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.coroutines.launch
import java.util.Calendar

class CategoryTotalActivity : AppCompatActivity() {

    private var startDateDb: String? = null
    private var endDateDb: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_total)
        setActiveNav(R.id.navAnalysis)

        val etStart = findViewById<EditText>(R.id.etStartDate)
        val etEnd = findViewById<EditText>(R.id.etEndDate)
        val btnCalc = findViewById<Button>(R.id.btnCalculate)
        val pieChart = findViewById<PieChart>(R.id.pieChart)

        etStart.setOnClickListener {
            showDatePicker(etStart, true)
        }

        etEnd.setOnClickListener {
            showDatePicker(etEnd, false)
        }

        findViewById<TextView>(R.id.tvBack).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        val db = AppDatabase.getDatabase(this)

        btnCalc.setOnClickListener {
            val start = startDateDb
            val end = endDateDb

            if (start == null || end == null) {
                Toast.makeText(this, "Please select start and end dates", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val totals = db.expenseDao().getTotalsByCategory(start, end)

                val entries = totals.map {
                    PieEntry(it.totalAmount.toFloat(), it.categoryName)
                }

                val dataSet = PieDataSet(entries, "")
                dataSet.setColors(
                    Color.parseColor("#7DB8FF"),
                    Color.parseColor("#A5D6A7"),
                    Color.parseColor("#FFCC80"),
                    Color.parseColor("#CE93D8"),
                    Color.parseColor("#80CBC4"),
                    Color.parseColor("#FF8A80")
                )

                dataSet.valueTextSize = 16f
                dataSet.valueTextColor = Color.WHITE
                dataSet.sliceSpace = 3f
                dataSet.selectionShift = 10f

                val pieData = PieData(dataSet)
                pieData.setValueFormatter(PercentFormatter(pieChart))

                runOnUiThread {
                    if (totals.isEmpty()) {
                        Toast.makeText(
                            this@CategoryTotalActivity,
                            "No expenses found for this period",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    pieChart.data = pieData
                    pieChart.setUsePercentValues(true)

                    pieChart.centerText = "Spending"
                    pieChart.setCenterTextSize(20f)
                    pieChart.setCenterTextColor(Color.parseColor("#17323A"))

                    pieChart.description.isEnabled = false

                    pieChart.setEntryLabelColor(Color.parseColor("#17323A"))
                    pieChart.setEntryLabelTextSize(14f)

                    pieChart.isDrawHoleEnabled = true
                    pieChart.holeRadius = 58f
                    pieChart.transparentCircleRadius = 63f

                    val legend = pieChart.legend
                    legend.textSize = 13f
                    legend.formSize = 12f
                    legend.textColor = Color.parseColor("#17323A")
                    legend.isWordWrapEnabled = true

                    pieChart.animateY(1200)
                    pieChart.invalidate()
                }
            }
        }

        findViewById<LinearLayout>(R.id.navHome).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.navAnalysis).setOnClickListener { }

        findViewById<LinearLayout>(R.id.navTransactions).setOnClickListener {
            startActivity(Intent(this, ExpenseListActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.navCategories).setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.navProfile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
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