package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {
    @Query("SELECT * FROM high_scores ORDER BY score DESC, timeTakenSeconds ASC LIMIT :limit")
    fun getTopScores(limit: Int = 10): Flow<List<ScoreEntity>>

    @Query("SELECT * FROM high_scores WHERE difficulty = :difficulty ORDER BY score DESC, timeTakenSeconds ASC LIMIT :limit")
    fun getTopScoresByDifficulty(difficulty: String, limit: Int = 10): Flow<List<ScoreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: ScoreEntity)

    @Query("DELETE FROM high_scores")
    suspend fun clearAllScores()
}

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements")
    fun getAllAchievements(): Flow<List<AchievementEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInitialAchievements(achievements: List<AchievementEntity>)

    @Query("UPDATE achievements SET isUnlocked = 1, unlockedAt = :timestamp WHERE id = :id AND isUnlocked = 0")
    suspend fun unlockAchievement(id: String, timestamp: Long)

    @Query("UPDATE achievements SET isUnlocked = 0, unlockedAt = NULL")
    suspend fun resetAchievements()
}
