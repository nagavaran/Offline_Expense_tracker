package com.compose.offline.expencetracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.compose.offline.expencetracker.getDayLabel
import com.compose.offline.expencetracker.getFormattedDate
import com.compose.offline.expencetracker.getMonthLabel
import com.compose.offline.expencetracker.getWeekLabel
import com.compose.offline.expencetracker.isDayOffset
import com.compose.offline.expencetracker.isMonthOffset
import com.compose.offline.expencetracker.isWeekOffset
import com.compose.offline.expencetracker.ui.components.PeriodTabs
import com.compose.offline.expencetracker.viewmodel.ExpenseViewModel

@Composable
fun ExpenseScreen(navController: NavController, vm: ExpenseViewModel) {

    var tabIndex by remember { mutableStateOf(0) }
    val expenses by vm.expenses.collectAsState()

    var offset by remember { mutableStateOf(0) }

    val periodLabel = remember(tabIndex, offset) {
        when (tabIndex) {
            0 -> getDayLabel(offset)
            1 -> getWeekLabel(offset)
            else -> getMonthLabel(offset)
        }
    }

    val filteredList = remember(expenses, tabIndex, offset) {
        when (tabIndex) {
            0 -> expenses.filter { isDayOffset(it.date, offset) }
            1 -> expenses.filter { isWeekOffset(it.date, offset) }
            else -> expenses.filter { isMonthOffset(it.date, offset) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // 🔹 Tabs
        PeriodTabs(tabIndex) { tabIndex = it }

        Spacer(modifier = Modifier.height(8.dp))

        // 🔹 Date Navigator
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
                text = periodLabel,
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

        Spacer(modifier = Modifier.height(8.dp))

        // 🔹 Expense List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp)
        ) {

            // ✅ Empty State
            if (filteredList.isEmpty()) {
                item {
                    Text(
                        "No expenses found",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
            }

            items(filteredList) { item ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        // 🔹 Top Row (Title + Amount)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = item.note,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = "₹${item.amount}",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // 🔹 Category
                        Text(
                            text = item.category,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        // 🔹 Date
                        Text(
                            text = getFormattedDate(item.date),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}