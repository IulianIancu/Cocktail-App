package com.iulian.iancu.domain

import com.iulian.iancu.entity.Cocktail

interface DrinksRepository {
    suspend fun getDrinks(search: String): List<Cocktail>
}