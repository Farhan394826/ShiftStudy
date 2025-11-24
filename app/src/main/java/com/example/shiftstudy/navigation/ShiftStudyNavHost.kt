package com.example.shiftstudy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shiftstudy.screens.AddTaskScreen
import com.example.shiftstudy.screens.HomeScreen
import com.example.shiftstudy.screens.LoginScreen
import com.example.shiftstudy.screens.ScheduleScreen
import com.example.shiftstudy.screens.SignupScreen
import com.example.shiftstudy.screens.TasksScreen
import com.example.shiftstudy.viewmodel.AuthViewModel
import com.example.shiftstudy.viewmodel.TaskViewModel

@Composable
fun ShiftStudyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // Create ViewModels at NavHost level so they're shared across screens
    val authViewModel: AuthViewModel = viewModel()
    val taskViewModel: TaskViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(navController, authViewModel)
        }
        composable("signup") {
            SignupScreen(navController, authViewModel)
        }

        composable(BottomNavItem.Home.route) {
            HomeScreen(navController, authViewModel, taskViewModel)
        }
        composable(BottomNavItem.Schedule.route) {
            ScheduleScreen(navController, authViewModel, taskViewModel)
        }
        composable(BottomNavItem.Tasks.route) {
            TasksScreen(navController, authViewModel, taskViewModel)
        }
        composable("addTask") {
            AddTaskScreen(navController, authViewModel, taskViewModel)
        }
    }
}