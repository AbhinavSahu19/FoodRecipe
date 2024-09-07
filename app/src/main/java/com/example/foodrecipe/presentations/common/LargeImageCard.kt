package com.example.foodrecipe.presentations.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.foodrecipe.R

@Composable
fun LargeImageCard(
    image: String
){
    Card(
        modifier = Modifier.padding(10.dp,3.dp)
            .fillMaxWidth()
            .height(250.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white)
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 0.dp
        ),
        shape = RoundedCornerShape(10.dp)

    ) {
        if(image.isNullOrEmpty()){
            Column {
                Text(text = "No Image found",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                    )
            }
        }
        else{
            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
                contentDescription = "image",
                modifier = Modifier
                    .weight(1f)
//                    .height(100.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
    }
}