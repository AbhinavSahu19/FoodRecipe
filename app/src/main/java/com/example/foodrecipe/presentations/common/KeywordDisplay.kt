package com.example.foodrecipe.presentations.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodrecipe.R

@Composable
fun KeywordDisplay(
    list :List<String>,
    onArrowClick: (String)->Unit,
    onItemClick: (String)-> Unit
){
    LazyColumn {
        items(list){item->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(7.dp, 1.dp)
                    .background(
                        colorResource(id = R.color.white),
                        RoundedCornerShape(3.dp)
                    )
                    .border(
                        1.dp,
                        colorResource(id = R.color.light_grey),
                        RoundedCornerShape(3.dp)
                    )
                    .clickable {
                        onItemClick(item)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = item,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.food_text),
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp, 5.dp),
                )
                Icon(painter = painterResource(id = R.drawable.up_left_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(10.dp, 5.dp)
                        .size(16.dp)
                        .clickable { onArrowClick(item)},
                    colorResource(id = R.color.food_text)
                )
            }
        }
    }
}