package com.example.foodrecipe.dataClass

import com.google.gson.annotations.SerializedName


data class IngredientKeyword(
    @field:SerializedName("name")
    val name: String = ""
)
