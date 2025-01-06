# Parental Control Android App

The **Parental Control Android App** is a comprehensive solution designed to help parents monitor and manage their children's mobile device usage. With real-time monitoring, keyword detection, restricted app alerts, and email notifications, this app ensures a safer and more productive digital environment for children.

---

## 📋 Features

- **Keyword Monitoring**: Detects suspicious keywords in real time from user interactions.
- **Restricted App Alerts**: Monitors usage of apps marked as restricted and sends immediate notifications.
- **Real-Time Notifications**: Sends instant alerts to parents about suspicious activities or app usage.
- **Email Notifications**: Delivers detailed alerts to the parent's registered email address.
- **User Authentication**: Secure login system using Firebase Authentication.
- **Multi-Language Support**: Monitors text in English and Telugu (including Romanized Telugu).
- **Customizable Alerts**: Parents can manage restricted apps and keywords dynamically.
- **Lightweight Design**: Optimized for efficiency without impacting device performance.

---

## 🛠 Technology Stack

### **Frontend**
- **Android SDK**: Used for building the mobile application.
- **Kotlin**: Ensures concise and secure code for Android development.
- **Firebase Authentication**: Provides secure user authentication.

### **Backend**
- **Spring Boot**: Handles email notifications via RESTful APIs.
- **Maven**: For dependency management.

### **Other Tools**
- **Volley Library**: Enables network communication between the frontend and backend.
- **Shared Preferences**: Manages lightweight local storage for user sessions.
- **Android Notification Manager**: Delivers in-app notifications.
- **Postman**: Used for backend API testing.

---

## 🔧 System Architecture

- **Frontend**: User-friendly Android application for parental control and monitoring.
- **Backend**: Spring Boot REST API for managing email notifications.

---

## 🚀 How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/sanjaysamsoth/ParentalControlApp.git
2. Navigate to the `android-app` folder and open it in **Android Studio**:
   - Build the project and run it on an Android emulator or device.

3. Navigate to the `spring-boot-backend` folder and open it in **IntelliJ IDEA**:
   - Run the Spring Boot application to enable email notifications.

4. Configure Firebase Authentication:
   - Add your Firebase project credentials to the Android app.

5. Test the application:
   - Add restricted apps or keywords and observe notifications and alerts.

---

## 📷 Screenshots

| **Description**               | **Screenshot**             |
|--------------------------------|----------------------------|
| App Dashboard                 | ![photo_2024-12-03_01-51-59](https://github.com/user-attachments/assets/56258e22-b3db-425d-95b4-ecd37695287c)|
| Restricted App Alert          | ![photo_2024-12-03_01-52-00](https://github.com/user-attachments/assets/bac5332a-20d7-4795-bcd1-8e5b441ccb9b)|
| Keyword Detection Notification | ![photo_2024-12-03_01-52-01](https://github.com/user-attachments/assets/dd2a51ed-ddaa-40e5-b6c9-6265f45caf3c)|
| Email Notification            | ![photo_2024-12-03_01-52-04](https://github.com/user-attachments/assets/4a6d93d1-8390-49a8-9b11-4215361ac590)|


---

## 📚 Use Cases

- **Parental Supervision**: Real-time alerts on children's digital activities.
- **Custom Restrictions**: Block specific apps during study hours.
- **Emergency Alerts**: Immediate notifications for sensitive activities (e.g., harmful keywords).
- **Educational Monitoring**: Encourage focused learning by restricting non-educational apps.

---

## 📂 Additional Information

For a detailed explanation of the project, including the system architecture, implementation details, and use cases, please refer to the following documents:
- [Project Report (PDF)]([docs/Report - Parental Control Android App.pdf](https://github.com/sanjaysamsoth/ParentalControlApp/blob/796d8a11e0c7a345576be087e9b34f241845ab78/docs/Report%20-%20Parental%20Control%20Android%20App.pdf))
- [Project Presentation (PPT)]([docs/PPT - Parental Control Android App.pdf](https://github.com/sanjaysamsoth/ParentalControlApp/blob/796d8a11e0c7a345576be087e9b34f241845ab78/docs/PPT%20-%20Parental%20Control%20App.pdf))

---
