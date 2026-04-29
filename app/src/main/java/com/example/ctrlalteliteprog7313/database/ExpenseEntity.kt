package com.example.ctrlalteliteprog7313.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val amount: Double,
    val description: String,
    val categoryId: Int,
    val date: String,
    val startTime: String,
    val endTime: String,
    val imagePath: String? // NEW
)