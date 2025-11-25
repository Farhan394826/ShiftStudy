package com.example.shiftstudy.repository

import android.util.Log
import com.example.shiftstudy.data.api.Quote
import com.example.shiftstudy.data.api.QuoteApiService

class QuoteRepository {
    private val apiService = QuoteApiService.create()

    suspend fun getRandomQuote(): Result<Quote> {
        return try {
            val quote = apiService.getRandomQuote()
            Log.d("QuoteRepository", "Quote fetched: ${quote.content}")
            Result.success(quote)
        } catch (e: Exception) {
            Log.e("QuoteRepository", "Error fetching quote: ${e.message}", e)
            Result.failure(e)
        }
    }
}