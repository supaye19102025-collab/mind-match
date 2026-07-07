package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "high_scores")
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val playerName: String,
    val score: Int,
    val moves: Int,
    val timeTakenSeconds: Int,
    val difficulty: String, // "Easy" (3x4), "Medium" (4x4), "Hard" (4x5 or 4x6)
    val cardTheme: String, // "Classic", "Math", "Myanmar"
    val timestamp: Long = System.currentTimeMillis()
)
