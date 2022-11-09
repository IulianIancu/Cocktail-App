package com.iulian.iancu.data

class DrinksRepository constructor(private val retrofitService: DrinksService) {
    suspend fun getDrinks() =
        retrofitService.getDrinks()
}