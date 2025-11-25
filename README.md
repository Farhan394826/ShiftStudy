# ShiftStudy - Student Task Management App

A comprehensive Android mobile application designed to help students manage their academic tasks, assignments, and study schedule effectively.

## Features

### Core Functionality
- **User Authentication**: Secure login and signup with password hashing (SHA-256)
- **Task Management**: Create, view, update, and delete tasks with full CRUD operations
- **Smart Scheduling**: Weekly calendar view showing tasks organized by day
- **Priority System**: Categorize tasks as High, Medium, or Low priority
- **Category Organization**: Organize tasks by subject/category
- **Due Dates & Times**: Set specific due dates and optional times for tasks
- **Task Completion**: Mark tasks as complete/incomplete with visual feedback
- **Expandable Details**: Tap tasks to view full descriptions

### Additional Features
- **Motivational Quotes**: Daily educational quotes from Quotable API with offline fallback
- **Background Audio**: Optional ambient sound for focused studying with volume control
- **Statistics Dashboard**: View total, completed, and pending task counts
- **Today's Tasks**: Highlighted section showing tasks due today
- **Upcoming Tasks**: Preview of next 5 upcoming tasks
- **Settings Screen**: User profile management, logout, and audio controls
- **Custom Branding**: Professional logo with "Learn Smarter, Not Harder" tagline

## Technologies Used

### Core Technologies
- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern UI toolkit for native Android
- **Material Design 3**: Google's latest design system

### Architecture & Libraries
- **MVVM Architecture**: Model-View-ViewModel pattern
- **Room Database**: Local data persistence for users and tasks
- **Navigation Component**: Type-safe navigation between screens
- **Coroutines**: Asynchronous programming for smooth performance
- **ViewModel**: Lifecycle-aware data management
- **SharedPreferences**: Storing user preferences

### Network & API
- **Retrofit**: HTTP client for API calls
- **Gson**: JSON serialization/deserialization
- **Quotable API**: Educational quote integration

### Testing
- **JUnit**: Unit testing framework
- **AndroidX Test**: Instrumented testing
- **Room Testing**: Database testing utilities
- **Espresso**: UI testing (Activity lifecycle)

## Database Schema

### User Table
- userId (Primary Key)
- email (Unique)
- password (Hashed)
- name

### Task Table
- taskId (Primary Key)
- userId (Foreign Key)
- title
- description
- dueDate
- dueTime (Optional)
- category
- priority
- isCompleted

## Testing

The app includes comprehensive instrumented tests:

### Database Tests (7 tests)
- Insert and retrieve user
- User login authentication
- Insert and retrieve task
- Update task completion status
- Delete task
- Filter incomplete tasks

### UI Tests (6 tests)
- Activity launches successfully
- Activity has content view
- Activity visibility check
- App stability test (2+ seconds)
- Activity lifecycle functionality
- Lifecycle state transitions

**Total: 13 passing instrumented tests**

Run tests with: `./gradlew connectedAndroidTest`

## Installation

1. Clone the repository:
```bash
   git clone https://github.com/yourusername/ShiftStudy.git
```

2. Open the project in Android Studio (Hedgehog or later)

3. Sync Gradle files

4. Run on an emulator or physical device (API 24+)

## Requirements

- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34 (Android 14)
- **Compile SDK**: 34
- **Kotlin Version**: 1.9+
- **Gradle Version**: 8.0+

## UI Screens

1. **Login Screen**: User authentication with email and password
2. **Signup Screen**: New user registration
3. **Home Screen**: Dashboard with stats, quotes, and task overview
4. **Schedule Screen**: Weekly calendar view (Monday-Sunday)
5. **Tasks Screen**: Full task list with filtering (All/Pending/Completed)
6. **Add Task Screen**: Create new tasks with all details
7. **Settings Screen**: Profile, audio controls, and logout

## Security Features

- Password hashing using SHA-256
- Secure user authentication
- Session management
- Input validation

## Audio Feature

- Background ambient sound for studying
- Toggle on/off in Settings
- Volume control slider (0-100%)
- Preferences persist across sessions
- Handles app lifecycle (pause/resume)

## Network Connectivity

- Integrates Quotable API for educational quotes
- Graceful offline handling with fallback quotes
- Error handling for network failures
- HTTP client with Retrofit and OkHttp

## Developer

**Farhan Imtiaz**
- Course: CP3406 Mobile App Development
- Institution: James Cook University
- Year: 2025

## License

This project was created as an academic assignment for CP3406.

## Acknowledgments

- Quotable API for educational quotes
- Material Design 3 for UI components
- Android Jetpack libraries
- Course instructors and teaching staff




