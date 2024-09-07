package com.example.foodrecipe.presentations.RecipeSearchResult

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodrecipe.R
import com.example.foodrecipe.dataClass.RecipeListItemByQuery
import com.example.foodrecipe.dataClass.RecipeResponseByQuery
import com.example.foodrecipe.presentations.common.ErrorDisplay
import com.example.foodrecipe.presentations.common.GeneralBottomBar
import com.example.foodrecipe.presentations.common.GeneralTopBar
import com.example.foodrecipe.presentations.common.LargeImageCard
import com.example.foodrecipe.presentations.common.LoadingAnimation
import com.example.foodrecipe.utils.ResponseModel

@Composable
fun RecipeSearchResultScreen(
    recipeSearchResultViewModel: RecipeSearchResultViewModel = hiltViewModel(),
    navigateToDetails: (Int)-> Unit
){
    val searchResult by recipeSearchResultViewModel.searchResult.collectAsState()
    var isComplete by rememberSaveable {
        mutableStateOf(false)
    }
    val query by remember {
        mutableStateOf(recipeSearchResultViewModel.query)
    }
    Scaffold(
        topBar = { GeneralTopBar()
        },
        bottomBar = { GeneralBottomBar() },
        containerColor = colorResource(id = R.color.white)
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            item {
                Text(
                    text = "Search Result for Recipe",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(10.dp),
                    color = colorResource(id = R.color.food_green)
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp)
                        .border(
                            1.dp,
                            colorResource(id = R.color.food_green),
                            RoundedCornerShape(5.dp)
                        ),
                ) {
                    Text(
                        text = query,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(10.dp, 4.dp)
                    )
                }
            }
            when(searchResult){
                is ResponseModel.Error -> {
                    item {
                        ErrorDisplay(onReload = {

                        }, errorMsg = (searchResult as ResponseModel.Error).errorMsg,
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
                    items((searchResult as ResponseModel.Success<RecipeResponseByQuery>).data.results){ item ->
                        RecipeSearchItemCard(
                            title = item.title,
                            image = item.image,
                            onItemClick = { navigateToDetails(item.id) }
                        )
                    }
                    if(!isComplete){
                        item {
                            TextButton(onClick = {

                            }) {
                                Text(text = "Fetch more recipes",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colorResource(id = R.color.food_green),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            recipeSearchResultViewModel.getResult(100)
                                            isComplete = true
                                        },
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
fun RecipeSearchItemCard(
    title: String,
    image: String,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable { onItemClick() }
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
            LargeImageCard(image = image)
            Text(text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier= Modifier.padding(10.dp, 3.dp)
            )
        }
    }
}

//@Composable
//@Preview
//fun RecipeSearchResultScreenPreview(){
//    RecipeSearchResultScreen(
//        ResponseModel.Success(
//            RecipeResponseByQuery(
//                1,1,3,
//                listOf(
//                    RecipeListItemByQuery(12,)
//                )
//            )
//        )
//    )
//}