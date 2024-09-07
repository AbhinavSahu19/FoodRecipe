package com.example.foodrecipe.network

import com.example.foodrecipe.dataClass.IngredientKeyword
import com.example.foodrecipe.dataClass.RecipeDetails
import com.example.foodrecipe.dataClass.RecipeKeyword
import com.example.foodrecipe.dataClass.RecipeListItemByIngredient
import com.example.foodrecipe.dataClass.RecipeResponseByQuery
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("recipes/autocomplete")
    suspend fun getRecipeKeyword(
        @Query("number") number: Int,
        @Query("query") query: String
    ): List<RecipeKeyword>

    @GET("food/ingredients/autocomplete")
    suspend fun getIngredientKeywords(
        @Query("number") number: Int,
        @Query("query") query: String
    ): List<IngredientKeyword>

    @GET("recipes/findByIngredients")
    suspend fun getRecipeByIngredients(
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int,
        @Query("ranking") ranking: Int,
    ): List<RecipeListItemByIngredient>

    @GET("recipes/complexSearch")
    suspend fun getRecipeByQuery(
        @Query("query") query: String,
        @Query("number")number: Int
    ): RecipeResponseByQuery

    @GET("recipes/{recipe_id}/information")
    suspend fun getRecipeDetails(
        @Path("recipe_id") recipeId: Int,
        @Query("includeNutrition") includeNutrition: Boolean = true,
        @Query("includeAnalyzedInstructions") includeAnalyzedInstructions: Boolean = true,
    ): RecipeDetails
}