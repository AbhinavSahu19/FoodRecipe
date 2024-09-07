package com.example.foodrecipe.presentations.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.foodrecipe.R

@Composable
fun GeneralBottomBar(){
    BottomAppBar(
        containerColor = colorResource(id = R.color.white),
        modifier = Modifier
            .fillMaxWidth()
            .height(15.dp)
    ){}
}
