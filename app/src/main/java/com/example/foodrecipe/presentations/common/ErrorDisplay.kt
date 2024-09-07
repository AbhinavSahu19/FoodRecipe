package com.example.foodrecipe.presentations.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodrecipe.R

@Composable
fun ErrorDisplay(
    onReload: ()-> Unit,
    errorMsg : String,
    modifier: Modifier
){
    Column (
        modifier = modifier
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = errorMsg,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
        )
        Button(onClick = { onReload() },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.food_green),
                contentColor = colorResource(id = R.color.white)
            )) {
            Text(text = "Reload")
        }
    }
}