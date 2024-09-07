package com.example.foodrecipe.presentations.home

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodrecipe.R
import com.example.foodrecipe.presentations.common.GeneralBottomBar
import com.example.foodrecipe.presentations.common.GeneralTopBar
import com.example.foodrecipe.utils.showToast

@Composable
fun HomeScreen(
    navigateToIngredientSearch: () -> Unit,
    navigateToRecipeSearch: () -> Unit
) {
    Scaffold(
//        topBar = { GeneralTopBar()},
//        bottomBar = { GeneralBottomBar()}
    ) {contentPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)){
            Image(painter = painterResource(id = R.drawable.img),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.BottomEnd
            )
            Column(
                modifier = Modifier
//                    .padding(contentPadding)
                    .fillMaxWidth()
                    .fillMaxHeight()

                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorResource(id = R.color.transparent),
//                                colorResource(id = R.color.transparent),
//                                colorResource(id = R.color.transparent),
//                                colorResource(id = R.color.transparent),
//                                colorResource(id = R.color.transparent),
//                                colorResource(id = R.color.t_white),
//                                colorResource(id = R.color.t_white),
                                colorResource(id = R.color.t_white),
                            )
                        ),
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,

            ) {
                Text(text = "Cooking a",
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp, 1.dp),
                    fontWeight = FontWeight.Black,
                    fontSize = 32.sp,
                    color = colorResource(id = R.color.food_green))
                Text(text = "Delicious Food",
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp, 1.dp),
                    fontWeight = FontWeight.Black,
                    fontSize = 30.sp,
                    color = colorResource(id = R.color.food_green))
                Text(text = "Easily",
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp, 1.dp, 10.dp, 5.dp),
                    fontWeight = FontWeight.Black,
                    fontSize = 30.sp,
                    color = colorResource(id = R.color.food_green))

                Text(text = "From ingredients to inspiration, we've got you covered. Search, cook, and master the art of deliciousness!",
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp, 2.dp, 10.dp, 10.dp),
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.food_green))

                Button(onClick = {navigateToRecipeSearch()},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.white),
                        contentColor = colorResource(id = R.color.food_green),),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, colorResource(id = R.color.food_green)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 10.dp),
                ) {
                    Text(text = "Search for Dishes",
                        fontSize = 23.sp,
                        modifier = Modifier.padding(5.dp, 5.dp)
                    )
                }
                Button(
                    onClick = { navigateToIngredientSearch() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.white),
                        contentColor = colorResource(id = R.color.food_green),
                    ),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, colorResource(id = R.color.food_green)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 25.dp),
                ) {
                    Text(text = "Got Ingredients? Find Recipes",
                        fontSize = 23.sp,
                        modifier = Modifier
                            .padding(0.dp, 5.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@Composable
@Preview
fun HomeScreenPreview(){
    HomeScreen(
        navigateToRecipeSearch = {},
        navigateToIngredientSearch = {}
    )
}