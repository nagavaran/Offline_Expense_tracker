package com.compose.offline.expencetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.compose.offline.expencetracker.ui.screens.MainScreen
import com.compose.offline.expencetracker.ui.theme.ExpenceTrackerTheme
import com.compose.offline.expencetracker.viewmodel.ExpenseViewModel
import com.compose.offline.expencetracker.viewmodel.ExpenseViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: ExpenseViewModel by viewModels {
        ExpenseViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenceTrackerTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}