package com.example.shiftstudy.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface QuoteApiService {

    @GET("random?tags=education|learning|wisdom|success|inspirational")
    suspend fun getRandomQuote(): Quote

    companion object {
        private const val BASE_URL = "https://api.quotable.io/"

        fun create(): QuoteApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(QuoteApiService::class.java)
        }
    }
}