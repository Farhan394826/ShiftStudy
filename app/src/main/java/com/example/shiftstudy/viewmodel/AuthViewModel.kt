package com.example.shiftstudy.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shiftstudy.data.database.ShiftStudyDatabase
import com.example.shiftstudy.data.entity.User
import com.example.shiftstudy.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepository

    var currentUser by mutableStateOf<User?>(null)
        private set

    var authState by mutableStateOf<AuthState>(AuthState.Idle)
        private set

    init {
        val database = ShiftStudyDatabase.getDatabase(application)
        userRepository = UserRepository(database.userDao())
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authState = AuthState.Loading
            val result = userRepository.loginUser(email, password)
            result.onSuccess { user ->
                currentUser = user
                authState = AuthState.Success("Login successful")
            }.onFailure { error ->
                authState = AuthState.Error(error.message ?: "Login failed")
            }
        }
    }

    fun signup(email: String, password: String, name: String) {
        viewModelScope.launch {
            authState = AuthState.Loading
            val result = userRepository.registerUser(email, password, name)
            result.onSuccess { user ->
                currentUser = user
                authState = AuthState.Success("Registration successful")
            }.onFailure { error ->
                authState = AuthState.Error(error.message ?: "Registration failed")
            }
        }
    }

    fun logout() {
        currentUser = null
        authState = AuthState.Idle
    }

    fun resetAuthState() {
        authState = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
}