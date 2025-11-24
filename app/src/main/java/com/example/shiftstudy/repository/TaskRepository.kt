package com.example.shiftstudy.repository

import com.example.shiftstudy.data.dao.TaskDao
import com.example.shiftstudy.data.entity.Task

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun getAllTasksForUser(userId: Int): List<Task> {
        return taskDao.getAllTasksForUser(userId)
    }

    suspend fun getIncompleteTasksForUser(userId: Int): List<Task> {
        return taskDao.getIncompleteTasksForUser(userId)
    }

    suspend fun getTasksForWeek(userId: Int, startDate: Long, endDate: Long): List<Task> {
        return taskDao.getTasksForWeek(userId, startDate, endDate)
    }

    suspend fun getTaskById(taskId: Int): Task? {
        return taskDao.getTaskById(taskId)
    }
}