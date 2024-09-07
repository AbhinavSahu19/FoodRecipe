package com.example.foodrecipe.presentations.IngredientSearchResult

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodrecipe.R

@Composable
fun OptionBar(
    option: Int,
    onChange: (Int) -> Unit
) {
    val color1 = if (option==1) colorResource(id = R.color.food_green)
    else colorResource(id = R.color.white)
    val color2 = if (option==2) colorResource(id = R.color.food_green)
    else colorResource(id = R.color.white)

    val textColor1 = if (option==1) colorResource(id = R.color.white)
    else colorResource(id = R.color.black)
    val textColor2 = if (option==2) colorResource(id = R.color.white)
    else colorResource(id = R.color.black)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp)
            .background(color = colorResource(id = R.color.white)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = color1
            ),
            border = BorderStroke(1.dp, colorResource(id = R.color.food_green)),
            shape = RoundedCornerShape(5.dp, 0.dp, 0.dp, 5.dp),
            modifier = Modifier.clickable {
                if(option != 1)onChange(1)
            }
        ) {
            Column (
                modifier = Modifier.padding(20.dp, 5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Maximize Used ",
                    fontSize = 18.sp,
                    color = textColor1,
                    modifier = Modifier
                )
                Text(
                    text = "Ingredients",
                    fontSize = 18.sp,
                    color = textColor1,
                    modifier = Modifier
                )
            }
        }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = color2
            ),
            border = BorderStroke(1.dp, colorResource(id = R.color.food_green)),
            shape = RoundedCornerShape(0.dp, 5.dp, 5.dp, 0.dp),
            modifier = Modifier.clickable {
                if(option != 2)onChange(2)
            }
        ) {
            Column (
                modifier = Modifier.padding(20.dp, 5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Minimize Missing ",
                    fontSize = 18.sp,
                    color = textColor2,
                    modifier = Modifier
                )
                Text(
                    text = "Ingredients",
                    fontSize = 18.sp,
                    color = textColor2,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
@Preview
fun GenreOptionPreview() {
    OptionBar(
        option = 1,
        onChange = {})
}