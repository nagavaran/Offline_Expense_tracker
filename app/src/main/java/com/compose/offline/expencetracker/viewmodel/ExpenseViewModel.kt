package com.compose.offline.expencetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.offline.expencetracker.data.ExpenseEntity
import com.compose.offline.expencetracker.data.ExpenseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExpenseViewModel(
    private val repo: ExpenseRepository
) : ViewModel() {

    val expenses = repo.getAllExpenses()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addExpense(amount: Double, note: String, date: Long, category: String) {
        viewModelScope.launch {
            repo.insertExpense(
                ExpenseEntity(amount = amount, note = note, date = date, category = category)
            )
        }
    }
}