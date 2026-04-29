package com.example.ctrlalteliteprog7313.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GoalDao {

    @Insert
    suspend fun insertGoal(goal: GoalEntity)

    @Query("SELECT * FROM goals WHERE month = :month LIMIT 1")
    suspend fun getGoalByMonth(month: String): GoalEntity?
}