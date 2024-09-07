package com.example.foodrecipe.presentations.RecipeSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipe.dataClass.IngredientKeyword
import com.example.foodrecipe.dataClass.RecipeKeyword
import com.example.foodrecipe.navigation.NavigationDestination
import com.example.foodrecipe.repository.ApiRepository
import com.example.foodrecipe.utils.ResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

object RecipeSearchDestination : NavigationDestination {
    override val route: String = "recipe_search"
}

@HiltViewModel
class RecipeSearchViewModel @Inject constructor(
    private val apiRepository: ApiRepository
): ViewModel(){
    private val _recipeSearchList = MutableStateFlow<ResponseModel<List<RecipeKeyword>>>(
        ResponseModel.Loading)
    val recipeSearchList: StateFlow<ResponseModel<List<RecipeKeyword>>> = _recipeSearchList

    fun getRecipeSearchList(query: String){
        viewModelScope.launch {
            apiRepository.getRecipeKeywords(10, query).collect{
                _recipeSearchList.value = it
            }
        }
    }
}