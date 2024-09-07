package com.example.foodrecipe.presentations.recipeDetails

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodrecipe.R
import com.example.foodrecipe.dataClass.AnalyzedInstructionItem
import com.example.foodrecipe.dataClass.IngredientListItem
import com.example.foodrecipe.dataClass.Nutrient
import com.example.foodrecipe.dataClass.NutritionListItem
import com.example.foodrecipe.dataClass.RecipeDetails
import com.example.foodrecipe.dataClass.StepListItem
import com.example.foodrecipe.presentations.common.ErrorDisplay
import com.example.foodrecipe.presentations.common.GeneralBottomBar
import com.example.foodrecipe.presentations.common.GeneralTopBar
import com.example.foodrecipe.presentations.common.LargeImageCard
import com.example.foodrecipe.presentations.common.LoadingAnimation
import com.example.foodrecipe.presentations.common.SmallImageCard
import com.example.foodrecipe.utils.ResponseModel

@Composable
fun RecipeDetailsScreen(
    recipeDetailsViewModel : RecipeDetailsViewModel = hiltViewModel(),
//    recipeDetailsResponse: ResponseModel<RecipeDetails>
){
    val recipeDetailsResponse by recipeDetailsViewModel.details.collectAsState()
    Scaffold(
        topBar = { GeneralTopBar()},
        bottomBar = { GeneralBottomBar()},
        containerColor = colorResource(id = R.color.white)
    ) { contentPadding->
        when(recipeDetailsResponse){
            is ResponseModel.Error -> {
                ErrorDisplay(
                    onReload = {
//                        recipeDetailsViewModel.getRecipe()
                               },
                    errorMsg = (recipeDetailsResponse as ResponseModel.Error).errorMsg,
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize()
                )
            }
            ResponseModel.Loading -> {
                LoadingAnimation(modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize())
            }
            is ResponseModel.Success -> {
                RecipeDetailsDisplay(
                    (recipeDetailsResponse as ResponseModel.Success<RecipeDetails>).data,
                    modifier = Modifier.padding(contentPadding)
                )
            }
        }
    }
}

@Composable
fun RecipeDetailsDisplay(data: RecipeDetails,
                         modifier: Modifier) {
    var i by remember {
        mutableIntStateOf(0)
    }

    LazyColumn (
        modifier = modifier
    ){
        item {
            LargeImageCard(image = data.image)
        }
        item{
            RecipeDetailsText(
                data.title,
                data.readyInMinutes,
                data.servings,
                data.dishTypes,
                data.vegetarian,
                data.vegan,
                data.dairyFree
            )
        }
        if(data.ingredients.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                Text(text = "Ingredients",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier= Modifier.padding(10.dp, 3.dp)
                )
            }
            items(data.ingredients) { ing ->
                RecipeDetailsIngredientsCard(ing)
            }
        }
        if(data.analyzedInstructions.isNotEmpty()){
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // "Instruction" heading
                    Text(
                        text = "Instruction",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(end = 16.dp)
                    )
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(),
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        // Left arrow button
//                        Icon(
//                            painter = painterResource(id = R.drawable.left_arrow),
//                            contentDescription = "Previous Instruction",
//                            modifier = Modifier
//                                .clickable {
//                                    if (i > 0) i--
//                                }
//                                .padding(4.dp)
//                        )
//
//                        // Instruction number
//                        Text(
//                            text = "Instruction: ${i + 1}",
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Normal,
//                            color = colorResource(id = R.color.food_text),
//                            modifier = Modifier.padding(horizontal = 8.dp)
//                        )
//
//                        // Right arrow button
//
//                        Icon(
//                            painter = painterResource(id = R.drawable.right_arrow),
//                            contentDescription = "Next Instruction",
//                            modifier = Modifier
//                                .clickable {
//                                    if (i < data.analyzedInstructions.size - 1) i++
//                                }
//                                .padding(4.dp)
//                        )
//
//                    }
                }
            }
            items(data.analyzedInstructions.get(i).steps){step->
                RecipeDetailsStepCard(step)
            }
        }
    }
}

