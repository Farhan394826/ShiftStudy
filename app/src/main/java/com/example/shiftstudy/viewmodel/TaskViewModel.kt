package com.example.shiftstudy.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shiftstudy.data.database.ShiftStudyDatabase
import com.example.shiftstudy.data.entity.Task
import com.example.shiftstudy.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskRepository: TaskRepository

    var tasks by mutableStateOf<List<Task>>(emptyList())
        private set

    var taskState by mutableStateOf<TaskState>(TaskState.Idle)
        private set

    init {
        val database = ShiftStudyDatabase.getDatabase(application)
        taskRepository = TaskRepository(database.taskDao())
    }

    fun loadTasksForUser(userId: Int) {
        viewModelScope.launch {
            taskState = TaskState.Loading
            try {
                tasks = taskRepository.getAllTasksForUser(userId)
                taskState = TaskState.Success
            } catch (e: Exception) {
                taskState = TaskState.Error(e.message ?: "Failed to load tasks")
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.insertTask(task)
                loadTasksForUser(task.userId)
            } catch (e: Exception) {
                taskState = TaskState.Error(e.message ?: "Failed to add task")
            }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.updateTask(task)
                loadTasksForUser(task.userId)
            } catch (e: Exception) {
                taskState = TaskState.Error(e.message ?: "Failed to update task")
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.deleteTask(task)
                loadTasksForUser(task.userId)
            } catch (e: Exception) {
                taskState = TaskState.Error(e.message ?: "Failed to delete task")
            }
        }
    }

    fun toggleTaskCompletion(task: Task) {
        val updatedTask = task.copy(isCompleted = !task.isCompleted)
        updateTask(updatedTask)
    }
}

sealed class TaskState {
    object Idle : TaskState()
    object Loading : TaskState()
    object Success : TaskState()
    data class Error(val message: String) : TaskState()
}