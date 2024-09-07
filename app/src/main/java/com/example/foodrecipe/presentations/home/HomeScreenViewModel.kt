package com.example.foodrecipe.presentations.home

import androidx.lifecycle.ViewModel
import com.example.foodrecipe.navigation.NavigationDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

object HomeDestination: NavigationDestination{
    override val route: String = "home"

}
@HiltViewModel
class HomeScreenViewModel @Inject constructor(

): ViewModel() {

}