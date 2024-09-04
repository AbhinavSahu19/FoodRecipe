package com.example.foodrecipe.dataClass

import com.google.gson.annotations.SerializedName


data class RecipeKeyword(
    @field:SerializedName("title")
    val title: String = ""
)
