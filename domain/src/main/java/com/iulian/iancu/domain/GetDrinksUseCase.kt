package com.iulian.iancu.domain

import com.iulian.iancu.entity.Cocktail

class GetDrinksUseCase(private val drinksRepository: DrinksRepository) {
    private suspend fun run(search: String): List<Cocktail> {
        return drinksRepository.getDrinks(search)
    }

    suspend operator fun invoke(search: String): List<Cocktail> {
        return run(search)
    }
}