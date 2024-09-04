package com.example.foodrecipe.repository

import com.example.foodrecipe.dataClass.IngredientKeyword
import com.example.foodrecipe.dataClass.RecipeDetails
import com.example.foodrecipe.dataClass.RecipeKeyword
import com.example.foodrecipe.dataClass.RecipeListItemByIngredient
import com.example.foodrecipe.dataClass.RecipeResponseByQuery
import com.example.foodrecipe.utils.ResponseModel
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    fun getRecipeKeywords(number: Int, query: String): Flow<ResponseModel<List<RecipeKeyword>>>

    fun getIngredientKeywords(number: Int, query: String): Flow<ResponseModel<List<IngredientKeyword>>>

    fun getRecipeByIngredient(ingredients: String, number: Int, ranking: Int): Flow<ResponseModel<List<RecipeListItemByIngredient>>>

    fun getRecipeByQuery(query: String): Flow<ResponseModel<RecipeResponseByQuery>>

    fun getRecipeDetails(recipeId: Int): Flow<ResponseModel<RecipeDetails>>
}