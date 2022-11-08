package com.iulian.iancu.cocktailapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iulian.iancu.cocktailapp.data.DrinksRepository

class MainViewModelFactory(
    private val drinksRepository: DrinksRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(drinksRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}