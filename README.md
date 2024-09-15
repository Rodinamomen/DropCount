# Drop Count

| ![Logo](https://github.com/user-attachments/assets/ce7cead7-1911-4f93-b11d-823891c16394) | **About**<br>Drop Count is a water tracking app designed to help users monitor their daily water intake. The app sends hourly notifications, lets you choose the amount of water you drank per cup, and allows you to review your intake records. It also supports both dark and light themes. |
|:--:|--|
## Table of Contents

- [Features](#features)
- [Demo](#demo)
- [Screenshots](#screenshots)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Clone the Repository](#clone-the-repository)
- [Dependencies](#dependencies)
- [Languages](#languages)
## Features

- **Customizable Cup Size**: Log your water intake by selecting the cup size that fits your preference.
- **Hourly Notification Reminder**: Get notified every hour to remind you to drink water.
- **Water Intake History**: Track and view your water intake history over time to monitor your progress.
- **Daily Reset**: Automatically reset your total water intake at the start of each day.
- **Dark/Light Theme**: Easily switch between dark and light themes for a personalized app experience.
  
## Demo


Check out the video demonstration of **DropCount**:



https://github.com/user-attachments/assets/922047b5-71d8-4426-9f2c-206b1baecc2f

## Screenshots

Here’s a walkthrough of **DropCount** showing both light and dark themes.
### 1. Before Adding Water Records:

| Light Theme | Dark Theme |
|:-----------:|:----------:|
| <img src="https://github.com/user-attachments/assets/8830d5f8-793e-4405-a891-85d2173c38d2" width="300"/> | <img src="https://github.com/user-attachments/assets/953857c2-5d76-4fc9-afcf-fcd0288379a8" width="300"/> |

### 2. Choosing Water Cup:

| Light Theme | Dark Theme |
|:-----------:|:----------:|
| <img src="https://github.com/user-attachments/assets/273c2ccf-dc7c-4e6d-8e89-80d530b44375" width="300"/> | <img src="https://github.com/user-attachments/assets/0ebcf920-0283-447d-8990-935df3e6894a" width="300"/> |

This screen allows the user to choose the size of the water cup they want to log. Both light and dark theme views are shown for a comparison of the user interface.

### 3. After Logging Water Intake:

| Light Theme | Dark Theme |
|:-----------:|:----------:|
| <img src="https://github.com/user-attachments/assets/a78ef33f-86cb-4228-b042-b34d866268c3" width="300"/> | <img src="https://github.com/user-attachments/assets/dec92b4f-ae21-4e60-b0dc-38c059faf3fb" width="300"/> |

This screen displays the app after the user has logged their water intake. The current intake is reflected in both the light and dark themes, showing progress toward the daily hydration goal.

### 4. Enabling Hourly Reminder Notification:

| Light Theme | Dark Theme |
|:-----------:|:----------:|
| <img src="https://github.com/user-attachments/assets/cc5bebaf-4caf-4901-b721-6b77d29b26e0" width="300"/> | <img src="https://github.com/user-attachments/assets/b6c2f1a5-715d-483a-ac34-ef46520c1827" width="300"/> |

This screen demonstrates the process of enabling the hourly water intake reminder notification. The user can activate this feature to receive periodic reminders to log their water intake. Both light and dark theme variations are shown.

### 5. Disabling Hourly Reminder Notification:

| Light Theme | Dark Theme |
|:-----------:|:----------:|
| <img src="https://github.com/user-attachments/assets/9eb1293e-b2ef-447d-a623-0fcd3e28b75e" width="300"/> | <img src="https://github.com/user-attachments/assets/509e47f0-ebd3-47fd-b08b-f15d09a1295e" width="300"/> |

This screen shows the process of disabling the hourly reminder notification. The user can turn off notifications to stop receiving periodic reminders about their water intake. Both light and dark themes are presented for comparison.

### 6. Congratulations for Reaching Daily Intake Target:

| Light Theme | Dark Theme |
|:-----------:|:----------:|
| <img src="https://github.com/user-attachments/assets/8903791f-f8ef-42ca-8b89-fcfb28f304cc" width="300"/> | <img src="https://github.com/user-attachments/assets/9759386b-c822-4596-9fc9-ffabeb539758" width="300"/> |

This screen celebrates the user for achieving their daily water intake goal. A congratulatory message is displayed along with animations to mark the accomplishment. Shown in both light and dark themes for comparison.

## Technologies Used

- **Shared Preferences:** Used to store user preferences such as theme selection and notification settings.
- **WorkManager:** Handles scheduling of periodic tasks, such as sending hourly water intake reminders.
- **Room Database:** Provides a persistent local database for storing water intake history and records.
- **Navigation Components:** Used for navigating between different screens and fragments within the app.
- **View Binding:** Ensures safe and efficient interaction with UI components.
- **Coroutines:** Used for handling background tasks asynchronously, such as retrieving data from Room or API calls.
- **Notifications:** Used to remind the user to log their water intake periodically.

## Getting Started

To get a local copy of the project up and running, follow these steps:

### Clone the Repository

1. Clone the repository by running the following command in your terminal:

    ```bash
    git clone https://github.com/Rodinamomen/DropCount.git
    ```

## Dependencies

The project relies on the following libraries and frameworks:

- **Navigation Components**  
  - `androidx.navigation:navigation-fragment-ktx:2.7.7`
  - `androidx.navigation:navigation-ui-ktx:2.7.7`
  
- **WorkManager**  
  - `androidx.work:work-runtime-ktx:2.9.1`
   
- **Konfetti (for celebrations)**  
  - `nl.dionsegijn:konfetti-xml:2.0.4`
  
- **Circular Progress Bar**  
  - `com.mikhaellopez:circularprogressbar:3.1.0`
  
- **Room Database**  
  - `androidx.room:room-runtime:2.6.1`
  - `androidx.room:room-ktx:2.6.1`
  - `androidx.room:room-compiler:2.6.1`

Add these dependencies to your `build.gradle` (Module: app) file to ensure that all necessary libraries are included in your project.

## Languages:

This project is developed using:

- **Kotlin** for writing application logic.
- **XML** for defining user interface layouts and resources.
