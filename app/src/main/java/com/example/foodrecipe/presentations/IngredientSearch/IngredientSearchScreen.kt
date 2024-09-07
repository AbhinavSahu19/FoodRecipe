package com.example.foodrecipe.presentations.IngredientSearch

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodrecipe.R
import com.example.foodrecipe.presentations.IngredientSearchResult.IngredientSearchResultDestination
import com.example.foodrecipe.presentations.common.GeneralBottomBar
import com.example.foodrecipe.presentations.common.GeneralTopBar
import com.example.foodrecipe.utils.showToast

@Composable
fun IngredientSearchScreen(
    ingredientsSearchViewModel: IngredientsSearchViewModel = hiltViewModel(),
    navigateToIngredientSearchResult: (String) -> Unit
){
    val ingSearchList by ingredientsSearchViewModel.ingSearchList.collectAsState()
    val ingList = remember{
        mutableStateListOf<String>()
    }
    val context = LocalContext.current
//    val ingList = listOf("first", "second")
    var dialogForNewIng by remember {
        mutableStateOf(false)
    }
    Scaffold (
        topBar = { GeneralTopBar() },
        bottomBar = { GeneralBottomBar() }
    ){contentPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
                .padding(contentPadding)
                .padding(10.dp, 5.dp)
        ) {
            Text(text = "Add Ingredients",
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(10.dp),
                color = colorResource(id = R.color.food_green)
            )
            LazyColumn (
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp, 0.dp)
                    .background(colorResource(id = R.color.white))
            ){
                items(ingList){ing->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(7.dp, 1.dp)
                            .background(colorResource(id = R.color.white), RoundedCornerShape(3.dp))
                            .border(
                                1.dp,
                                colorResource(id = R.color.food_green),
                                RoundedCornerShape(3.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = ing,
                            fontSize = 22.sp,
                            color = colorResource(id = R.color.food_text),
                            modifier = Modifier
                                .weight(1f)
                                .padding(10.dp, 5.dp),
                        )
                        Icon(painter = painterResource(id = R.drawable.cross_icon),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(10.dp, 5.dp)
                                .size(22.dp)
                                .clickable {
                                    ingList.remove(ing)
                                },
                            colorResource(id = R.color.food_text))
                    }
                }
            }

            TextButton(onClick = {
                dialogForNewIng = true
            }) {
                Text(text = "Add Ingredients",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.food_green),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Button(onClick = {
                if (ingList.size == 0){
                    showToast(context, "No ingredient added", Toast.LENGTH_SHORT)
                }
                else{
                    navigateToIngredientSearchResult(ingList.joinToString(",+") )
                }
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.food_green),
                    contentColor = colorResource(id = R.color.white),
                    disabledContentColor = colorResource(id = R.color.black),
                    disabledContainerColor = colorResource(id = R.color.light_grey)
                ),
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 10.dp, 10.dp),
                enabled = ingList.size != 0
            ) {
                Text(text = "Search Recipe",
                    fontSize = 26.sp,
                    modifier = Modifier.padding(10.dp, 5.dp)
                    )
            }
        }
    }

    if(dialogForNewIng){
        DialogForNewIng(
            onDismissRequest = {dialogForNewIng = false},
            onAddIng = {
                ingList.add(it)
                ingredientsSearchViewModel.reset()
                dialogForNewIng = false
            },
            ingSearchResponse = ingSearchList,
            onQueryChange = {
                ingredientsSearchViewModel.getIngSearchList(query = it)
            },
            ingList = ingList
        )
    }
}


//@Composable
//@Preview
//fun IngredientSearchScreenPreview(){
//    IngredientSearchScreen(navigateToIngredientSearchResult = {navController.navigate("${IngredientSearchResultDestination.route}/$it")})
//}