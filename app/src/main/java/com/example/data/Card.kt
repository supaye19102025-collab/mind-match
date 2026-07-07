package com.example.data

data class Card(
    val id: Int,
    val pairId: Int, // Cards with the same pairId match
    val displayValue: String, // Can be emoji (e.g. "🦁") or text (e.g. "3 + 5" or "8")
    val isFaceUp: Boolean = false,
    val isMatched: Boolean = false,
    val isErrorState: Boolean = false // Set to true briefly for wrong match wiggle
)
