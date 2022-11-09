package com.iulian.iancu.cocktailapp.di

import com.iulian.iancu.data.DrinksRepositoryImpl
import com.iulian.iancu.data.DrinksService
import com.iulian.iancu.domain.DrinksRepository
import com.iulian.iancu.domain.GetDrinksUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MainModule {
    @Provides
    fun provideGetCocktailsUseCase(drinksRepository: DrinksRepository): GetDrinksUseCase =
        GetDrinksUseCase(drinksRepository)

    @Provides
    fun provideDrinkService(): DrinksService = DrinksService.getInstance()

    @Provides
    fun provideDrinksRepository(drinksService: DrinksService): DrinksRepository =
        DrinksRepositoryImpl(drinksService)
}