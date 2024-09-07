package com.example.foodrecipe.presentations.IngredientSearchResult

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipe.dataClass.RecipeListItemByIngredient
import com.example.foodrecipe.navigation.NavigationDestination
import com.example.foodrecipe.repository.ApiRepository
import com.example.foodrecipe.utils.ResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

object IngredientSearchResultDestination: NavigationDestination{
    override val route: String = "ingredient_search_result"
    const val args = "list"
    val routeWithArgs = "$route/{$args}"
}

@HiltViewModel
class IngredientSearchResultViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val ingList: String = checkNotNull(savedStateHandle[IngredientSearchResultDestination.args])

    private val _searchResult = MutableStateFlow<ResponseModel<List<RecipeListItemByIngredient>>>(ResponseModel.Loading)
    val searchResult: StateFlow<ResponseModel<List<RecipeListItemByIngredient>>> = _searchResult

    init {
        getResult(10, 1)
    }
    fun getResult(number: Int, ranking: Int){
        viewModelScope.launch {
            apiRepository.getRecipeByIngredient(ingList, number, ranking).collect{
                _searchResult.value = it
            }
        }
    }
}
