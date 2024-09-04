package com.example.foodrecipe.dataClass

import com.google.gson.annotations.SerializedName

data class RecipeDetails(
    @field:SerializedName("vegetarian")
    val vegetarian: Boolean = false,

    @field:SerializedName("vegan")
    val vegan: Boolean = false,

    @field:SerializedName("dairyFree")
    val dairyFree: Boolean = false,

    @field:SerializedName("veryPopular")
    val veryPopular: Boolean = false,

    @field:SerializedName("extendedIngredients")
    val ingredients: List<IngredientListItem> = emptyList(),

    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("title")
    val title: String = "",

    @field:SerializedName("readyInMinutes")
    val readyInMinutes: Int = 0,

    @field:SerializedName("servings")
    val servings: Int = 0,

    @field:SerializedName("image")
    val image: String = "",

    @field:SerializedName("nutrition")
    val nutrition: List<NutritionListItem> = emptyList(),

    @field:SerializedName("dishTypes")
    val dishTypes: List<String> = emptyList(),

    @field:SerializedName("analyzedInstructions")
    val analyzedInstructions: List<AnalyzedInstructionItem> = emptyList(),
)

data class IngredientListItem(
    @field:SerializedName("image")
    val image: String = "",

    @field:SerializedName("original")
    val original: String = ""
)

data class NutritionListItem(
    @field:SerializedName("nutrients")
    val nutrients: List<Nutrient> = emptyList(),
)

data class Nutrient(
    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("amount")
    val amount: Double = 0.0,

    @field:SerializedName("unit")
    val unit: String = "",

    @field:SerializedName("percentOfDailyNeeds")
    val percentOfDailyNeeds: Double = 0.0,
)

data class AnalyzedInstructionItem(
    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("steps")
    val steps: List<StepListItem> = emptyList(),
)
data class StepListItem(
    @field:SerializedName("number")
    val number: Int = 0,

    @field:SerializedName("step")
    val step: String = "",
)