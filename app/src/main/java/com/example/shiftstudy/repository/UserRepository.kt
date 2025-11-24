package com.example.shiftstudy.repository

import com.example.shiftstudy.data.dao.UserDao
import com.example.shiftstudy.data.entity.User
import com.example.shiftstudy.utils.PasswordUtils

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(email: String, password: String, name: String): Result<User> {
        return try {
            val existingUser = userDao.getUserByEmail(email)
            if (existingUser != null) {
                Result.failure(Exception("Email already exists"))
            } else {
                // Hash the password before storing
                val hashedPassword = PasswordUtils.hashPassword(password)
                val user = User(email = email, password = hashedPassword, name = name)
                val userId = userDao.insertUser(user)
                Result.success(user.copy(userId = userId.toInt()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(email: String, password: String): Result<User> {
        return try {
            // Get user by email first
            val user = userDao.getUserByEmail(email)

            if (user != null && PasswordUtils.verifyPassword(password, user.password)) {
                Result.success(user)
            } else {
                Result.failure(Exception("Invalid email or password"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }
}