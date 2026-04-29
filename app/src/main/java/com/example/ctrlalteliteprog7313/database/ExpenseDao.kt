package com.example.ctrlalteliteprog7313.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("""
        SELECT 
            expenses.amount AS amount,
            expenses.description AS description,
            expenses.date AS date,
            expenses.startTime AS startTime,
            expenses.endTime AS endTime,
            expenses.imagePath AS imagePath,
            categories.name AS categoryName
        FROM expenses
        INNER JOIN categories ON expenses.categoryId = categories.id
        ORDER BY expenses.date DESC
    """)
    suspend fun getAllExpensesWithCategory(): List<ExpenseWithCategory>

    @Query("""
        SELECT 
            expenses.amount AS amount,
            expenses.description AS description,
            expenses.date AS date,
            expenses.startTime AS startTime,
            expenses.endTime AS endTime,
            expenses.imagePath AS imagePath,
            categories.name AS categoryName
        FROM expenses
        INNER JOIN categories ON expenses.categoryId = categories.id
        WHERE expenses.date BETWEEN :startDate AND :endDate
        ORDER BY expenses.date DESC
    """)
    suspend fun getExpensesByDateWithCategory(startDate: String, endDate: String): List<ExpenseWithCategory>

    @Query("""
        SELECT 
            categories.name AS categoryName,
            SUM(expenses.amount) AS totalAmount
        FROM expenses
        INNER JOIN categories ON expenses.categoryId = categories.id
        WHERE expenses.date BETWEEN :startDate AND :endDate
        GROUP BY categories.name
        ORDER BY categories.name ASC
    """)
    suspend fun getTotalsByCategory(startDate: String, endDate: String): List<CategoryTotal>
}