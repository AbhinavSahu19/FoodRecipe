package com.example.foodrecipe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodrecipe.presentations.IngredientSearch.IngredientSearchDestination
import com.example.foodrecipe.presentations.IngredientSearch.IngredientSearchScreen
import com.example.foodrecipe.presentations.IngredientSearchResult.IngredientSearchResultDestination
import com.example.foodrecipe.presentations.IngredientSearchResult.IngredientSearchResultScreen
import com.example.foodrecipe.presentations.RecipeSearch.RecipeSearchDestination
import com.example.foodrecipe.presentations.RecipeSearch.RecipeSearchScreen
import com.example.foodrecipe.presentations.RecipeSearchResult.RecipeSearchResultDestination
import com.example.foodrecipe.presentations.RecipeSearchResult.RecipeSearchResultScreen
import com.example.foodrecipe.presentations.home.HomeDestination
import com.example.foodrecipe.presentations.home.HomeScreen
import com.example.foodrecipe.presentations.recipeDetails.RecipeDetailsDestination
import com.example.foodrecipe.presentations.recipeDetails.RecipeDetailsScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
){
    NavHost(navController = navController,
        startDestination = HomeDestination.route) {
        composable(HomeDestination.route){
            HomeScreen(
                navigateToIngredientSearch = { navController.navigate(IngredientSearchDestination.route)},
                navigateToRecipeSearch = { navController.navigate(RecipeSearchDestination.route)}
            )
        }
        composable(IngredientSearchDestination.route){
            IngredientSearchScreen(
                navigateToIngredientSearchResult = {navController.navigate("${IngredientSearchResultDestination.route}/$it")}
            )
        }
        composable(RecipeSearchDestination.route){
            RecipeSearchScreen(
                navigateToRecipeSearchResult = {navController.navigate("${RecipeSearchResultDestination.route}/$it")}
            )
        }
        composable(RecipeSearchResultDestination.routeWithArgs,
            arguments = listOf(navArgument(RecipeSearchResultDestination.args){type = NavType.StringType})
        ){
            RecipeSearchResultScreen(
                navigateToDetails = {navController.navigate("${RecipeDetailsDestination.route}/$it")}
            )
        }
        composable(IngredientSearchResultDestination.routeWithArgs,
            arguments = listOf(navArgument(IngredientSearchResultDestination.args){type = NavType.StringType})
            ){
            IngredientSearchResultScreen(
                navigateToDetails = {navController.navigate("${RecipeDetailsDestination.route}/$it")},
                navigateToHome = {navController.navigate(HomeDestination.route)}
            )
        }
        composable(RecipeDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(RecipeDetailsDestination.args){type = NavType.IntType})
        ){
            RecipeDetailsScreen()
        }
    }
}