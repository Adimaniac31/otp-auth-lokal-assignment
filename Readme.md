# OTP Authentication App (Android – Jetpack Compose)

This project implements a **passwordless authentication flow** using **Email + OTP**, followed by a **session screen** that tracks login duration.  
It is built as per the given Android assignment, with emphasis on **correct state management**, **clean architecture**, and **Jetpack Compose usage** rather than UI polish or backend integration.

---

## Features

- Email-based OTP authentication (local, no backend)
- 6-digit OTP generation
- OTP expiry after **60 seconds**
- Maximum **3 OTP validation attempts**
- Resend OTP (invalidates old OTP and resets attempts)
- Session screen with live duration timer (**mm:ss**)
- Logout functionality
- Event logging using **Timber**

---

## Tech Stack

- **Language:** Kotlin  
- **UI:** Jetpack Compose  
- **Architecture:** ViewModel + UI State (one-way data flow)  
- **Concurrency:** Kotlin Coroutines  
- **Logging / External SDK:** Timber  

---

## Architecture Overview

The application follows a clean separation of concerns:
UI (Jetpack Compose)
↓
AuthViewModel (State + Business Orchestration)
↓
OtpManager (Pure OTP Logic)


- Composables are responsible only for rendering UI and handling user input  
- ViewModel owns all business state and application flow  
- OTP logic is encapsulated in a dedicated manager class  
- No global mutable state is used  

---

## OTP Logic and Expiry Handling

OTPs are stored per email using an **in-memory map** structure.

Each OTP record contains:

- The 6-digit OTP value  
- An absolute expiry timestamp (in milliseconds)  
- Remaining validation attempts (maximum 3)  

### Rules Implemented

- OTP is always exactly **6 digits**
- OTP expires **60 seconds** after generation
- Maximum **3 attempts**
- Generating a new OTP invalidates the previous one and resets attempts  

### Validation Order

1. Check if an OTP exists for the email  
2. Check if the OTP is expired  
3. Check if attempts are exhausted  
4. Validate OTP match  

This order prevents wasting attempts on expired OTPs and ensures defensive handling of edge cases.

---

## Session Timer Logic

- On successful OTP validation, the session start time is stored in the ViewModel  
- Session duration is calculated as: currentTimeMillis - sessionStartTime

- The UI updates the timer every second using a coroutine-based effect  
- Because the source of truth is in the ViewModel, the timer:
  - Survives recompositions
  - Continues correctly across configuration changes
  - Stops immediately on logout  

---

## 📊 Logging and External SDK (Timber)

**Timber** is used as the external SDK for logging.

### Why Timber?

- Lightweight and simple to integrate  
- Clean abstraction over Android’s logging system  
- Automatically tags logs using the calling class name  
- Suitable for analytics-style event logging  

### Logged Events

- OTP generated  
- OTP validation success  
- OTP validation failure (invalid, expired, or attempts exceeded)  
- Logout  

> ⚠️ **Note:** OTP values are logged only for local testing and demonstration purposes.  
> In a real production system, sensitive information like OTPs should **never** be logged.

---

## 🤖 Use of GPT / AI Assistance

GPT was used to:

- Clarify Jetpack Compose concepts  
- Understand Android tooling (Gradle, Logcat, Manifest)  
- Review architectural decisions and edge cases  

All core logic, data structures, and implementation decisions were **understood and implemented manually**, not blindly copy-pasted.

---

## 🚀 Setup Instructions (For New Users)

### Prerequisites

- Android Studio (latest stable version)
- Android SDK (API level **24+**)
- Android Emulator or physical Android device

### Steps

1. Clone the repository from GitHub  
2. Open the project in Android Studio  
3. Allow Gradle to sync completely (no additional setup required)  
4. Run the app on an emulator or physical device  
5. To test OTP flow:
   - Enter any email  
   - Tap **Send OTP**  
   - Check **Logcat** (filter by tag: `Timber`) to view the generated OTP  
   - Enter the OTP to log in  

---

## Project Structure
com.example.otpauthapp
│
├── data
│ ├── OtpRecord
│ └── OtpManager
│
├── viewModel
│ ├── AuthState
│ └── AuthViewModel
│
├── ui
│ ├── AuthApp
│ ├── LoginScreen
│ ├── OtpScreen
│ └── SessionScreen
│
├── OtpAuthApplication
└── MainActivity


---

## What Was Intentionally Avoided

- No global mutable state  
- No UI logic inside the ViewModel  
- No backend or network calls  
- No unnecessary abstractions  
- No blocking calls on the main thread  

The focus was **clarity, correctness, and maintainability**.

---

## Author

**Aditya Bhivgade**