@Composable
fun RecipeDetailsStepCard(step: StepListItem) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(10.dp, 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white)
        ),
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(1.dp, colorResource(id = R.color.food_text))
    ) {
        Row {
            Text(text = "${step.number}. ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.black),
                modifier= Modifier.padding(10.dp, 7.dp, 0.dp, 7.dp)
            )
            Text(text = step.step,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.black),
                modifier= Modifier.padding(0.dp, 7.dp, 5.dp, 7.dp)
            )
        }
    }
}

@Composable
fun RecipeDetailsIngredientsCard(ingredient: IngredientListItem) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(10.dp, 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white)
        ),
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(1.dp, colorResource(id = R.color.food_text))
    ) {
        Row {
           if(!ingredient.image.isNullOrEmpty()){
               SmallImageCard(image = ingredient.image)
               Log.i("Img", ingredient.image)
           }
            Text(text = ingredient.original,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.black),
                modifier= Modifier.padding(10.dp, 7.dp, 5.dp, 7.dp)
            )
        }
    }
}

@Composable
fun RecipeDetailsText(
    title: String,
    minutes: Int,
    serving: Int,
    dishType: List<String>,
    vegetarian: Boolean,
    vegan: Boolean,
    dairyFree: Boolean
){
    Text(text = title,
        fontSize = 26.sp,
        fontWeight = FontWeight.SemiBold,
        modifier= Modifier.padding(10.dp, 3.dp)
    )
    Text(text = "Ready in: $minutes min",
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.food_text),
        modifier= Modifier.padding(10.dp, 0.dp)
    )
    Text(text = "Serving: $serving",
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.food_text),
        modifier= Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp)
    )
    if (vegan) {
        Text(
            text = "Vegan",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.food_text),
            modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp)
        )
    } else if (vegetarian) {
        Text(
            text = "Vegetarian",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.food_text),
            modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp)
        )
    }
    if (dairyFree) {
        Text(
            text = "Dairy Free",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.food_text),
            modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp)
        )
    }

    if (dishType.isNotEmpty()){
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            item{
                Text(text = "Dish type:  ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.food_text),
                    modifier= Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                )
            }
            items(dishType){type->
                Text(text = "$type, ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.food_text),
                    modifier= Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp) )

            }
        }
    }
}
//@Composable
//@Preview
//fun RecipeDetailsScreenPreview(){
//    RecipeDetailsScreen(
////        ResponseModel.Loading
//        ResponseModel.Success(
//            RecipeDetails(
//                true,
//                true,
//                true,
//                false,
//                listOf(
////                    IngredientListItem("","1 tbsp butter"),
////                    IngredientListItem("blueberries.jpg","1 tbsp butter"),
////                    IngredientListItem("blueberries.jpg","1 tbsp butter"),
////                    IngredientListItem("blueberries.jpg","1 tbsp butter"),
////                    IngredientListItem("blueberries.jpg","1 tbsp butter"),
//                    IngredientListItem("blueberries.jpg","1 tbsp butter"),
//                ),
//                123,
//                "whole wheat bread crumbs",
//                123,
//                2,
//                "blueberries.jpg",
//                NutritionListItem(
//
//                    listOf(
//                        Nutrient("protien", 2.3, "gm", 12.2),
//                        Nutrient("protien", 2.3, "gm", 12.2),
//                        Nutrient("protien", 2.3, "gm", 12.2),
//                        Nutrient("protien", 2.3, "gm", 12.2),
//                        Nutrient("protien", 2.3, "gm", 12.2),
//                        Nutrient("protien", 2.3, "gm", 12.2),
//                        Nutrient("protien", 2.3, "gm", 12.2),
//                    )
//
//
//                ),
//                listOf(
//                    "main course", "main course", "main course", "main course"
//                ),
//                listOf(
//                    AnalyzedInstructionItem(
//                        "name",
//                        listOf(
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//
//                        )
//                    ),
//                    AnalyzedInstructionItem(
//                        "name",
//                        listOf(
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//
//                            )
//                    ),
//                    AnalyzedInstructionItem(
//                        "name",
//                        listOf(
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//                            StepListItem(
//                                1,
//                                "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
//                            ),
//
//                            )
//                    )
//                )
//            )
//        )
////        ResponseModel.Error("this is the issue")
//    )
//}