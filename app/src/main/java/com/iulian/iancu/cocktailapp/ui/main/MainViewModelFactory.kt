package com.iulian.iancu.cocktailapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iulian.iancu.data.DrinksRepositoryImpl
import com.iulian.iancu.domain.GetDrinksUseCase

class MainViewModelFactory(
    private val getDrinksUseCase: GetDrinksUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(getDrinksUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}