package com.example.foodrecipe.repository

import com.example.foodrecipe.dataClass.IngredientKeyword
import com.example.foodrecipe.dataClass.RecipeDetails
import com.example.foodrecipe.dataClass.RecipeKeyword
import com.example.foodrecipe.dataClass.RecipeListItemByIngredient
import com.example.foodrecipe.dataClass.RecipeResponseByQuery
import com.example.foodrecipe.network.ApiService
import com.example.foodrecipe.utils.ResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): ApiRepository {
    override fun getRecipeKeywords(number: Int, query: String): Flow<ResponseModel<List<RecipeKeyword>>> = flow{
        emit(ResponseModel.Loading)

        try{
            val response = withContext( Dispatchers.IO) { apiService.getRecipeKeyword(number, query) }
            emit(ResponseModel.Success(response))
        }
        catch (e: Exception){
            emit(ResponseModel.Error(e.message ?: "Something went wrong"))
        }
    }

    override fun getIngredientKeywords(number: Int, query: String): Flow<ResponseModel<List<IngredientKeyword>>> = flow{
        emit(ResponseModel.Loading)

        try{
            val response = withContext( Dispatchers.IO) { apiService.getIngredientKeywords(number, query) }
            emit(ResponseModel.Success(response))
        }
        catch (e: Exception){
            emit(ResponseModel.Error(e.message ?: "Something went wrong"))
        }
    }

    override fun getRecipeByIngredient(
        ingredients: String,
        number: Int,
        ranking: Int
    ): Flow<ResponseModel<List<RecipeListItemByIngredient>>> = flow{
        emit(ResponseModel.Loading)

        try{
            val response = withContext( Dispatchers.IO) { apiService.getRecipeByIngredients(ingredients, number, ranking) }
            emit(ResponseModel.Success(response))
        }
        catch (e: Exception){
            emit(ResponseModel.Error(e.message ?: "Something went wrong"))
        }
    }

    override fun getRecipeByQuery(query: String): Flow<ResponseModel<RecipeResponseByQuery>> = flow{
        emit(ResponseModel.Loading)

        try{
            val response = withContext( Dispatchers.IO) { apiService.getRecipeByQuery(query) }
            emit(ResponseModel.Success(response))
        }
        catch (e: Exception){
            emit(ResponseModel.Error(e.message ?: "Something went wrong"))
        }
    }

    override fun getRecipeDetails(recipeId: Int): Flow<ResponseModel<RecipeDetails>> = flow{
        emit(ResponseModel.Loading)

        try{
            val response = withContext( Dispatchers.IO) { apiService.getRecipeDetails(recipeId) }
            emit(ResponseModel.Success(response))
        }
        catch (e: Exception){
            emit(ResponseModel.Error(e.message ?: "Something went wrong"))
        }
    }
}