package com.example.shiftstudy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shiftstudy.data.entity.Task
import com.example.shiftstudy.viewmodel.AuthViewModel
import com.example.shiftstudy.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class DaySchedule(
    val dayName: String,
    val date: String,
    val isToday: Boolean,
    val tasks: List<Task>
)

@Composable
fun ScheduleScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel(),
    taskViewModel: TaskViewModel = viewModel()
) {
    // Load tasks
    LaunchedEffect(authViewModel.currentUser) {
        authViewModel.currentUser?.let { user ->
            taskViewModel.loadTasksForUser(user.userId)
        }
    }

    val calendar = Calendar.getInstance()
    val today = calendar.clone() as Calendar

    // Set to Monday
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

    // Create 7 days (Mon-Sun)
    val weekSchedule = (0..6).map { dayOffset ->
        val dayCalendar = calendar.clone() as Calendar
        dayCalendar.add(Calendar.DAY_OF_MONTH, dayOffset)

        val startOfDay = dayCalendar.clone() as Calendar
        startOfDay.set(Calendar.HOUR_OF_DAY, 0)
        startOfDay.set(Calendar.MINUTE, 0)
        startOfDay.set(Calendar.SECOND, 0)

        val endOfDay = dayCalendar.clone() as Calendar
        endOfDay.set(Calendar.HOUR_OF_DAY, 23)
        endOfDay.set(Calendar.MINUTE, 59)
        endOfDay.set(Calendar.SECOND, 59)

        val dayTasks = taskViewModel.tasks.filter { task ->
            task.dueDate >= startOfDay.timeInMillis && task.dueDate <= endOfDay.timeInMillis
        }.sortedBy { it.dueTime ?: "00:00" }

        val isToday = dayCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                dayCalendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)

        DaySchedule(
            dayName = SimpleDateFormat("EEEE", Locale.getDefault()).format(dayCalendar.time),
            date = SimpleDateFormat("MMM dd", Locale.getDefault()).format(dayCalendar.time),
            isToday = isToday,
            tasks = dayTasks
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7FB))
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Weekly Schedule",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                Text(
                    text = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date()),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            Text(
                text = "📅",
                fontSize = 32.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Week Schedule
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(weekSchedule) { daySchedule ->
                DayCard(daySchedule)
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun DayCard(daySchedule: DaySchedule) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (daySchedule.isToday) Color(0xFFE3F2FD) else Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (daySchedule.isToday) 4.dp else 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Day Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (daySchedule.isToday) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF2196F3),
                            modifier = Modifier.size(8.dp)
                        ) {}
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Text(
                        text = daySchedule.dayName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (daySchedule.isToday) Color(0xFF1976D2) else Color(0xFF212121)
                    )
                }

                Text(
                    text = daySchedule.date,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            if (daySchedule.tasks.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))

                Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)

                Spacer(modifier = Modifier.height(12.dp))

                daySchedule.tasks.forEach { task ->
                    TaskRow(task)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            } else {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "No tasks scheduled",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun TaskRow(task: Task) {
    val priorityColor = when (task.priority) {
        "High" -> Color(0xFFE53935)
        "Medium" -> Color(0xFFFF9800)
        "Low" -> Color(0xFF4CAF50)
        else -> Color.Gray
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Time
        Text(
            text = task.dueTime ?: "All day",
            fontSize = 12.sp,
            color = Color(0xFF757575),
            modifier = Modifier.width(60.dp),
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Priority Indicator
        Surface(
            shape = CircleShape,
            color = priorityColor,
            modifier = Modifier.size(6.dp)
        ) {}

        Spacer(modifier = Modifier.width(8.dp))

        // Task Info
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = task.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF212121)
            )

            Text(
                text = task.category,
                fontSize = 11.sp,
                color = Color(0xFF757575)
            )
        }

        // Priority Badge
        Surface(
            shape = RoundedCornerShape(6.dp),
            color = priorityColor.copy(alpha = 0.1f)
        ) {
            Text(
                text = task.priority,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = priorityColor,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
            )
        }
    }
}