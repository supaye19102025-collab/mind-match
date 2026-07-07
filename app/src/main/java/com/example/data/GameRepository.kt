package com.example.data

import kotlinx.coroutines.flow.Flow

class GameRepository(private val database: GameDatabase) {
    private val scoreDao = database.scoreDao()
    private val achievementDao = database.achievementDao()

    val topScores: Flow<List<ScoreEntity>> = scoreDao.getTopScores(15)
    val achievements: Flow<List<AchievementEntity>> = achievementDao.getAllAchievements()

    fun getTopScoresByDifficulty(difficulty: String): Flow<List<ScoreEntity>> {
        return scoreDao.getTopScoresByDifficulty(difficulty, 15)
    }

    suspend fun insertScore(score: ScoreEntity) {
        scoreDao.insertScore(score)
    }

    suspend fun clearAllScores() {
        scoreDao.clearAllScores()
    }

    suspend fun unlockAchievement(id: String) {
        achievementDao.unlockAchievement(id, System.currentTimeMillis())
    }

    suspend fun resetAchievements() {
        achievementDao.resetAchievements()
    }

    suspend fun seedAchievements() {
        val initialList = listOf(
            AchievementEntity(
                id = "first_win",
                titleEn = "First Victory",
                titleMy = "ဦးဆုံးအောင်ပွဲ",
                descriptionEn = "Match all cards and win your first game!",
                descriptionMy = "ပထမဆုံးပွဲကို နိုင်အောင် ကစားပြီး ကတ်အားလုံးကို တွဲဖက်လိုက်ပါ။"
            ),
            AchievementEntity(
                id = "perfect_game",
                titleEn = "Perfect Mind",
                titleMy = "ထက်မြက်သောစိတ်",
                descriptionEn = "Complete a game with over 80% accuracy!",
                descriptionMy = "တိကျမှု ၈၀ ရာခိုင်နှုန်းအထက်ဖြင့် ပွဲပြီးအောင် ကစားပါ။"
            ),
            AchievementEntity(
                id = "speed_demon",
                titleEn = "Speed Demon",
                titleMy = "အမြန်နှုန်းဘုရင်",
                descriptionEn = "Complete a game in under 45 seconds!",
                descriptionMy = "၄၅ စက္ကန့်အတွင်း ပွဲပြီးအောင် ကစားပါ။"
            ),
            AchievementEntity(
                id = "hard_conqueror",
                titleEn = "Hard Mode Master",
                titleMy = "အဆင့်မြင့်ချန်ပီယံ",
                descriptionEn = "Complete a game on Hard difficulty!",
                descriptionMy = "ခက်ခဲသောအဆင့် (Hard Mode) တွင် နိုင်အောင်ကစားပါ။"
            ),
            AchievementEntity(
                id = "score_master",
                titleEn = "Mind Master",
                titleMy = "မှတ်ဉာဏ်စွမ်းရည်ရှင်",
                descriptionEn = "Score over 1,500 points in a single match!",
                descriptionMy = "ပွဲစဉ်တစ်ခုတည်းတွင် ရမှတ် ၁,၅၀၀ ကျော်ရယူပါ။"
            )
        )
        achievementDao.insertInitialAchievements(initialList)
    }
}
