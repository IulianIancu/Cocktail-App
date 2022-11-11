@file:OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.iulian.iancu.cocktailapp

import com.iulian.iancu.cocktailapp.ui.main.Error.*
import com.iulian.iancu.cocktailapp.ui.main.MainViewModel
import com.iulian.iancu.data.Drink
import com.iulian.iancu.data.Drinks
import com.iulian.iancu.data.DrinksRepositoryImpl
import com.iulian.iancu.data.DrinksService
import com.iulian.iancu.domain.GetDrinksUseCase
import com.iulian.iancu.entity.Cocktail
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainViewModelTest {
    private lateinit var viewModel: MainViewModel
    private lateinit var getDrinksUseCase: GetDrinksUseCase
    private lateinit var drinksRepository: DrinksRepositoryImpl

    @MockK
    lateinit var drinksService: DrinksService

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        Dispatchers.setMain(mainThreadSurrogate)
        drinksRepository = DrinksRepositoryImpl(drinksService)
        getDrinksUseCase = GetDrinksUseCase(drinksRepository)
        viewModel = MainViewModel(getDrinksUseCase)
    }

    @Test
    fun givenSearchIsBlankAndCallSucceeds_usecaseGivesCorrectList() = runTest {
        val testDrink = Drink(
            idDrink = "1",
            strDrink = "1",
            strDrinkThumb = "1",
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
        )

        val testResult = Cocktail("1","1","1","", emptyList())
        coEvery { drinksService.getDrinks("") } returns Response.success(Drinks(listOf(testDrink)))

        val result = getDrinksUseCase("")
        coVerify { drinksRepository.getDrinks("") }
        coVerify { drinksService.getDrinks("") }

        Assert.assertEquals(testResult,result)
    }

    @Test
    fun givenSearchIsPopulatedAndCallSucceeds_usecaseGivesCorrectList() = runTest {
        val testDrink = Drink(
            idDrink = "2",
            strDrink = "2",
            strDrinkThumb = "2",
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
        )

        val testResult = Cocktail("2","2","2","", emptyList())
        coEvery { drinksService.getDrinks("") } returns Response.success(Drinks(listOf(testDrink)))
        val result = getDrinksUseCase("Margarita")
        coVerify { drinksRepository.getDrinks("Margarita") }
        coVerify { drinksService.getDrinks("Margarita") }

        Assert.assertEquals(testResult,result)
    }

    @Test
    fun givenSearchIsPopulatedAndCallFails_ViewModelStateBecomesError() = runTest {
        coEvery { drinksService.getDrinks("") } returns Response.error(404, fakeError)
        viewModel.getDrinks("Margerita")
        coVerify { drinksRepository.getDrinks("Margerita") }
        coVerify { drinksService.getDrinks("Margerita") }

        Assert.assertEquals(Unknown,viewModel.state.value?.error)
    }

    @Test
    fun givenSearchIsPopulatedAndCallFailsNoDrinks_ViewModelStateBecomesError() = runTest {

        coEvery { drinksService.getDrinks("") } returns Response.success(Drinks(emptyList()))
        viewModel.getDrinks("Margerita")
        coVerify { drinksRepository.getDrinks("Margerita") }
        coVerify { drinksService.getDrinks("Margerita") }

        Assert.assertEquals(Network,viewModel.state.value?.error)
    }

    object fakeError :ResponseBody() {
        override fun contentLength(): Long {
            TODO("Not yet implemented")
        }

        override fun contentType(): MediaType? {
            TODO("Not yet implemented")
        }

        override fun source(): BufferedSource {
            TODO("Not yet implemented")
        }
    }
}