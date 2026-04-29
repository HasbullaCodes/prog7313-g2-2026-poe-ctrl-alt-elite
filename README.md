# 💰 JIMI SAVE – Expense Tracker App

## 📱 Overview
**JIMI SAVE** is a mobile Expense Tracker application developed using **Android (Kotlin)**.  
The app helps users manage their finances by tracking expenses, categorising spending, setting goals, and analysing spending patterns.

---

## 🎯 Features

### 🔐 User Management
- Register a new account
- Login with email and password
- Forgot password functionality

### 💸 Expense Tracking
- Add expenses with:
  - Amount
  - Description
  - Category
  - Date and time
  - Photo (camera or gallery)
- View all transactions
- Filter transactions by date range

### 🗂️ Category Management
- Add custom spending categories
- View categories dynamically

### 📊 Spending Analysis
- Generate pie chart of spending by category
- Filter analysis by date range
- Visual representation using charts

### 🎯 Goal Setting
- Set minimum and maximum spending goals
- Save goals locally (SharedPreferences)
- Display saved goals on profile

### 🧭 Navigation
- Bottom navigation bar for easy access:
  - Home
  - Analysis
  - Transactions
  - Categories
  - Profile

---

## 🛠️ Technologies Used

- **Kotlin**
- **Android SDK**
- **Room Database**
- **SharedPreferences**
- **Coroutines (lifecycleScope)**
- **MPAndroidChart**
- **Intents**

---

## 📸 Image Handling
Users can:
- Upload images from gallery
- Capture photos using camera  
Images are displayed alongside expenses.

---

## 📅 Date Handling
- Date selection implemented using `DatePickerDialog`
- Dates stored in database-friendly format (`YYYY-MM-DD`)

---

## 🧠 Design Considerations
- Clean and user-friendly interface
- Offline functionality (no internet required)
- Modular structure (Activities + Database + UI)
- Input validation and error handling

---

## ⚠️ Limitations
- Data is stored locally (not user-specific)
- No cloud/database sync
- Basic camera implementation (thumbnail only)
- No advanced authentication/security

---

## 🚀 Future Improvements
- User-specific data (link records to userId)
- Cloud integration (Firebase / API)
- Full-resolution image storage
- Edit/Delete expenses
- Improved UI/UX design

---

## 📦 Installation

1. Clone the repository:
```bash
git clone https://github.com/HasbullaCodes/prog7313-g2-2026-poe-ctrl-alt-elite.git
```

2. Open the project:
- Open **Android Studio**
- Click **Open**
- Select the project folder

3. Run the app:
- Use an emulator OR connect a physical device (USB debugging enabled)
- Click **Run ▶️**

4. First use:
- Register a new account
- Login and start using the app

### ⚠️ Notes
- If the app does not run, go to **Build → Clean Project**
- You can also reinstall or clear app data if needed

## 👨‍💻 Author

- ST10407472: Joel Mikyle Parduman
- ST10359128: Ezekiel Isaac Narayanasami
- ST10250201: Mishen Naidoo
- ST10448362: Iqra Nazir
- ST10454477: Maseeha Aziz
