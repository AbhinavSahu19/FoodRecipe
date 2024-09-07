package com.example.foodrecipe.presentations.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.foodrecipe.R

@Composable
fun LoadingAnimation(
    modifier: Modifier
){
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        CircularProgressIndicator(
            modifier = Modifier.background(colorResource(id = R.color.white))
                .padding(10.dp),
            color = colorResource(id = R.color.food_green),
            trackColor = colorResource(id = R.color.light_grey),
        )
    }
}

