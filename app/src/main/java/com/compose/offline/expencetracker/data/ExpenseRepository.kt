package com.compose.offline.expencetracker.data

import kotlinx.coroutines.flow.Flow

class ExpenseRepository(
    private val dao: ExpenseDao
) {

    fun getAllExpenses(): Flow<List<ExpenseEntity>> {
        return dao.getAll()
    }

    suspend fun insertExpense(expense: ExpenseEntity) {
        dao.insert(expense)
    }
}