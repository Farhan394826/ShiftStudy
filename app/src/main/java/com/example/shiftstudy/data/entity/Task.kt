package com.example.shiftstudy.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val taskId: Int = 0,
    val userId: Int,
    val title: String,
    val description: String,
    val dueDate: Long,
    val category: String,
    val priority: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)