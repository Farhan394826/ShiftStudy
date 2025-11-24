package com.example.shiftstudy.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shiftstudy.data.dao.TaskDao
import com.example.shiftstudy.data.dao.UserDao
import com.example.shiftstudy.data.entity.Task
import com.example.shiftstudy.data.entity.User

@Database(
    entities = [User::class, Task::class],
    version = 2,
    exportSchema = false
)
abstract class ShiftStudyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: ShiftStudyDatabase? = null

        fun getDatabase(context: Context): ShiftStudyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShiftStudyDatabase::class.java,
                    "shiftstudy_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}