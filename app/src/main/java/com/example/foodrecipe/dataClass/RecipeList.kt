package com.example.foodrecipe.dataClass

import com.google.gson.annotations.SerializedName

data class RecipeListItemByIngredient(
    @field:SerializedName("id")
     val id: Int = 0,

    @field:SerializedName("title")
    val title: String = "",

    @field:SerializedName("image")
    val image: String = "",

    @field:SerializedName("usedIngredientCount")
    val usedIngredientCount: String = "",

    @field:SerializedName("missedIngredientCount")
    val missedIngredientCount: Int = 0
)