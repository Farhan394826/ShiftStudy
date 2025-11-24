package com.example.shiftstudy.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shiftstudy.data.entity.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks WHERE userId = :userId ORDER BY dueDate ASC")
    suspend fun getAllTasksForUser(userId: Int): List<Task>

    @Query("SELECT * FROM tasks WHERE userId = :userId AND isCompleted = 0 ORDER BY dueDate ASC")
    suspend fun getIncompleteTasksForUser(userId: Int): List<Task>

    @Query("SELECT * FROM tasks WHERE userId = :userId AND dueDate >= :startDate AND dueDate < :endDate ORDER BY dueDate ASC")
    suspend fun getTasksForWeek(userId: Int, startDate: Long, endDate: Long): List<Task>

    @Query("SELECT * FROM tasks WHERE taskId = :taskId")
    suspend fun getTaskById(taskId: Int): Task?
}