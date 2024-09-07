package com.example.foodrecipe.presentations.IngredientSearch

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodrecipe.R
import com.example.foodrecipe.dataClass.IngredientKeyword
import com.example.foodrecipe.presentations.common.ErrorDisplay
import com.example.foodrecipe.presentations.common.KeywordDisplay
import com.example.foodrecipe.presentations.common.LoadingAnimation
import com.example.foodrecipe.utils.ResponseModel
import com.example.foodrecipe.utils.showToast

@Composable
fun DialogForNewIng(
    onDismissRequest: ()->Unit,
    onAddIng: (String)-> Unit,
    ingSearchResponse: ResponseModel<List<IngredientKeyword>>,
    onQueryChange: (String)->Unit,
    ingList: List<String>
) {
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
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title ={
            Text(text = "Add Ingredient",
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            Column (
                modifier = Modifier.fillMaxWidth()
            ){
                BasicTextField(value = query,
                    onValueChange = {
                        query = it
                        onQueryChange(it.text) },
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
                        imeAction = ImeAction.Done
                    ),
                    textStyle = TextStyle(colorResource(id = R.color.food_text),22.sp, FontWeight.Medium)
                )
                Spacer(modifier = Modifier.height(10.dp))
                when(ingSearchResponse){
                    is ResponseModel.Error -> {
                        ErrorDisplay(onReload = { onQueryChange(query.text) },
                            errorMsg = ingSearchResponse.errorMsg,
                            modifier = Modifier.fillMaxWidth())
                    }
                    is ResponseModel.Success ->{
                        KeywordDisplay(list = ingSearchResponse.data.ingredientToStringList(),
                            onArrowClick = {
                                query = TextFieldValue(
                                    text = it,
                                    selection = TextRange(it.length)
                                )
                                           },
                            onItemClick = {
                                if(ingList.contains(it)){
                                    showToast(context, "Ingredient Already Added", Toast.LENGTH_SHORT)
                                }
                                else{
                                    onAddIng(it)
                                }
                            }
                        )
                    }
                    ResponseModel.Loading -> {
                        LoadingAnimation(
                        modifier = Modifier.fillMaxWidth()
                    )
                    }
                }
            }

        },
        confirmButton = {
            Text(text = "Add Ingredient",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.food_green),
                modifier = Modifier.clickable {
                    if(query.text.isNotBlank()){
                        if(ingList.contains(query.text)){
                            showToast(context, "Ingredient Already Added", Toast.LENGTH_SHORT)
                        }
                        else{
                            onAddIng(query.text)
                            onDismissRequest()
                        }
                    }
                    else{
                        showToast(context, "Search and add ingredient", Toast.LENGTH_SHORT)
                    }
                }
            )
        },
        dismissButton = {
            Text(text = "Cancel",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.food_green),
                modifier = Modifier.clickable {
                    onDismissRequest()
                }
                    .padding(10.dp, 0.dp))
        },
        containerColor = colorResource(id = R.color.white)
    )
}


fun List<IngredientKeyword>.ingredientToStringList(): List<String>{
    val list = mutableListOf<String>()
    for(ing in this){
        list.add(ing.name)
    }
    return list
}

@Composable
@Preview
fun DialogForNewIngPreview(){
    DialogForNewIng(
        onDismissRequest={},
        onAddIng={},
        ingSearchResponse= ResponseModel.Success(
            listOf(
                IngredientKeyword("first"),
                IngredientKeyword("second"),
                IngredientKeyword("third"),)
        ),
        onQueryChange={},
        ingList= listOf("first","second")
    )
}