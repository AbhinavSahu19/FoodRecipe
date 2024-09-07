package com.example.foodrecipe.presentations.IngredientSearchResult

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.foodrecipe.R
import com.example.foodrecipe.dataClass.RecipeListItemByIngredient
import com.example.foodrecipe.presentations.common.ErrorDisplay
import com.example.foodrecipe.presentations.common.GeneralBottomBar
import com.example.foodrecipe.presentations.common.GeneralTopBar
import com.example.foodrecipe.presentations.common.LargeImageCard
import com.example.foodrecipe.presentations.common.LoadingAnimation
import com.example.foodrecipe.utils.ResponseModel

@Composable
fun IngredientSearchResultScreen(
    ingredientSearchResultViewModel: IngredientSearchResultViewModel = hiltViewModel(),
    navigateToDetails: (Int)-> Unit,
    navigateToHome: ()->Unit
){
    BackHandler {
        navigateToHome()
    }
    val searchResponse by ingredientSearchResultViewModel.searchResult.collectAsState()
    val ingList by remember {
        mutableStateOf(ingredientSearchResultViewModel.ingList.split(",+"))
    }
    var option by rememberSaveable {
        mutableIntStateOf(1)
    }
    var isComplete1 by rememberSaveable {
        mutableStateOf(false)
    }
    var isComplete2 by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = { GeneralTopBar()
                 },
        bottomBar = { GeneralBottomBar()},
        containerColor = colorResource(id = R.color.white)
    ) {contentPadding->
        LazyColumn (
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ){
            item {
                Text(text = "Search Result with Ingredients",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(10.dp),
                    color = colorResource(id = R.color.food_green)
                )
            }
            item {
                Column (
                    modifier = Modifier
                        .padding(10.dp, 5.dp)
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            colorResource(id = R.color.food_green),
                            RoundedCornerShape(5.dp)
                        )
                ){
                    for (i in 0..ingList.size-1){
                        Text(text = ingList[i],
                            fontSize = 22.sp,
                            modifier = Modifier.padding(10.dp, 2.dp))
                    }
                }
            }
            item {
                OptionBar(option = option,
                    onChange = {
                        option = it
                        if (option == 1) {
                            if (!isComplete1) {
                                ingredientSearchResultViewModel.getResult(10, it)
                            } else {
                                ingredientSearchResultViewModel.getResult(100, it)
                            }
                        } else {
                            if (!isComplete2) {
                                ingredientSearchResultViewModel.getResult(10, it)
                            } else {
                                ingredientSearchResultViewModel.getResult(100, it)
                            }
                        }
                    }
                )
            }
                when(searchResponse){
                    is ResponseModel.Error -> {
                        item {
                            ErrorDisplay(onReload = {

                            }, errorMsg = (searchResponse as ResponseModel.Error).errorMsg,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    ResponseModel.Loading -> {
                        item {
                            LoadingAnimation(modifier = Modifier.fillMaxSize())

                        }
                    }

                    is ResponseModel.Success -> {
                        items((searchResponse as ResponseModel.Success<List<RecipeListItemByIngredient>>).data) { item ->
                            RecipeByIngCard(
                                modifier = Modifier.clickable { navigateToDetails(item.id) },
                                item
                            )
                        }
                        if (option == 1 && !isComplete1) {
                            item {
                                TextButton(onClick = {
                                    ingredientSearchResultViewModel.getResult(100, option)
                                    isComplete1 = true
                                }) {
                                    Text(
                                        text = "Fetch more recipes",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = colorResource(id = R.color.food_green),
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        else if(option == 2 && !isComplete2){
                            item {
                                TextButton(onClick = {
                                    ingredientSearchResultViewModel.getResult(100, option)
                                    isComplete2 = true
                                }) {
                                    Text(text = "Fetch more recipes",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = colorResource(id = R.color.food_green),
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                }
            }
        }
    }
}

@Composable
fun RecipeByIngCard(modifier: Modifier,
                    item: RecipeListItemByIngredient) {
    Card(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .border(1.dp, colorResource(id = R.color.light_grey), RoundedCornerShape(5.dp)) ,
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white)
        ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
        ) {
            LargeImageCard(image = item.image)
            Text(text = item.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier= Modifier.padding(10.dp, 3.dp)
            )
            Text(text = "used ingredients: ${item.usedIngredientCount}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.food_text),
                modifier= Modifier.padding(10.dp, 0.dp)
            )
            Text(text = "missing ingredients: ${item.missedIngredientCount}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.food_text),
                modifier= Modifier.padding(10.dp, 0.dp, 10.dp, 5.dp)
            )
        }
    }
}

//@Composable
//@Preview
//fun IngredientSearchResultScreenPreview(){
//    IngredientSearchResultScreen(
////        ResponseModel.Success(
////            listOf(
////                RecipeListItemByIngredient(12,"Apple Or Peach Strudel", "https://img.spoonacular.com/recipes/73420-312x231.jpg", 1,3),
////                RecipeListItemByIngredient(12,"Apple Or Peach Strudel", "https://img.spoonacular.com/recipes/73420-312x231.jpg", 1,3),
////                RecipeListItemByIngredient(12,"Apple Or Peach Strudel", "https://img.spoonacular.com/recipes/73420-312x231.jpg", 1,3),
////                RecipeListItemByIngredient(12,"Apple Or Peach Strudel", "https://img.spoonacular.com/recipes/73420-312x231.jpg", 1,3),
////                RecipeListItemByIngredient(12,"Apple Or Peach Strudel", "https://img.spoonacular.com/recipes/73420-312x231.jpg", 1,3),
////                RecipeListItemByIngredient(12,"Apple Or Peach Strudel", "https://img.spoonacular.com/recipes/73420-312x231.jpg", 1,3),
////                RecipeListItemByIngredient(12,"Apple Or Peach Strudel", "https://img.spoonacular.com/recipes/73420-312x231.jpg", 1,3),
////                RecipeListItemByIngredient(12,"Apple Or Peach Strudel", "https://img.spoonacular.com/recipes/73420-312x231.jpg", 1,3),
////
////                )
////        ),
////        ResponseModel.Loading,
////        listOf("First","Second","Third")
//    )
//}