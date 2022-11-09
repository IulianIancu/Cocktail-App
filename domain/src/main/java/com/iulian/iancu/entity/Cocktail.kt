package com.iulian.iancu.entity

data class Cocktail(
    val idDrink: String,
    val name: String,
    val image: String,
    val instructions: String,
    val ingredients: List<Pair<String, String>>
)