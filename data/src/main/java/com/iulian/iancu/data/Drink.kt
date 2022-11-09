package com.iulian.iancu.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Drink(
    @Expose
    @SerializedName("idDrink")
    val idDrink: String,
    @Expose
    @SerializedName("strDrink")
    val strDrink: String,
    @Expose
    @SerializedName("strDrinkThumb")
    val strDrinkThumb: String,
    @Expose
    @SerializedName("strDrinkAlternate")
    val strDrinkAlternate: String?,
    @Expose
    @SerializedName("strTags")
    val strTags: String?,
    @Expose
    @SerializedName("strVideo")
    val strVideo: String?,
    @Expose
    @SerializedName("strCategory")
    val strCategory: String?,
    @Expose
    @SerializedName("strIBA")
    val strIBA: String?,
    @Expose
    @SerializedName("strAlcoholic")
    val strAlcoholic: String?,
    @Expose
    @SerializedName("strGlass")
    val strGlass: String?,
    @Expose
    @SerializedName("strInstructions")
    val strInstructions: String?,
    @Expose
    @SerializedName("strInstructionsES")
    val strInstructionsES: String?,
    @Expose
    @SerializedName("strInstructionsDE")
    val strInstructionsDE: String?,
    @Expose
    @SerializedName("strInstructionsFR")
    val strInstructionsFR: String?,
    @Expose
    @SerializedName("strInstructionsIT")
    val strInstructionsIT: String?,
    @Expose
    @SerializedName("strInstructionsZH-HANS")
    val strInstructionsZH_HANS: String?,
    @Expose
    @SerializedName("strInstructionsZH-HANT")
    val strInstructionsZH_HANT: String?,
    @Expose
    @SerializedName("strIngredient1")
    val strIngredient1: String?,
    @Expose
    @SerializedName("strIngredient2")
    val strIngredient2: String?,
    @Expose
    @SerializedName("strIngredient3")
    val strIngredient3: String?,
    @Expose
    @SerializedName("strIngredient4")
    val strIngredient4: String?,
    @Expose
    @SerializedName("strIngredient5")
    val strIngredient5: String?,
    @Expose
    @SerializedName("strIngredient6")
    val strIngredient6: String?,
    @Expose
    @SerializedName("strIngredient7")
    val strIngredient7: String?,
    @Expose
    @SerializedName("strIngredient8")
    val strIngredient8: String?,
    @Expose
    @SerializedName("strIngredient9")
    val strIngredient9: String?,
    @Expose
    @SerializedName("strIngredient10")
    val strIngredient10: String?,
    @Expose
    @SerializedName("strIngredient11")
    val strIngredient11: String?,
    @Expose
    @SerializedName("strIngredient12")
    val strIngredient12: String?,
    @Expose
    @SerializedName("strIngredient13")
    val strIngredient13: String?,
    @Expose
    @SerializedName("strIngredient14")
    val strIngredient14: String?,
    @Expose
    @SerializedName("strIngredient15")
    val strIngredient15: String?,
    @Expose
    @SerializedName("strMeasure1")
    val strMeasure1: String?,
    @Expose
    @SerializedName("strMeasure2")
    val strMeasure2: String?,
    @Expose
    @SerializedName("strMeasure3")
    val strMeasure3: String?,
    @Expose
    @SerializedName("strMeasure4")
    val strMeasure4: String?,
    @Expose
    @SerializedName("strMeasure5")
    val strMeasure5: String?,
    @Expose
    @SerializedName("strMeasure6")
    val strMeasure6: String?,
    @Expose
    @SerializedName("strMeasure7")
    val strMeasure7: String?,
    @Expose
    @SerializedName("strMeasure8")
    val strMeasure8: String?,
    @Expose
    @SerializedName("strMeasure9")
    val strMeasure9: String?,
    @Expose
    @SerializedName("strMeasure10")
    val strMeasure10: String?,
    @Expose
    @SerializedName("strMeasure11")
    val strMeasure11: String?,
    @Expose
    @SerializedName("strMeasure12")
    val strMeasure12: String?,
    @Expose
    @SerializedName("strMeasure13")
    val strMeasure13: String?,
    @Expose
    @SerializedName("strMeasure14")
    val strMeasure14: String?,
    @Expose
    @SerializedName("strMeasure15")
    val strMeasure15: String?,
    @Expose
    @SerializedName("strImageSource")
    val strImageSource: String?,
    @Expose
    @SerializedName("strImageAttribution")
    val strImageAttribution: String?,
    @Expose
    @SerializedName("strCreativeCommonsConfirmed")
    val strCreativeCommonsConfirmed: String?,
    @Expose
    @SerializedName("dateModified")
    val dateModified: String?
)