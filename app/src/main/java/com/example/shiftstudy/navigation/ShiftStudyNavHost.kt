package com.example.shiftstudy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shiftstudy.screens.AddTaskScreen
import com.example.shiftstudy.screens.HomeScreen
import com.example.shiftstudy.screens.LoginScreen
import com.example.shiftstudy.screens.ScheduleScreen
import com.example.shiftstudy.screens.SignupScreen
import com.example.shiftstudy.screens.TasksScreen

@Composable
fun ShiftStudyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("signup") {
            SignupScreen(navController)
        }

        composable(BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(BottomNavItem.Schedule.route) {
            ScheduleScreen()
        }
        composable(BottomNavItem.Tasks.route) {
            TasksScreen()
        }
        composable("addTask") {
            AddTaskScreen(navController)
        }
    }
}
