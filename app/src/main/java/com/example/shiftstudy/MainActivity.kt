package com.example.shiftstudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shiftstudy.navigation.BottomNavBar
import com.example.shiftstudy.navigation.BottomNavItem
import com.example.shiftstudy.navigation.ShiftStudyNavHost
import com.example.shiftstudy.ui.theme.ShiftStudyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShiftStudyTheme {
                ShiftStudyApp()
            }
        }
    }
}

@Composable
fun ShiftStudyApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Screens where we want bottom bar + FAB visible
    val mainRoutes = setOf(
        BottomNavItem.Home.route,
        BottomNavItem.Schedule.route,
        BottomNavItem.Tasks.route,
        BottomNavItem.Settings.route
    )
    val showChrome = currentRoute in mainRoutes

    Scaffold(
        bottomBar = {
            if (showChrome) {
                BottomNavBar(navController = navController)
            }
        },
        floatingActionButton = {
            if (showChrome) {
                FloatingActionButton(
                    onClick = { navController.navigate("addTask") },
                    containerColor = Color(0xFFE53935),
                    contentColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add task"
                    )
                }
            }
        }
    ) { innerPadding ->
        ShiftStudyNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
