package com.example.my_assignment1

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.NestedScrollView
import com.google.android.material.card.MaterialCardView
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // Input Views
    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var rbMale: RadioButton
    private lateinit var rbFemale: RadioButton
    private lateinit var etHeight: EditText
    private lateinit var etWeight: EditText
    private lateinit var spActivityLevel: Spinner
    private lateinit var spGoal: Spinner
    private lateinit var spDietType: Spinner
    private lateinit var btnCalculate: Button
    private lateinit var btnReset: Button
    private lateinit var mainScrollView: NestedScrollView

    // Result Views
    private lateinit var cardResults: MaterialCardView
    private lateinit var tvResultGreeting: TextView
    private lateinit var tvBmiValue: TextView
    private lateinit var tvBmiCategory: TextView
    private lateinit var tvIdealWeightRange: TextView
    private lateinit var tvCalories: TextView
    private lateinit var tvTdeeDetail: TextView
    private lateinit var tvWater: TextView
    private lateinit var tvWaterGlasses: TextView
    private lateinit var tvProtein: TextView
    private lateinit var tvCarbs: TextView
    private lateinit var tvFats: TextView
    private lateinit var tvMealBreakfast: TextView
    private lateinit var tvMealLunch: TextView
    private lateinit var tvMealSnack: TextView
    private lateinit var tvMealDinner: TextView
    private lateinit var tvTips: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Handle edge to edge system insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainScrollView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupSpinners()

        btnCalculate.setOnClickListener {
            calculateDietPlan()
        }

        btnReset.setOnClickListener {
            resetForm()
        }
    }

    private fun initViews() {
        mainScrollView = findViewById(R.id.mainScrollView)
        etName = findViewById(R.id.etName)
        etAge = findViewById(R.id.etAge)
        rgGender = findViewById(R.id.rgGender)
        rbMale = findViewById(R.id.rbMale)
        rbFemale = findViewById(R.id.rbFemale)
        etHeight = findViewById(R.id.etHeight)
        etWeight = findViewById(R.id.etWeight)
        spActivityLevel = findViewById(R.id.spActivityLevel)
        spGoal = findViewById(R.id.spGoal)
        spDietType = findViewById(R.id.spDietType)
        btnCalculate = findViewById(R.id.btnCalculate)
        btnReset = findViewById(R.id.btnReset)

        cardResults = findViewById(R.id.cardResults)
        tvResultGreeting = findViewById(R.id.tvResultGreeting)
        tvBmiValue = findViewById(R.id.tvBmiValue)
        tvBmiCategory = findViewById(R.id.tvBmiCategory)
        tvIdealWeightRange = findViewById(R.id.tvIdealWeightRange)
        tvCalories = findViewById(R.id.tvCalories)
        tvTdeeDetail = findViewById(R.id.tvTdeeDetail)
        tvWater = findViewById(R.id.tvWater)
        tvWaterGlasses = findViewById(R.id.tvWaterGlasses)
        tvProtein = findViewById(R.id.tvProtein)
        tvCarbs = findViewById(R.id.tvCarbs)
        tvFats = findViewById(R.id.tvFats)
        tvMealBreakfast = findViewById(R.id.tvMealBreakfast)
        tvMealLunch = findViewById(R.id.tvMealLunch)
        tvMealSnack = findViewById(R.id.tvMealSnack)
        tvMealDinner = findViewById(R.id.tvMealDinner)
        tvTips = findViewById(R.id.tvTips)
    }

    private fun setupSpinners() {
        val activityAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.activity_levels,
            android.R.layout.simple_spinner_dropdown_item
        )
        spActivityLevel.adapter = activityAdapter

        val goalAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.goals,
            android.R.layout.simple_spinner_dropdown_item
        )
        spGoal.adapter = goalAdapter

        val dietAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.diet_types,
            android.R.layout.simple_spinner_dropdown_item
        )
        spDietType.adapter = dietAdapter
    }

    private fun calculateDietPlan() {
        val name = etName.text.toString().trim()
        val ageStr = etAge.text.toString().trim()
        val heightStr = etHeight.text.toString().trim()
        val weightStr = etWeight.text.toString().trim()

        // Validation
        if (ageStr.isEmpty()) {
            etAge.error = "Please enter your age"
            etAge.requestFocus()
            return
        }
        val age = ageStr.toIntOrNull()
        if (age == null || age < 12 || age > 100) {
            etAge.error = "Enter a realistic age (12 - 100)"
            etAge.requestFocus()
            return
        }

        if (heightStr.isEmpty()) {
            etHeight.error = "Please enter your height"
            etHeight.requestFocus()
            return
        }
        val heightCm = heightStr.toDoubleOrNull()
        if (heightCm == null || heightCm < 80.0 || heightCm > 250.0) {
            etHeight.error = "Enter valid height (80 - 250 cm)"
            etHeight.requestFocus()
            return
        }

        if (weightStr.isEmpty()) {
            etWeight.error = "Please enter your weight"
            etWeight.requestFocus()
            return
        }
        val weightKg = weightStr.toDoubleOrNull()
        if (weightKg == null || weightKg < 25.0 || weightKg > 300.0) {
            etWeight.error = "Enter valid weight (25 - 300 kg)"
            etWeight.requestFocus()
            return
        }

        val isMale = rbMale.isChecked
        val activityIndex = spActivityLevel.selectedItemPosition
        val goalIndex = spGoal.selectedItemPosition
        val dietTypeIndex = spDietType.selectedItemPosition

        // 1. BMI Calculation
        val heightM = heightCm / 100.0
        val bmi = weightKg / (heightM * heightM)

        val (bmiCategory, categoryColor) = when {
            bmi < 18.5 -> Pair("Underweight", ContextCompat.getColor(this, R.color.bmi_underweight))
            bmi < 25.0 -> Pair("Normal Weight", ContextCompat.getColor(this, R.color.bmi_normal))
            bmi < 30.0 -> Pair("Overweight", ContextCompat.getColor(this, R.color.bmi_overweight))
            else -> Pair("Obese", ContextCompat.getColor(this, R.color.bmi_obese))
        }

        val minIdealWeight = 18.5 * (heightM * heightM)
        val maxIdealWeight = 24.9 * (heightM * heightM)

        // 2. BMR (Mifflin-St Jeor formula)
        val bmr = if (isMale) {
            (10.0 * weightKg) + (6.25 * heightCm) - (5.0 * age) + 5.0
        } else {
            (10.0 * weightKg) + (6.25 * heightCm) - (5.0 * age) - 161.0
        }

        // 3. TDEE based on activity level
        val activityMultiplier = when (activityIndex) {
            0 -> 1.2    // Sedentary
            1 -> 1.375  // Light exercise
            2 -> 1.55   // Moderate
            3 -> 1.725  // Very active
            else -> 1.2
        }
        val tdee = bmr * activityMultiplier

        // 4. Target Calories based on Goal
        val targetCalories = when (goalIndex) {
            0 -> (tdee - 450.0).coerceAtLeast(if (isMale) 1500.0 else 1200.0) // Weight Loss
            1 -> tdee                                                          // Maintain
            2 -> tdee + 400.0                                                  // Muscle / Weight Gain
            else -> tdee
        }

        // 5. Water Intake (~33ml per kg body weight)
        val waterLiters = weightKg * 0.033
        val waterGlasses = (waterLiters * 1000.0 / 250.0).toInt().coerceAtLeast(6)

        // 6. Macronutrients Distribution
        val (proteinPct, carbPct, fatPct) = when (goalIndex) {
            0 -> Triple(0.30, 0.40, 0.30) // Weight Loss (Higher protein, moderate carbs)
            1 -> Triple(0.25, 0.50, 0.25) // Maintain (Balanced)
            2 -> Triple(0.30, 0.50, 0.20) // Gain (Higher carbs & protein for muscle build)
            else -> Triple(0.25, 0.50, 0.25)
        }

        val proteinGrams = ((targetCalories * proteinPct) / 4.0).toInt()
        val carbGrams = ((targetCalories * carbPct) / 4.0).toInt()
        val fatGrams = ((targetCalories * fatPct) / 9.0).toInt()

        // 7. Update UI
        if (name.isNotEmpty()) {
            tvResultGreeting.text = "Personalized Plan for $name"
        } else {
            tvResultGreeting.text = "Your Nutrition Plan"
        }

        tvBmiValue.text = String.format(Locale.getDefault(), "%.1f", bmi)
        tvBmiCategory.text = bmiCategory
        tvBmiCategory.setTextColor(categoryColor)

        tvIdealWeightRange.text = String.format(
            Locale.getDefault(),
            "Healthy weight range for %.0f cm: %.1f kg - %.1f kg",
            heightCm, minIdealWeight, maxIdealWeight
        )

        tvCalories.text = String.format(Locale.getDefault(), "%,d kcal / day", targetCalories.toInt())
        tvTdeeDetail.text = String.format(Locale.getDefault(), "Maintenance TDEE: %,d kcal | BMR: %,d kcal", tdee.toInt(), bmr.toInt())

        tvWater.text = String.format(Locale.getDefault(), "%.1f Liters", waterLiters)
        tvWaterGlasses.text = "~$waterGlasses glasses (250 ml each)"

        tvProtein.text = "${proteinGrams}g (${(proteinPct * 100).toInt()}%)"
        tvCarbs.text = "${carbGrams}g (${(carbPct * 100).toInt()}%)"
        tvFats.text = "${fatGrams}g (${(fatPct * 100).toInt()}%)"

        // Meal suggestions
        updateMealPlan(goalIndex, dietTypeIndex)

        // Tips
        updateTips(goalIndex, bmi)

        // Reveal results and scroll down smoothly
        cardResults.visibility = View.VISIBLE
        mainScrollView.post {
            mainScrollView.smoothScrollTo(0, cardResults.top)
        }

        Toast.makeText(this, "Plan generated successfully!", Toast.LENGTH_SHORT).show()
    }

    private fun updateMealPlan(goal: Int, dietType: Int) {
        // dietType: 0 = Vegetarian, 1 = Non-Vegetarian, 2 = Vegan
        // goal: 0 = Weight Loss, 1 = Maintain, 2 = Gain

        val breakfast = when (dietType) {
            0 -> when (goal) { // Vegetarian
                0 -> "Oatmeal with chia seeds, handful of berries and warm skimmed milk or buttermilk."
                1 -> "2 Moong dal chillas / Vegetable oats upma + a cup of unsweetened curd."
                else -> "3 Paneer parathas / Peanut butter banana toast with a large glass of full-cream milk."
            }
            1 -> when (goal) { // Non-Vegetarian
                0 -> "3 Egg white vegetable omelette with 1 slice of whole wheat toast and green tea."
                1 -> "2 Boiled whole eggs + 2 slices of whole grain bread + a glass of fresh orange juice."
                else -> "3 Whole egg scramble with cheese, 3 slices of whole wheat toast and a banana peanut butter smoothie."
            }
            else -> when (goal) { // Vegan
                0 -> "Overnight oats with almond milk, flaxseeds, and sliced apple."
                1 -> "Tofu scramble with spinach, bell peppers, and whole grain toast."
                else -> "Large smoothie bowl with soy milk, oats, banana, peanut butter, and pumpkin seeds."
            }
        }

        val lunch = when (dietType) {
            0 -> when (goal) { // Vegetarian
                0 -> "1 Whole wheat roti, 1 bowl of thick yellow dal, generous bowl of stir-fried veggies & cucumber salad."
                1 -> "2 Multigrain rotis, 1 cup paneer bhurji / chana masala, mixed vegetable curry, and curd."
                else -> "3 Rotis, 1 large bowl of Rajma / Paneer curry, 1 cup brown rice, and raita."
            }
            1 -> when (goal) { // Non-Vegetarian
                0 -> "150g Grilled chicken breast / fish with steamed broccoli, carrots, and 1/2 cup brown rice."
                1 -> "Chicken curry with 2 whole wheat rotis, 1/2 cup steamed rice, and fresh tossed salad."
                else -> "200g Chicken breast or fish curry, 2 cups rice / 3 rotis, boiled lentils, and egg salad."
            }
            else -> when (goal) { // Vegan
                0 -> "Chickpea & quinoa salad bowl with cucumbers, tomatoes, lemon dressing, and steamed beans."
                1 -> "Tofu and vegetable stir-fry served with brown rice and lentil soup."
                else -> "Large bowl of kidney bean chili (rajma) with brown rice, sautéed tofu, and avocado slices."
            }
        }

        val snack = when (dietType) {
            0 -> when (goal) { // Vegetarian
                0 -> "A cup of green tea with a handful of roasted makhana (foxnuts) or roasted chana."
                1 -> "Fresh seasonal fruit (apple/papaya) + 6-8 soaked almonds and walnuts."
                else -> "Paneer sandwich or fruit smoothie with nuts and seeds."
            }
            1 -> when (goal) { // Non-Vegetarian
                0 -> "2 Boiled egg whites with black pepper and a cup of green tea."
                1 -> "Spiced egg bhurji toast or a small chicken salad roll."
                else -> "Boiled egg sandwich with whole wheat bread and mixed nuts."
            }
            else -> when (goal) { // Vegan
                0 -> "Green tea with roasted chana or air-popped popcorn."
                1 -> "Handful of walnuts, almonds, and sliced pear or apple."
                else -> "Peanut butter on rice cakes or roasted chickpeas with hummus."
            }
        }

        val dinner = when (dietType) {
            0 -> when (goal) { // Vegetarian
                0 -> "Warm mixed vegetable and lentil soup with 100g sautéed tofu or paneer."
                1 -> "1-2 Rotis with seasonal subzi, 1 small cup dal, and cucumber salad."
                else -> "2 Rotis with Paneer tikka / soya chaap curry, 1 cup jeera rice, and salad."
            }
            1 -> when (goal) { // Non-Vegetarian
                0 -> "Clear chicken vegetable soup with grilled fish or boiled chicken breast."
                1 -> "Grilled fish fillet / baked chicken with sautéed green beans and sweet potato."
                else -> "Chicken curry with whole wheat roti, sautéed vegetables, and a boiled egg."
            }
            else -> when (goal) { // Vegan
                0 -> "Light vegetable stew with boiled chickpeas and sautéed mushrooms."
                1 -> "1 Roti with soya chunk curry, mixed vegetables, and green salad."
                else -> "Quinoa and lentil pilaf with grilled tofu and sesame broccoli."
            }
        }

        tvMealBreakfast.text = breakfast
        tvMealLunch.text = lunch
        tvMealSnack.text = snack
        tvMealDinner.text = dinner
    }

    private fun updateTips(goal: Int, bmi: Double) {
        val tipsList = mutableListOf<String>()

        tipsList.add("• Drink a full glass of water 20-30 minutes before every meal to aid digestion.")
        tipsList.add("• Aim for 7 to 8 hours of uninterrupted sleep every night to maintain balanced metabolism.")

        when (goal) {
            0 -> {
                tipsList.add("• Focus on high-protein and high-fiber foods to stay full while in a moderate calorie deficit.")
                tipsList.add("• Limit sugary sodas, refined flours, and late-night snacking.")
            }
            1 -> {
                tipsList.add("• Maintain your weight by keeping active and eating a balanced plate of whole foods.")
                tipsList.add("• Weigh yourself once a week at the same time for consistent monitoring.")
            }
            2 -> {
                tipsList.add("• Eat calorie-dense, nutrient-rich foods such as nuts, peanut butter, whole grains, and dairy.")
                tipsList.add("• Incorporate strength and resistance training 3-4 days a week for lean muscle growth.")
            }
        }

        if (bmi >= 25.0) {
            tipsList.add("• Incorporate 30 minutes of brisk walking or light cardio at least 5 days a week.")
        } else if (bmi < 18.5) {
            tipsList.add("• Avoid skipping meals; consider having 5 smaller meals spread consistently throughout the day.")
        }

        tvTips.text = tipsList.joinToString("\n")
    }

    private fun resetForm() {
        etName.text.clear()
        etAge.text.clear()
        etHeight.text.clear()
        etWeight.text.clear()
        rbMale.isChecked = true
        spActivityLevel.setSelection(0)
        spGoal.setSelection(0)
        spDietType.setSelection(0)

        etAge.error = null
        etHeight.error = null
        etWeight.error = null

        cardResults.visibility = View.GONE
        mainScrollView.smoothScrollTo(0, 0)
        Toast.makeText(this, "Form reset", Toast.LENGTH_SHORT).show()
    }
}