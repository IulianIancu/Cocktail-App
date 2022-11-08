package com.iulian.iancu.cocktailapp.data

class DrinksRepository constructor(private val retrofitService: DrinksService) {
    suspend fun getDrinks() =
        retrofitService.getDrinks()
}