# Student Course Registration System

A Java Swing–based desktop application that allows students to register for courses and admins to manage courses and monitor registrations, backed by a MySQL database.

# 📌 Project Overview

The Student Course Registration System is designed to automate the manual process of course enrollment.
It provides role-based access where:

👨‍🎓 Students can view courses, register, drop courses, and see their registered subjects.

🧑‍💼 Admins can add and view courses, monitor student registrations, and view total student counts.

The system ensures real-time seat availability updates and maintains data securely using a relational database.

# 🚀 Features
✅ Common Features

Login and Sign Up for both Student and Admin

Secure database connectivity using JDBC

Clean and professional GUI with Java Swing

# 👨‍🎓 Student Module

View available courses

Register for courses

Drop registered courses

View registered courses

Logout

# 🧑‍💼 Admin Module

Add new courses

View all courses

View total number of students

View courses registered by each student

Logout

# 🛠 Technologies Used

Java (Swing) – GUI development

MySQL – Database

JDBC – Java Database Connectivity

MySQL Connector/J – JDBC Driver

VS Code / Eclipse / IntelliJ – IDE

# 📑Project Folder Structure

StudentCourseRegistrationSystem/
<br>
│
<br>
├── src/
<br>
│   ├── db/
<br>
│   │   └── DBConnection.java             # Database connection class
<br>
│   │
<br>
│   ├── ui/
<br>
│   │   ├── LoginFrame.java               # Login screen
<br>
│   │   ├── SignUpFrame.java              # Sign up screen
<br>
│   │   ├── AdminDashboard.java           # Admin main dashboard
<br>
│   │   ├── StudentDashboard.java         # Student main dashboard
<br>
│   │   ├── AddCourseFrame.java           # Admin adds new course
<br>
│   │   ├── ViewCoursesFrame.java         # Admin views courses
<br>
│   │   ├── ViewStudentCountFrame.java    # Admin views total students
<br>
│   │   ├── ViewStudentRegistrationsFrame.java  # Admin views student's registered courses
<br>
│   │   ├── RegisterCourseFrame.java      # Student registers for course
<br>
│   │   ├── DropCourseFrame.java          # Student drops a course
<br>
│   │   └── ViewRegisteredCoursesFrame.java     # Student views registered courses
<br>
│
<br>
├── lib/
<br>
      └── mysql-connector-j-9.4.0.jar       # JDBC driver for MySQL
