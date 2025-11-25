package com.example.shiftstudy

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shiftstudy.data.dao.TaskDao
import com.example.shiftstudy.data.dao.UserDao
import com.example.shiftstudy.data.database.ShiftStudyDatabase
import com.example.shiftstudy.data.entity.Task
import com.example.shiftstudy.data.entity.User
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test for database operations
 * Tests Room database functionality on an Android device/emulator
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var database: ShiftStudyDatabase
    private lateinit var userDao: UserDao
    private lateinit var taskDao: TaskDao

    @Before
    fun setup() {
        // Create an in-memory database for testing
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            ShiftStudyDatabase::class.java
        ).allowMainThreadQueries().build()

        userDao = database.userDao()
        taskDao = database.taskDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    /**
     * Test 1: Insert and retrieve a user
     */
    @Test
    fun insertAndRetrieveUser() = runBlocking {
        // Create a test user
        val user = User(
            email = "test@example.com",
            password = "password123",
            name = "Test User"
        )

        // Insert user
        val userId = userDao.insertUser(user)

        // Retrieve user by email
        val retrievedUser = userDao.getUserByEmail("test@example.com")

        // Verify user was inserted and retrieved correctly
        assertNotNull("User should not be null", retrievedUser)
        assertEquals("Email should match", "test@example.com", retrievedUser?.email)
        assertEquals("Name should match", "Test User", retrievedUser?.name)
        assertTrue("User ID should be greater than 0", userId > 0)
    }

    /**
     * Test 2: Insert user and verify login
     */
    @Test
    fun userLoginTest() = runBlocking {
        // Create and insert a user
        val user = User(
            email = "login@test.com",
            password = "testpass",
            name = "Login User"
        )
        userDao.insertUser(user)

        // Attempt to retrieve user with correct credentials
        val loggedInUser = userDao.getUserByEmail("login@test.com")

        // Verify login succeeds
        assertNotNull("User should be found", loggedInUser)
        assertEquals("Password should match", "testpass", loggedInUser?.password)
    }

    /**
     * Test 3: Insert task and retrieve it
     */
    @Test
    fun insertAndRetrieveTask() = runBlocking {
        // First create a user (tasks need a userId)
        val user = User(
            email = "taskuser@test.com",
            password = "password",
            name = "Task User"
        )
        val userId = userDao.insertUser(user).toInt()

        // Create a task
        val task = Task(
            userId = userId,
            title = "Test Assignment",
            description = "Complete unit testing",
            dueDate = System.currentTimeMillis(),
            category = "CP3406",
            priority = "High",
            isCompleted = false
        )

        // Insert task
        taskDao.insertTask(task)

        // Retrieve all tasks for user
        val tasks = taskDao.getAllTasksForUser(userId)

        // Verify task was inserted
        assertEquals("Should have 1 task", 1, tasks.size)
        assertEquals("Title should match", "Test Assignment", tasks[0].title)
        assertEquals("Category should match", "CP3406", tasks[0].category)
        assertEquals("Priority should match", "High", tasks[0].priority)
        assertFalse("Task should not be completed", tasks[0].isCompleted)
    }

    /**
     * Test 4: Update task completion status
     */
    @Test
    fun updateTaskCompletion() = runBlocking {
        // Create user and task
        val user = User(email = "update@test.com", password = "pass", name = "User")
        val userId = userDao.insertUser(user).toInt()

        val task = Task(
            userId = userId,
            title = "Complete Testing",
            description = "Test description",
            dueDate = System.currentTimeMillis(),
            category = "Testing",
            priority = "Medium",
            isCompleted = false
        )

        taskDao.insertTask(task)

        // Get the task
        val tasks = taskDao.getAllTasksForUser(userId)
        val insertedTask = tasks[0]

        // Update task to completed
        val updatedTask = insertedTask.copy(isCompleted = true)
        taskDao.updateTask(updatedTask)

        // Retrieve and verify
        val updatedTasks = taskDao.getAllTasksForUser(userId)
        assertTrue("Task should be completed", updatedTasks[0].isCompleted)
    }

    /**
     * Test 5: Delete task
     */
    @Test
    fun deleteTask() = runBlocking {
        // Create user and task
        val user = User(email = "delete@test.com", password = "pass", name = "User")
        val userId = userDao.insertUser(user).toInt()

        val task = Task(
            userId = userId,
            title = "Task to Delete",
            description = "This will be deleted",
            dueDate = System.currentTimeMillis(),
            category = "Test",
            priority = "Low",
            isCompleted = false
        )

        taskDao.insertTask(task)

        // Verify task exists
        var tasks = taskDao.getAllTasksForUser(userId)
        assertEquals("Should have 1 task", 1, tasks.size)

        // Delete task
        taskDao.deleteTask(tasks[0])

        // Verify task is deleted
        tasks = taskDao.getAllTasksForUser(userId)
        assertEquals("Should have 0 tasks", 0, tasks.size)
    }

    /**
     * Test 6: Get incomplete tasks only
     */
    @Test
    fun getIncompleteTasks() = runBlocking {
        // Create user
        val user = User(email = "incomplete@test.com", password = "pass", name = "User")
        val userId = userDao.insertUser(user).toInt()

        // Create completed and incomplete tasks
        val completedTask = Task(
            userId = userId,
            title = "Completed Task",
            description = "Done",
            dueDate = System.currentTimeMillis(),
            category = "Test",
            priority = "High",
            isCompleted = true
        )

        val incompleteTask = Task(
            userId = userId,
            title = "Incomplete Task",
            description = "Not done",
            dueDate = System.currentTimeMillis(),
            category = "Test",
            priority = "High",
            isCompleted = false
        )

        taskDao.insertTask(completedTask)
        taskDao.insertTask(incompleteTask)

        // Get only incomplete tasks
        val incompleteTasks = taskDao.getIncompleteTasksForUser(userId)

        // Verify only incomplete task is returned
        assertEquals("Should have 1 incomplete task", 1, incompleteTasks.size)
        assertEquals("Should be the incomplete task", "Incomplete Task", incompleteTasks[0].title)
        assertFalse("Task should not be completed", incompleteTasks[0].isCompleted)
    }
}