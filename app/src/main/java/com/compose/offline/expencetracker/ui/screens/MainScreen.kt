package com.compose.offline.expencetracker.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.compose.offline.expencetracker.navigation.NavGraph
import com.compose.offline.expencetracker.navigation.Screen
import com.compose.offline.expencetracker.ui.components.BottomNav
import com.compose.offline.expencetracker.ui.components.TopBar
import com.compose.offline.expencetracker.viewmodel.ExpenseViewModel

@Composable
fun MainScreen(viewModel: ExpenseViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopBar(
                currentRoute = currentRoute,
                onAddClick = {
                    navController.navigate(Screen.AddExpense.route)
                }
            )
        },
        bottomBar = {
            if (currentRoute != Screen.AddExpense.route) {
                BottomNav(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Expense.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { padding ->
        NavGraph(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(padding)
        )
    }
}