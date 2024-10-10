## Description

This is a simple yet functional To-Do List application built using Android Studio and Java. The app allows users to manage their tasks efficiently with features like adding tasks, setting task reminders, editing tasks, deleting tasks, and more.

## Features

- Add Tasks: Users can create new tasks with a title, description, and an optional time to remind them.
- Edit Tasks: Users can edit the details of an existing task.
- Delete Tasks: Users can delete tasks individually or delete all tasks.
- Reminder with Notification: The app triggers a notification at the set time for tasks.
- Context Menu: Long press on a task to edit or delete it via a context menu.
- Checkbox for Multiple Deletions: Users can select multiple tasks to delete.
- Localization: Users can switch between different languages.
- Task Details: Clicking on a task in the list opens a custom activity showing all task details.

  ## Project Architecture
  The project follows the MVVM (Model-View-ViewModel) architecture, which ensures a clear separation between business logic and UI components. It helps in making the app more maintainable and testable.

  ## Technical Overview
- Java: The primary programming language used to build this application.
- Room Database: For local storage of tasks, Room provides an abstraction layer over SQLite.
- RecyclerView: Displays the list of tasks and supports interaction with items.
- LiveData: Updates the UI in response to data changes automatically.
- Notifications: Sends alerts to remind users of tasks at the set time.
- Custom Dialogs and Context Menus: For editing, deleting tasks, and performing bulk operations.
