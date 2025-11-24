package com.example.shiftstudy.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shiftstudy.data.entity.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: Int): User?
}