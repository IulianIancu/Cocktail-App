package com.iulian.iancu.data

import android.accounts.NetworkErrorException
import com.iulian.iancu.domain.DrinksRepository
import com.iulian.iancu.entity.Cocktail

class DrinksRepositoryImpl constructor(private val retrofitService: DrinksService) :
    DrinksRepository {
    override suspend fun getDrinks(search: String): List<Cocktail> {
        val result = retrofitService.getDrinks(search)
        if (result.isSuccessful && !result.body()?.drinks.isNullOrEmpty()) {
            return result.body()?.drinks?.map {
                Cocktail(
                    idDrink = it.idDrink,
                    name = it.strDrink,
                    image = it.strDrinkThumb,
                    instructions = it.strInstructions ?: "",
                    ingredients = getIngredientsFromDrink(it)
                )
            } ?: emptyList()

        } else throw NetworkErrorException("There were no drinks in the API")
    }

    private fun getIngredientsFromDrink(drink: Drink): List<Pair<String, String>> {

        drink.apply {
            val ingredients = listOfNotNull(
                strIngredient1,
                strIngredient2,
                strIngredient3,
                strIngredient4,
                strIngredient5,
                strIngredient6,
                strIngredient7,
                strIngredient8,
                strIngredient9,
                strIngredient10,
                strIngredient11,
                strIngredient12,
                strIngredient13,
                strIngredient14,
                strIngredient15
            )
            val measurements = listOf(
                strMeasure1 ?: "",
                strMeasure2 ?: "",
                strMeasure3 ?: "",
                strMeasure4 ?: "",
                strMeasure5 ?: "",
                strMeasure6 ?: "",
                strMeasure7 ?: "",
                strMeasure8 ?: "",
                strMeasure9 ?: "",
                strMeasure10 ?: "",
                strMeasure11 ?: "",
                strMeasure12 ?: "",
                strMeasure13 ?: "",
                strMeasure14 ?: "",
                strMeasure15 ?: ""
            )

            return ingredients.zip(measurements)
        }
    }

}