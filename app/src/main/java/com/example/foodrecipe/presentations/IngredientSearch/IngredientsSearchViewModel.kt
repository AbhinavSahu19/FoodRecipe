package com.example.foodrecipe.presentations.IngredientSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipe.dataClass.IngredientKeyword
import com.example.foodrecipe.navigation.NavigationDestination
import com.example.foodrecipe.repository.ApiRepository
import com.example.foodrecipe.utils.ResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

object IngredientSearchDestination : NavigationDestination{
    override val route: String = "ingredient_search"
}
@HiltViewModel
class IngredientsSearchViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel(){
    private val _ingSearchList = MutableStateFlow<ResponseModel<List<IngredientKeyword>>>(ResponseModel.Loading)
    val ingSearchList: StateFlow<ResponseModel<List<IngredientKeyword>>> = _ingSearchList

    fun getIngSearchList(query: String){
        viewModelScope.launch {
            apiRepository.getIngredientKeywords(10, query).collect{
                _ingSearchList.value = it
            }
        }
    }

    fun reset(){
        _ingSearchList.value = ResponseModel.Loading
    }
}