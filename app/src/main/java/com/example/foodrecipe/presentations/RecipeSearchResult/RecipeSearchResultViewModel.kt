package com.example.foodrecipe.presentations.RecipeSearchResult

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipe.dataClass.RecipeListItemByIngredient
import com.example.foodrecipe.dataClass.RecipeResponseByQuery
import com.example.foodrecipe.navigation.NavigationDestination
import com.example.foodrecipe.repository.ApiRepository
import com.example.foodrecipe.utils.ResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

object RecipeSearchResultDestination: NavigationDestination{
    override val route: String = "recipe_search_result"
    const val args = "query"
    val routeWithArgs = "$route/{$args}"
}

@HiltViewModel
class RecipeSearchResultViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    val query:String = checkNotNull(savedStateHandle[RecipeSearchResultDestination.args])

    private val _searchResult = MutableStateFlow<ResponseModel<RecipeResponseByQuery>>(
        ResponseModel.Loading)
    val searchResult: StateFlow<ResponseModel<RecipeResponseByQuery>> = _searchResult

    init {
        getResult(10)
    }
    fun getResult(number: Int){
        viewModelScope.launch {
            apiRepository.getRecipeByQuery(query, number).collect{
                _searchResult.value = it
            }
        }
    }
}