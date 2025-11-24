package com.example.shiftstudy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shiftstudy.data.entity.Task
import com.example.shiftstudy.viewmodel.AuthViewModel
import com.example.shiftstudy.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TasksScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel(),
    taskViewModel: TaskViewModel = viewModel()
) {
    // Load tasks when screen opens
    LaunchedEffect(authViewModel.currentUser) {
        authViewModel.currentUser?.let { user ->
            taskViewModel.loadTasksForUser(user.userId)
        }
    }

    var filterStatus by remember { mutableStateOf("All") } // All, Pending, Completed

    // Filter tasks based on selected status
    val filteredTasks = when (filterStatus) {
        "Pending" -> taskViewModel.tasks.filter { !it.isCompleted }
        "Completed" -> taskViewModel.tasks.filter { it.isCompleted }
        else -> taskViewModel.tasks
    }.sortedBy { it.dueDate }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7FB))
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        // Header
        Text(
            text = "My Tasks",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF212121)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${taskViewModel.tasks.size} total tasks",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Filter Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = filterStatus == "All",
                onClick = { filterStatus = "All" },
                label = { Text("All (${taskViewModel.tasks.size})") }
            )
            FilterChip(
                selected = filterStatus == "Pending",
                onClick = { filterStatus = "Pending" },
                label = { Text("Pending (${taskViewModel.tasks.count { !it.isCompleted }})") }
            )
            FilterChip(
                selected = filterStatus == "Completed",
                onClick = { filterStatus = "Completed" },
                label = { Text("Done (${taskViewModel.tasks.count { it.isCompleted }})") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Task List
        if (filteredTasks.isEmpty()) {
            EmptyTasksState(filterStatus)
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredTasks, key = { it.taskId }) { task ->
                    TaskItemCard(
                        task = task,
                        onToggleComplete = {
                            taskViewModel.toggleTaskCompletion(task)
                        },
                        onDelete = {
                            taskViewModel.deleteTask(task)
                        }
                    )
                }

                // Add spacing at bottom for FAB
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
fun TaskItemCard(
    task: Task,
    onToggleComplete: () -> Unit,
    onDelete: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val priorityColor = when (task.priority) {
        "High" -> Color(0xFFE53935)
        "Medium" -> Color(0xFFFF9800)
        "Low" -> Color(0xFF4CAF50)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (task.isCompleted) Color(0xFFF5F5F5) else Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleComplete() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox Icon
            Icon(
                imageVector = if (task.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = if (task.isCompleted) "Completed" else "Mark as complete",
                tint = if (task.isCompleted) Color(0xFF4CAF50) else Color(0xFFBDBDBD),
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Priority Indicator
            Surface(
                shape = CircleShape,
                color = priorityColor,
                modifier = Modifier.size(8.dp)
            ) {}

            Spacer(modifier = Modifier.width(12.dp))

            // Task Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (task.isCompleted) Color.Gray else Color(0xFF212121),
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Category Badge
                    Text(
                        text = task.category,
                        fontSize = 11.sp,
                        color = Color(0xFF1976D2),
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFE3F2FD))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )

                    // Due Date/Time
                    val dateTimeText = if (task.dueTime != null) {
                        "${dateFormat.format(Date(task.dueDate))} • ${task.dueTime}"
                    } else {
                        dateFormat.format(Date(task.dueDate))
                    }

                    Text(
                        text = dateTimeText,
                        fontSize = 11.sp,
                        color = Color(0xFF757575)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Priority Badge
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = priorityColor.copy(alpha = 0.1f)
            ) {
                Text(
                    text = task.priority,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = priorityColor,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            // Delete Button
            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete task",
                    tint = Color(0xFFE53935),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun EmptyTasksState(filterStatus: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = when (filterStatus) {
                    "Completed" -> "📋"
                    "Pending" -> "✅"
                    else -> "📝"
                },
                fontSize = 64.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = when (filterStatus) {
                    "Completed" -> "No completed tasks yet"
                    "Pending" -> "No pending tasks"
                    else -> "No tasks yet"
                },
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = when (filterStatus) {
                    "Completed" -> "Complete some tasks to see them here"
                    "Pending" -> "All tasks are completed! 🎉"
                    else -> "Tap the + button to create your first task"
                },
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}