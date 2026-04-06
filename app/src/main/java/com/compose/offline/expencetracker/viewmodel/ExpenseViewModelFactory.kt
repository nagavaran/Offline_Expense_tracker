package com.compose.offline.expencetracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.compose.offline.expencetracker.data.AppDatabase
import com.compose.offline.expencetracker.data.ExpenseRepository

class ExpenseViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = AppDatabase.getDatabase(context)
        val dao = db.expenseDao()
        val repo = ExpenseRepository(dao)

        @Suppress("UNCHECKED_CAST")
        return ExpenseViewModel(repo) as T
    }
}