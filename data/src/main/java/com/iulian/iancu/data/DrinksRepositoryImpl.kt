package com.iulian.iancu.data

import android.accounts.NetworkErrorException
import com.iulian.iancu.domain.DrinksRepository
import com.iulian.iancu.entity.Cocktail

class DrinksRepositoryImpl constructor(private val retrofitService: DrinksService) :
    DrinksRepository {
    override suspend fun getDrinks(search:String): List<Cocktail> {
        val result = retrofitService.getDrinks(search)
        if (result.isSuccessful && !result.body()?.drinks.isNullOrEmpty()) {
            val cocktails = result.body()!!.drinks.map {
                Cocktail(
                    idDrink = it.idDrink,
                    name = it.strDrink,
                    image = it.strDrinkThumb,
                    instructions = it.strInstructions ?: "",
                    ingredients = getIngredientsFromDrink(it)
                )
            }
            return cocktails
        } else throw NetworkErrorException("There were no drinks in the API")
    }

    private fun getIngredientsFromDrink(drink: Drink): List<Pair<String, String>> {
        val ingredients = listOfNotNull(
            drink.strIngredient1,
            drink.strIngredient2,
            drink.strIngredient3,
            drink.strIngredient4,
            drink.strIngredient5,
            drink.strIngredient6,
            drink.strIngredient7,
            drink.strIngredient8,
            drink.strIngredient9,
            drink.strIngredient10,
            drink.strIngredient11,
            drink.strIngredient12,
            drink.strIngredient13,
            drink.strIngredient14,
            drink.strIngredient15
        )
        val measurements = listOf(
            drink.strMeasure1 ?: "",
            drink.strMeasure2 ?: "",
            drink.strMeasure3 ?: "",
            drink.strMeasure4 ?: "",
            drink.strMeasure5 ?: "",
            drink.strMeasure6 ?: "",
            drink.strMeasure7 ?: "",
            drink.strMeasure8 ?: "",
            drink.strMeasure9 ?: "",
            drink.strMeasure10 ?: "",
            drink.strMeasure11 ?: "",
            drink.strMeasure12 ?: "",
            drink.strMeasure13 ?: "",
            drink.strMeasure14 ?: "",
            drink.strMeasure15 ?: ""
        )

        return ingredients.zip(measurements)
    }

}