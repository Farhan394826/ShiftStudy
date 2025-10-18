@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.shiftstudy  // <-- keep your actual package name here

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { ShiftStudyApp() } }
    }
}

/* -------------------- App Shell -------------------- */

enum class Tab(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Planner("Planner", Icons.Filled.CalendarMonth),
    Tasks("Tasks", Icons.Filled.CheckCircle),
    Timer("Timer", Icons.Filled.Timer)
}

@Composable
fun ShiftStudyApp() {
    var current by remember { mutableStateOf(Tab.Planner) }

    Scaffold(
        topBar = { TopAppBar(title = { Text(current.label) }) },
        bottomBar = {
            NavigationBar {
                Tab.values().forEach { t ->
                    NavigationBarItem(
                        selected = t == current,
                        onClick = { current = t },
                        icon = { Icon(t.icon, contentDescription = t.label) },
                        label = { Text(t.label) }
                    )
                }
            }
        }
    ) { inner ->
        Box(Modifier.padding(inner)) {
            when (current) {
                Tab.Planner -> PlannerScreen()
                Tab.Tasks   -> TasksScreen()
                Tab.Timer   -> TimerScreen()
            }
        }
    }
}

/* -------------------- Planner Screen -------------------- */

data class PlannerItem(val title: String, val subtitle: String, val tag: String)

@Composable
fun PlannerScreen(items: List<PlannerItem> = demoPlanner) {
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        items(items) { it.PlannerCard() }
    }
}

@Composable
private fun PlannerItem.PlannerCard() {
    Card(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(subtitle, style = MaterialTheme.typography.bodyMedium)
            }
            AssistChip(onClick = { /* edit */ }, label = { Text(tag) })
        }
    }
}

private val demoPlanner = listOf(
    PlannerItem("COS201 Lecture", "Mon 9:00–11:00, Building A", "Subject"),
    PlannerItem("Shift at 7-Eleven", "Mon 17:00–21:00", "Work"),
    PlannerItem("Math Tutorial", "Tue 10:00–11:00, Room 204", "Subject"),
    PlannerItem("Delivery Shift", "Wed 18:00–22:00", "Work")
)

/* -------------------- Tasks Screen -------------------- */

data class Task(val title: String, val due: String, val progress: Float)

@Composable
fun TasksScreen(tasks: List<Task> = demoTasks) {
    Box(Modifier.fillMaxSize()) {
        LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
            items(tasks) { t -> TaskRow(t) }
        }
        ExtendedFloatingActionButton(
            onClick = { /* add task */ },
            text = { Text("Add Task") },
            icon = { Icon(Icons.Filled.CheckCircle, contentDescription = null) },
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        )
    }
}

@Composable
private fun TaskRow(t: Task) {
    Card(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text(t.title, style = MaterialTheme.typography.titleMedium)
            Text("Due: ${t.due}", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(progress = { t.progress }, modifier = Modifier.fillMaxWidth())
        }
    }
}

private val demoTasks = listOf(
    Task("CP5307 Part B slides", "Fri", 0.6f),
    Task("DB Assignment", "Oct 28", 0.25f),
    Task("Cyber Security Quiz", "Nov 2", 0.1f)
)

/* -------------------- Timer Screen -------------------- */

@Composable
fun TimerScreen() {
    var seconds by remember { mutableStateOf(25 * 60) } // 25 minutes
    var running by remember { mutableStateOf(false) }

    LaunchedEffect(running) {
        while (running && seconds > 0) {
            delay(1000)
            seconds--
        }
    }

    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "%02d:%02d".format(seconds / 60, seconds % 60),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))
        Row {
            Button(onClick = { running = true }) { Text("Start") }
            Spacer(Modifier.width(12.dp))
            OutlinedButton(onClick = { running = false }) { Text("Pause") }
            Spacer(Modifier.width(12.dp))
            TextButton(onClick = { seconds = 25 * 60 }) { Text("Reset") }
        }
    }
}

/* -------------------- Previews (for screenshots) -------------------- */

@Preview(showBackground = true, widthDp = 390)
@Composable fun PlannerPreview() { MaterialTheme { PlannerScreen() } }

@Preview(showBackground = true, widthDp = 390)
@Composable fun TasksPreview() { MaterialTheme { TasksScreen() } }

@Preview(showBackground = true, widthDp = 390)
@Composable fun TimerPreview() { MaterialTheme { TimerScreen() } }
