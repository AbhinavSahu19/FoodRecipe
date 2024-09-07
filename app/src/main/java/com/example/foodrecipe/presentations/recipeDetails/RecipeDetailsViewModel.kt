package com.example.foodrecipe.presentations.recipeDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipe.dataClass.RecipeDetails
import com.example.foodrecipe.navigation.NavigationDestination
import com.example.foodrecipe.repository.ApiRepository
import com.example.foodrecipe.utils.ResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

object RecipeDetailsDestination: NavigationDestination{
    override val route: String = "recipe_details"
    const val args = "id"
    val routeWithArgs = "$route/{$args}"
}

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    val id : Int = checkNotNull(savedStateHandle[RecipeDetailsDestination.args])

    private val _details = MutableStateFlow<ResponseModel<RecipeDetails>>(ResponseModel.Loading)
    val details : StateFlow<ResponseModel<RecipeDetails>> = _details

    init {
        getRecipe()
    }

    fun getRecipe(){
        viewModelScope.launch {
            apiRepository.getRecipeDetails(id).collect{
                _details.value = it
            }
        }
    }
}