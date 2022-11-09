package com.iulian.iancu.domain

import com.iulian.iancu.entity.Cocktail

interface DrinksRepository {
    suspend fun getDrinks(): List<Cocktail>
}