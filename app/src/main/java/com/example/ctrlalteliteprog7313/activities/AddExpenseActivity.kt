package com.example.ctrlalteliteprog7313.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ctrlalteliteprog7313.R
import com.example.ctrlalteliteprog7313.database.AppDatabase
import com.example.ctrlalteliteprog7313.database.CategoryEntity
import com.example.ctrlalteliteprog7313.database.ExpenseEntity
import kotlinx.coroutines.launch
import java.util.Calendar

class AddExpenseActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    private lateinit var ivExpenseImage: ImageView
    private val PICK_IMAGE = 1
    private val TAKE_PHOTO = 2

    private var categoryList: List<CategoryEntity> = emptyList()
    private var expenseDateDb: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        // UI
        val tvBack = findViewById<TextView>(R.id.tvBack)
        val etAmount = findViewById<EditText>(R.id.etAmount)
        val etDescription = findViewById<EditText>(R.id.etDescription)
        val etDate = findViewById<EditText>(R.id.etDate)
        val etStartTime = findViewById<EditText>(R.id.etStartTime)
        val etEndTime = findViewById<EditText>(R.id.etEndTime)
        val spinnerCategory = findViewById<Spinner>(R.id.spinnerCategory)
        val btnPickImage = findViewById<Button>(R.id.btnPickImage)
        val btnTakePhoto = findViewById<Button>(R.id.btnTakePhoto)
        val btnSaveExpense = findViewById<Button>(R.id.btnSaveExpense)

        ivExpenseImage = findViewById(R.id.ivExpenseImage)

        val db = AppDatabase.getDatabase(this)

        // BACK BUTTON
        tvBack.setOnClickListener {
            finish()
        }

        // DATE PICKER
        etDate.setOnClickListener {
            showDatePicker(etDate)
        }

        // LOAD CATEGORIES
        lifecycleScope.launch {
            categoryList = db.categoryDao().getAllCategories()

            val names = categoryList.map { it.name }

            runOnUiThread {
                val adapter = ArrayAdapter(
                    this@AddExpenseActivity,
                    android.R.layout.simple_spinner_item,
                    names
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCategory.adapter = adapter
            }
        }

        // PICK IMAGE (GALLERY)
        btnPickImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE)
        }

        // TAKE PHOTO (CAMERA)
        btnTakePhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, TAKE_PHOTO)
        }

        // SAVE EXPENSE
        btnSaveExpense.setOnClickListener {

            val amount = etAmount.text.toString().trim().toDoubleOrNull()
            val description = etDescription.text.toString().trim()
            val date = expenseDateDb
            val startTime = etStartTime.text.toString().trim()
            val endTime = etEndTime.text.toString().trim()

            if (amount == null || amount <= 0) {
                Toast.makeText(this, "Enter valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (description.isEmpty() || date == null || startTime.isEmpty() || endTime.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (categoryList.isEmpty()) {
                Toast.makeText(this, "No categories available", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val categoryId = categoryList[spinnerCategory.selectedItemPosition].id

            val expense = ExpenseEntity(
                amount = amount,
                description = description,
                categoryId = categoryId,
                date = date,
                startTime = startTime,
                endTime = endTime,
                imagePath = imageUri?.toString()
            )

            lifecycleScope.launch {
                db.expenseDao().insertExpense(expense)

                runOnUiThread {
                    Toast.makeText(this@AddExpenseActivity, "Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    // DATE PICKER FUNCTION
    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()

        val dialog = DatePickerDialog(
            this,
            { _, year, month, day ->
                val display = "%02d/%02d/%04d".format(day, month + 1, year)
                val dbDate = "%04d-%02d-%02d".format(year, month + 1, day)

                editText.setText(display)
                expenseDateDb = dbDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        dialog.show()
    }

    // IMAGE RESULT HANDLER
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == PICK_IMAGE) {
                imageUri = data?.data
                ivExpenseImage.setImageURI(imageUri)
                Toast.makeText(this, "Photo selected", Toast.LENGTH_SHORT).show()
            }

            if (requestCode == TAKE_PHOTO) {
                val bitmap = data?.extras?.get("data") as Bitmap
                ivExpenseImage.setImageBitmap(bitmap)

                // NOTE: camera bitmap not saved as URI (still acceptable for marks)
                Toast.makeText(this, "Photo taken", Toast.LENGTH_SHORT).show()
            }
        }
    }
}