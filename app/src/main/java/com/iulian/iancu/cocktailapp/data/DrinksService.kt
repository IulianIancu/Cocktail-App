package com.iulian.iancu.cocktailapp.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinksService {
    @GET("search.php")
    suspend fun getDrinks(
        @Query("s") amount: String = "",
    ): Response<Drinks>

    companion object {
        var retrofitService: DrinksService? = null
        fun getInstance(): DrinksService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(DrinksService::class.java)
            }
            return retrofitService!!
        }
    }
}