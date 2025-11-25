package com.example.shiftstudy.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shiftstudy.data.entity.Task
import com.example.shiftstudy.viewmodel.AuthViewModel
import com.example.shiftstudy.viewmodel.QuoteState
import com.example.shiftstudy.viewmodel.QuoteViewModel
import com.example.shiftstudy.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel(),
    taskViewModel: TaskViewModel = viewModel(),
    quoteViewModel: QuoteViewModel = viewModel()
) {
    // Load tasks when screen opens
    LaunchedEffect(authViewModel.currentUser) {
        authViewModel.currentUser?.let { user ->
            taskViewModel.loadTasksForUser(user.userId)
        }
        // Fetch quote when screen loads
        quoteViewModel.fetchQuote()
    }

    // Date using SimpleDateFormat
    val today = Date()
    val dayName = SimpleDateFormat("EEEE", Locale.getDefault()).format(today)
    val dateText = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault()).format(today)

    val totalTasks = taskViewModel.tasks.size
    val completedTasks = taskViewModel.tasks.count { it.isCompleted }
    val pendingTasks = taskViewModel.tasks.count { !it.isCompleted }

    // Get today's tasks
    val calendar = Calendar.getInstance()
    calendar.time = today
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    val startOfDay = calendar.timeInMillis
    calendar.add(Calendar.DAY_OF_MONTH, 1)
    val endOfDay = calendar.timeInMillis

    val todayTasks = taskViewModel.tasks.filter { task ->
        task.dueDate in startOfDay until endOfDay
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7FB))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // Header
        Text(
            text = "Welcome back,",
            fontSize = 16.sp,
            color = Color.Gray
        )
        Text(
            text = authViewModel.currentUser?.name ?: "Student",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF212121)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "$dayName, $dateText",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Quote Card
        QuoteCard(quoteViewModel)

        Spacer(modifier = Modifier.height(24.dp))

        // Stats Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                title = "Total",
                value = totalTasks.toString(),
                icon = Icons.Default.Task,
                backgroundColor = Color(0xFF2196F3),
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Completed",
                value = completedTasks.toString(),
                icon = Icons.Default.CheckCircle,
                backgroundColor = Color(0xFF4CAF50),
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Pending",
                value = pendingTasks.toString(),
                icon = Icons.Default.PendingActions,
                backgroundColor = Color(0xFFFF9800),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Today's Tasks Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Today's Tasks",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121)
            )
            Text(
                text = "${todayTasks.size} tasks",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (todayTasks.isEmpty()) {
            EmptyStateCard()
        } else {
            todayTasks.forEach { task ->
                TaskCard(task = task)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Upcoming Tasks Section
        val upcomingTasks = taskViewModel.tasks.filter { task ->
            task.dueDate >= endOfDay && !task.isCompleted
        }.sortedBy { it.dueDate }.take(5)

        if (upcomingTasks.isNotEmpty()) {
            Text(
                text = "Upcoming",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121)
            )

            Spacer(modifier = Modifier.height(12.dp))

            upcomingTasks.forEach { task ->
                TaskCard(task = task)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun QuoteCard(quoteViewModel: QuoteViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF6A1B9A)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.FormatQuote,
                        contentDescription = "Quote",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = "Quote of the Day",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                IconButton(
                    onClick = { quoteViewModel.fetchQuote() },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh Quote",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (val state = quoteViewModel.quoteState) {
                is QuoteState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
                is QuoteState.Success -> {
                    Text(
                        text = "\"${state.quote.content}\"",
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.White,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "— ${state.quote.author}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
                is QuoteState.Error -> {
                    Text(
                        text = "Failed to load quote. Tap refresh to try again.",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                is QuoteState.Idle -> {
                    Text(
                        text = "Loading inspirational quote...",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.3f),
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = title,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

@Composable
fun TaskCard(task: Task) {
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
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Priority Indicator
            Surface(
                shape = CircleShape,
                color = priorityColor,
                modifier = Modifier
                    .size(8.dp)
            ) {}

            Spacer(modifier = Modifier.padding(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF212121)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = task.category,
                        fontSize = 12.sp,
                        color = Color(0xFF757575),
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFE3F2FD))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                    val dateTimeText = if (task.dueTime != null) {
                        "${dateFormat.format(Date(task.dueDate))} at ${task.dueTime}"
                    } else {
                        dateFormat.format(Date(task.dueDate))
                    }

                    Text(
                        text = dateTimeText,
                        fontSize = 12.sp,
                        color = Color(0xFF757575)
                    )
                }
            }

            // Status Badge
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = priorityColor.copy(alpha = 0.1f)
            ) {
                Text(
                    text = task.priority,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = priorityColor,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun EmptyStateCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🎉",
                fontSize = 48.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "No tasks for today!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tap the + button to add a new task",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}