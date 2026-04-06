package com.compose.offline.expencetracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.compose.offline.expencetracker.getDayLabel
import com.compose.offline.expencetracker.getMonthLabel
import com.compose.offline.expencetracker.getWeekLabel
import com.compose.offline.expencetracker.isDayOffset
import com.compose.offline.expencetracker.isMonthOffset
import com.compose.offline.expencetracker.isWeekOffset
import com.compose.offline.expencetracker.ui.components.ExpensePieChart
import com.compose.offline.expencetracker.ui.components.PeriodTabs
import com.compose.offline.expencetracker.viewmodel.ExpenseViewModel

@Composable
fun AnalysisScreen(navController: NavController, vm: ExpenseViewModel) {

    var tabIndex by remember { mutableStateOf(0) }
    var offset by remember { mutableStateOf(0) }
    val expenses by vm.expenses.collectAsState()

    // Filtered expenses based on tab & offset
    val filtered = remember(expenses, tabIndex, offset) {
        when (tabIndex) {
            0 -> expenses.filter { isDayOffset(it.date, offset) }
            1 -> expenses.filter { isWeekOffset(it.date, offset) }
            else -> expenses.filter { isMonthOffset(it.date, offset) }
        }
    }

    // Pie chart data: group by category
    val pieData = filtered
        .groupBy { it.category }
        .map { (category, list) -> category to list.sumOf { it.amount }.toFloat() }
        .filter { it.second > 0 }
        .sortedByDescending { it.second }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // --- Tabs ---
        PeriodTabs(tabIndex) { tabIndex = it }

        Spacer(modifier = Modifier.height(8.dp))

        // --- Centered period navigator ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { offset -= 1 }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Previous",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = when (tabIndex) {
                    0 -> getDayLabel(offset)
                    1 -> getWeekLabel(offset)
                    else -> getMonthLabel(offset)
                },
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            IconButton(onClick = { offset += 1 }) {
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = "Next",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Pie Chart ---
        Text(
            text = "Spending by Category",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
        ExpensePieChart(pieData)

        Spacer(modifier = Modifier.height(24.dp))
    }
}