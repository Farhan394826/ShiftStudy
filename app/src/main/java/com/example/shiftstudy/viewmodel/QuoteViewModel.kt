package com.example.shiftstudy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shiftstudy.data.api.Quote
import com.example.shiftstudy.repository.QuoteRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

sealed class QuoteState {
    object Idle : QuoteState()
    object Loading : QuoteState()
    data class Success(val quote: Quote) : QuoteState()
    data class Error(val message: String) : QuoteState()
}

class QuoteViewModel : ViewModel() {
    private val repository = QuoteRepository()

    var quoteState by mutableStateOf<QuoteState>(QuoteState.Idle)
        private set

    // Fallback educational quotes
    private val fallbackQuotes = listOf(
        Quote("Education is the most powerful weapon which you can use to change the world.", "Nelson Mandela"),
        Quote("The beautiful thing about learning is that no one can take it away from you.", "B.B. King"),
        Quote("Live as if you were to die tomorrow. Learn as if you were to live forever.", "Mahatma Gandhi"),
        Quote("An investment in knowledge pays the best interest.", "Benjamin Franklin"),
        Quote("The more that you read, the more things you will know. The more that you learn, the more places you'll go.", "Dr. Seuss"),
        Quote("Education is not preparation for life; education is life itself.", "John Dewey"),
        Quote("The mind is not a vessel to be filled, but a fire to be kindled.", "Plutarch"),
        Quote("Learning never exhausts the mind.", "Leonardo da Vinci"),
        Quote("The expert in anything was once a beginner.", "Helen Hayes"),
        Quote("Success is the sum of small efforts repeated day in and day out.", "Robert Collier")
    )

    fun fetchQuote() {
        viewModelScope.launch {
            quoteState = QuoteState.Loading

            val result = repository.getRandomQuote()

            quoteState = if (result.isSuccess) {
                QuoteState.Success(result.getOrNull()!!)
            } else {
                // Use fallback quote if API fails
                val randomQuote = fallbackQuotes[Random.nextInt(fallbackQuotes.size)]
                QuoteState.Success(randomQuote)
            }
        }
    }
}