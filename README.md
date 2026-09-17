# Diet Consultant 🥗🏃‍♂️

**Diet Consultant** is a personalized health, fitness, and nutrition companion application for Android. Built entirely in Kotlin and styled with the Google Material Design 3 system, the app allows users to input their physiological details, lifestyle variables, and dietary preferences to instantly generate a comprehensive, tailored nutrition plan, macro breakdown, daily water requirement, and structured meal recommendations.

---

## 🌟 Core Features

### 📋 1. Personal Health Profiler
* **Robust Input Forms:** Captures user attributes including Full Name (optional), Age, Gender, Height (cm), and Weight (kg).
* **Smart Input Validation:** Active checking ensures realistic age ranges (12–100 years), heights (80–250 cm), and weights (25–300 kg) to maintain physiological sanity and prevent application crashes.

### ⚙️ 2. High-Precision Health Calculators
* **Body Mass Index (BMI):** Instantly determines the user's BMI score along with an dynamically color-coded status badge (`Underweight`, `Normal Weight`, `Overweight`, `Obese`). It also calculates the exact healthy weight range bound for the user's height.
* **Basal Metabolic Rate (BMR):** Computes precise resting metabolic output using the modern **Mifflin-St Jeor Equation**, taking biological sex variations into account.
* **Total Daily Energy Expenditure (TDEE):** Multiplies BMR with specific activity modifiers corresponding to the user's lifestyle:
  * *Sedentary (Little/no exercise)* $\rightarrow$ `1.2`
  * *Lightly Active (1-3 days/week)* $\rightarrow$ `1.375`
  * *Moderately Active (3-5 days/week)* $\rightarrow$ `1.55`
  * *Very Active (6-7 days/week)* $\rightarrow$ `1.725`
* **Goal-Targeted Calorie Allocations:** Adjusts final calorie caps based on objectives:
  * *Weight Loss:* Subtracts `450 kcal` safely (coerced to a minimum threshold of `1500 kcal` for males and `1200 kcal` for females).
  * *Maintenance:* Matches TDEE exactly.
  * *Weight/Muscle Gain:* Injects a controlled `+400 kcal` surplus.
* **Hydration Calculator:** Formulates daily water quotas based on physical mass (`~33ml` per kg of body weight) and renders it in both Liters and equivalent `250ml` glass count.

### 🥗 3. Tailored Macronutrient Distribution
Applies optimal, goal-oriented macronutrient percentage splits across total daily energy allocation:
* **Weight Loss:** High protein, moderate fat (`30% Protein / 40% Carbs / 30% Fats`)
* **Maintenance:** Balanced distribution (`25% Protein / 50% Carbs / 25% Fats`)
* **Weight/Muscle Gain:** Enhanced carbs and high protein (`30% Protein / 50% Carbs / 20% Fats`)

### 🍽️ 4. Dynamic Matrix-Driven Meal Plan Generator
Uses a contextual selection algorithm cross-referencing **Fitness Goals** $\times$ **Dietary Preferences** (`Vegetarian`, `Non-Vegetarian`, `Vegan`) to output an exact four-meal schedule layout:
* **Breakfast** 🍳
* **Lunch** 🍱
* **Evening Snack** 🍵
* **Dinner** 🍲

### 💡 5. Intelligent Lifestyle Coaching Tips
Appends highly relevant dietary habits and lifestyle bullet points mapped directly to the user's current goal status and physical BMI category boundaries.

---

## 🛠️ Technology Stack & Architecture

* **Language:** 100% Kotlin
* **UI Layout System:** Native Android XML View Hierarchy with dynamic data-binding elements.
* **Component Framework:** Google Material Components 3 (`MaterialCardView`, `TextInputLayout`, `TextInputEditText`, `MaterialButton`, `NestedScrollView`).
* **System Integration:** Includes full edge-to-edge system window inset rendering using `ViewCompat.setOnApplyWindowInsetsListener` to prevent status/navigation bar overlap across modern Android distributions.
* **UX Enhancements:** The results panel stays hidden (`View.GONE`) initially and smoothly animates to visible (`View.VISIBLE`) via an auto-focus smooth scroller upon a successful computation lifecycle.

---

## 📂 Project Structure

```bash
MY_ASSIGNMENT1/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/my_assignment1/
│   │   │   │   └── MainActivity.kt        # Main controller containing validation, formulas, & UI updates
│   │   │   └── res/
│   │   │       ├── layout/
│   │   │       │   └── activity_main.xml  # Material 3 responsive layout definition
│   │   │       └── values/
│   │   │           ├── colors.xml         # Custom app color scheme & dynamic BMI state colors
│   │   │           └── strings.xml        # Centralized app copy & drop-down array resources
│   └── build.gradle.kts                   # Module level build configuration
└── settings.gradle.kts                    # Project repositories and structure setup
```

---

## 🚀 Getting Started

### Prerequisites
* Android Studio (Ladybug 2024.2.1 or newer recommended)
* Android SDK (API 21 / Android 5.0 Lollipop or higher)
* Gradle JDK 17+

### Setup & Installation
1. Clone this repository to your local machine:
   ```bash
   git clone https://github.com/your-username/DietConsultant.git
   ```
2. Open Android Studio and choose **File > Open**, then select the project's root folder.
3. Allow Android Studio to complete the Gradle build and index files.
4. Connect a physical Android device with USB Debugging enabled, or set up a virtual device via the Device Manager.
5. Click the **Run** button (`Shift + F10`) or select **Run > Run 'app'** from the top toolbar menu.

---

## 📜 License
This project is open-source and available under the [MIT License](LICENSE).
