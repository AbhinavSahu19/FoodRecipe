package com.example.foodrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.foodrecipe.navigation.AppNavGraph
import com.example.foodrecipe.presentations.IngredientSearch.IngredientSearchScreen
import com.example.foodrecipe.presentations.RecipeSearch.RecipeSearchScreen
import com.example.foodrecipe.ui.theme.FoodRecipeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodRecipeTheme {
                AppNavGraph()
            }
        }
    }
}
