package com.iulian.iancu.domain

import com.iulian.iancu.entity.Cocktail

class GetDrinksUseCase(private val drinksRepository: DrinksRepository) {
    private suspend fun run():List<Cocktail>{
        return drinksRepository.getDrinks()
    }
    suspend operator fun invoke():List<Cocktail>{
        return run()
    }
}