package com.iulian.iancu.cocktailapp.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Drinks(
    @Expose
    @SerializedName("drinks")
    val drinks: List<Drink>
)