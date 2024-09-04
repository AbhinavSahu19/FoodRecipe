package com.example.foodrecipe.dataClass

import com.google.gson.annotations.SerializedName

data class RecipeResponseByQuery(
    @field:SerializedName("offset")
    val offset: Int = 0,

    @field:SerializedName("number")
    val number: Int = 0,

    @field:SerializedName("totalResults")
    val totalResults: Int = 0,

    @field:SerializedName("results")
    val results: List<RecipeListItemByQuery> = emptyList()
)

data class RecipeListItemByQuery(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("title")
    val title: String = "",

    @field:SerializedName("image")
    val image: String = "",

    )