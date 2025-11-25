package com.example.shiftstudy.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Task
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Schedule : BottomNavItem("schedule", Icons.Default.CalendarToday, "Schedule")
    object Tasks : BottomNavItem("tasks", Icons.Default.Task, "Tasks")
    object Settings : BottomNavItem("settings", Icons.Default.Settings, "Settings")
}