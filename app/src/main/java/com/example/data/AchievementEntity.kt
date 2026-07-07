package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey val id: String,
    val titleEn: String,
    val titleMy: String,
    val descriptionEn: String,
    val descriptionMy: String,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null
)
