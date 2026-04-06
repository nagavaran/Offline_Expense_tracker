package com.compose.offline.expencetracker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomNav(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar {

        NavigationBarItem(
            selected = currentRoute == "expense",
            onClick = { onNavigate("expense") },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Expense") }
        )

        NavigationBarItem(
            selected = currentRoute == "analysis",
            onClick = { onNavigate("analysis") },
            icon = { Icon(Icons.Default.BarChart, contentDescription = null) },
            label = { Text("Analysis") }
        )
    }
}