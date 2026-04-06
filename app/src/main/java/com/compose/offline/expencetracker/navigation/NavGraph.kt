package com.compose.offline.expencetracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.compose.offline.expencetracker.ui.screens.AddExpenseScreen
import com.compose.offline.expencetracker.ui.screens.AnalysisScreen
import com.compose.offline.expencetracker.ui.screens.ExpenseScreen
import com.compose.offline.expencetracker.viewmodel.ExpenseViewModel

sealed class Screen(val route: String) {
    object Expense : Screen("expense")
    object Analysis : Screen("analysis")
    object AddExpense : Screen("add_expense")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: ExpenseViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Expense.route,
        modifier = modifier
    ) {

        composable(Screen.Expense.route) {
            ExpenseScreen(navController, viewModel)
        }

        composable(Screen.Analysis.route) {
            AnalysisScreen(navController, viewModel)
        }

        composable(Screen.AddExpense.route) {
            AddExpenseScreen(navController, viewModel)
        }
    }
}