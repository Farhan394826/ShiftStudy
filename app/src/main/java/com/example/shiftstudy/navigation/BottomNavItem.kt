package com.example.shiftstudy.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Filled.Home)
    object Schedule : BottomNavItem("schedule", "Schedule", Icons.Filled.CalendarMonth)
    object Tasks : BottomNavItem("tasks", "Tasks", Icons.Filled.Checklist)
}
