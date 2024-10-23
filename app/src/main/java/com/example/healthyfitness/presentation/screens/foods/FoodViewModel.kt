package com.example.healthyfitness.presentation.screens.foods

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthyfitness.MyApplication
import com.example.healthyfitness.data.data_source.local.FitnessDatabase
import com.example.healthyfitness.data.food_data.FoodItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FoodViewModel : ViewModel() {
    private val _food = MutableStateFlow<List<FoodItem>>(emptyList())
    val food = _food.asStateFlow()
    private var foodApiService: FoodService
    private var foodDao = FitnessDatabase.getDaoInstance(MyApplication.getApplicationContext())

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://wger.de/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        foodApiService = retrofit.create(FoodService::class.java)

        getFood()
    }

    private fun getFood() {
        viewModelScope.launch {
            try {
                val allEndpoint = getAllFood()
                _food.value = allEndpoint
            } catch (e: Exception) {
                _food.value = emptyList()
                Log.e("TAG", "Error fetching food: ${e.message}")
            }
        }
    }

    private suspend fun getAllFood() = withContext(Dispatchers.IO) {
        try {
            val foods = foodApiService.getAllFood()
            val foodItems = foods.results ?: emptyList()

            val foodDatabaseItem = foodItems.toDatabaseItems()
            foodDao.insertAllFood(foodDatabaseItem)
            Log.d("TAG", "Fetched food items from API: ${foodItems.size}")

            return@withContext foodItems
        } catch (ex: Exception) {
            Log.e("TAG", "Error fetching food from API, falling back to database: ${ex.message}")
            val databaseItems = foodDao.getAllFood().toFoodItems()
            Log.d("TAG", "Fetched from database: ${databaseItems.size}")
            return@withContext databaseItems
        }
    }
}
