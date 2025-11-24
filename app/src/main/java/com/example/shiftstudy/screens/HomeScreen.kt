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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen() {
    // Date using SimpleDateFormat so it works on minSdk 24
    val today = Date()
    val dayName = SimpleDateFormat("EEEE", Locale.getDefault()).format(today)
    val dateText = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault()).format(today)

    var profileMenuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7FB))
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // ------------------------------------------------
        // Top row: Day + Date + Profile icon with menu
        // ------------------------------------------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = dayName,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = dateText,
                    fontSize = 16.sp,
                    color = Color(0xFF757575)
                )
            }

            Box {
                IconButton(onClick = { profileMenuExpanded = true }) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Profile",
                        tint = Color(0xFF1E88E5),
                        modifier = Modifier.size(32.dp)
                    )
                }

                DropdownMenu(
                    expanded = profileMenuExpanded,
                    onDismissRequest = { profileMenuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Farhan Imtiaz") },
                        onClick = { profileMenuExpanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("Logout") },
                        onClick = {
                            profileMenuExpanded = false
                            // TODO: later navigate back to Login screen here
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ------------------------------------------------
        // Summary chips row
        // ------------------------------------------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SummaryChip(
                title = "Classes",
                value = "0",
                modifier = Modifier.weight(1f)
            )
            SummaryChip(
                title = "Exams",
                value = "0",
                modifier = Modifier.weight(1f)
            )
            SummaryChip(
                title = "Tasks Due",
                value = "0",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // ------------------------------------------------
        // Empty state in the middle
        // ------------------------------------------------
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .alpha(0.25f)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF90CAF9), Color.Transparent)
                        ),
                        shape = RoundedCornerShape(60.dp)
                    )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "No classes, tasks or exams left for today",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "You can adjust what is displayed on your homepage in settings.",
                fontSize = 14.sp,
                color = Color(0xFF757575),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun SummaryChip(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(60.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF1976D2)
            )
        }
    }
}
