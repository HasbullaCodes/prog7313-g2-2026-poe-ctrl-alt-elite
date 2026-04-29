package com.example.ctrlalteliteprog7313.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class GoalEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val month: String,
    val minGoal: Double,
    val maxGoal: Double
)