package com.example.ctrlalteliteprog7313.database

data class ExpenseWithCategory(
    val amount: Double,
    val description: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val imagePath: String?,
    val categoryName: String
)