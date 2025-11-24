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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(navController: NavHostController) {

    var title by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7FB))
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Top bar
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Add New",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Simple fields (Task version only – enough for assignment)
        Text(text = "Title", style = MaterialTheme.typography.labelLarge)
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Task Title") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Details", style = MaterialTheme.typography.labelLarge)
        OutlinedTextField(
            value = details,
            onValueChange = { details = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Task description") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Subject", style = MaterialTheme.typography.labelLarge)
        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Select subject") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Type (Optional)", style = MaterialTheme.typography.labelLarge)
        OutlinedTextField(
            value = type,
            onValueChange = { type = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Task type") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }
            Button(
                onClick = {
                    // later: save to DB; for now just go back
                    navController.popBackStack()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Save Task")
            }
        }
    }
}
