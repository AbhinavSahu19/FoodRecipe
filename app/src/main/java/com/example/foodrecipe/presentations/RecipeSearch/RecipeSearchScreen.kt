package com.example.foodrecipe.presentations.RecipeSearch

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodrecipe.R
import com.example.foodrecipe.dataClass.RecipeKeyword
import com.example.foodrecipe.presentations.common.ErrorDisplay
import com.example.foodrecipe.presentations.common.GeneralBottomBar
import com.example.foodrecipe.presentations.common.GeneralTopBar
import com.example.foodrecipe.presentations.common.KeywordDisplay
import com.example.foodrecipe.presentations.common.LoadingAnimation
import com.example.foodrecipe.utils.ResponseModel
import com.example.foodrecipe.utils.showToast

@Composable
fun RecipeSearchScreen(
    recipeSearchViewModel: RecipeSearchViewModel = hiltViewModel(),
    navigateToRecipeSearchResult: (String) -> Unit,
) {
    val recipeSearchList by recipeSearchViewModel.recipeSearchList.collectAsState()
    var query by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val focusRequester = remember {
        FocusRequester()
    }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = { GeneralTopBar() },
        bottomBar = { GeneralBottomBar() }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
                .padding(contentPadding)
                .padding(10.dp, 5.dp)
        ) {
            Text(
                text = "Search Recipe",
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(10.dp),
                color = colorResource(id = R.color.food_green)
            )

            BasicTextField(
                value = query,
                onValueChange = { query = it
                                recipeSearchViewModel.getRecipeSearchList(query.text)},
                modifier = Modifier
                    .border(
                        1.dp,
                        colorResource(id = R.color.food_green),
                        RoundedCornerShape(5.dp)
                    )
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .padding(10.dp, 5.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = false,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (query.text.isNotBlank()){
                            navigateToRecipeSearchResult(query.text)
                        }
                        else{
                            showToast(context, "Search some recipe", Toast.LENGTH_SHORT)
                        }
                    }
                ),
                textStyle = TextStyle(
                    colorResource(id = R.color.food_text),
                    22.sp,
                    FontWeight.Medium
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            when (recipeSearchList) {
                is ResponseModel.Error -> {
                    ErrorDisplay(
                        onReload = {
                recipeSearchViewModel.getRecipeSearchList(query = query.text)
                        },
                        errorMsg = (recipeSearchList as ResponseModel.Error).errorMsg,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                    )
                }

                ResponseModel.Loading -> {
                    LoadingAnimation(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                    )
                }

                is ResponseModel.Success -> {
                    KeywordDisplay(list = (recipeSearchList as ResponseModel.Success<List<RecipeKeyword>>).data.recipeToStringList(),
                        onArrowClick = {
                            query = TextFieldValue(it, TextRange(it.length))
                        },
                        onItemClick = {
                            navigateToRecipeSearchResult(it)
                        })
                }
            }
        }
    }
}

fun List<RecipeKeyword>.recipeToStringList(): List<String> {
    val list = mutableListOf<String>()
    for (recipe in this) {
        list.add(recipe.title)
    }
    return list
}

//@Composable
//@Preview
//fun RecipeSearchScreenPreview() {
//    RecipeSearchScreen(
//        ResponseModel.Success(
//            listOf(
//                RecipeKeyword("first"),
//                RecipeKeyword("second"),
//                RecipeKeyword("third")
//            )
//        )
//    )
//}