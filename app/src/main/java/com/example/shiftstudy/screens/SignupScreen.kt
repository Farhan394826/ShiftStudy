package com.example.shiftstudy.screens

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shiftstudy.ui.components.ShiftStudyLogoAlt
import com.example.shiftstudy.viewmodel.AuthState
import com.example.shiftstudy.viewmodel.AuthViewModel

@Composable
fun SignupScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var retype by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    var retypeError by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Handle auth state changes
    LaunchedEffect(authViewModel.authState) {
        when (val state = authViewModel.authState) {
            is AuthState.Success -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                navController.navigate("home") {
                    popUpTo("signup") { inclusive = true }
                }
                authViewModel.resetAuthState()
            }
            is AuthState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                authViewModel.resetAuthState()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF42A5F5), Color(0xFF90CAF9))
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(top = 24.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Back Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back to Login",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Logo Section - MATCHING LOGIN
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            // Logo Container
            ShiftStudyLogoAlt(size = 70.dp)

            Spacer(modifier = Modifier.height(12.dp))

            // Company Name
            Text(
                text = "ShiftStudy",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 1.sp
            )

            Text(
                text = "Learn Smarter, Not Harder",
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.9f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Signup Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Create Account",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )

                Text(
                    text = "Enter your information to create your account.",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    enabled = authViewModel.authState !is AuthState.Loading
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    enabled = authViewModel.authState !is AuthState.Loading
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = false
                        retypeError = false
                    },
                    label = { Text("Password (min 8 characters)") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    enabled = authViewModel.authState !is AuthState.Loading,
                    isError = passwordError,
                    supportingText = {
                        if (passwordError) {
                            Text("Password must be at least 8 characters")
                        }
                    }
                )

                OutlinedTextField(
                    value = retype,
                    onValueChange = {
                        retype = it
                        retypeError = false
                    },
                    label = { Text("Re-type Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    enabled = authViewModel.authState !is AuthState.Loading,
                    isError = retypeError,
                    supportingText = {
                        if (retypeError) {
                            Text("Passwords do not match")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = {
                        when {
                            name.isBlank() -> {
                                Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
                            }
                            email.isBlank() -> {
                                Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
                            }
                            password.isBlank() -> {
                                Toast.makeText(context, "Please enter a password", Toast.LENGTH_SHORT).show()
                                passwordError = true
                            }
                            password.length < 8 -> {
                                Toast.makeText(context, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                                passwordError = true
                            }
                            retype.isBlank() -> {
                                Toast.makeText(context, "Please re-type your password", Toast.LENGTH_SHORT).show()
                                retypeError = true
                            }
                            password != retype -> {
                                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                                retypeError = true
                            }
                            else -> {
                                passwordError = false
                                retypeError = false
                                authViewModel.signup(email.trim(), password, name.trim())
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = authViewModel.authState !is AuthState.Loading
                ) {
                    if (authViewModel.authState is AuthState.Loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            text = "Sign up",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already have an account?",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    TextButton(
                        onClick = { navController.navigate("login") },
                        modifier = Modifier.padding(0.dp)
                    ) {
                        Text(
                            text = "Sign in",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}