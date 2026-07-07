package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Card
import com.example.data.GameDatabase
import com.example.data.GameRepository
import com.example.data.ScoreEntity
import com.example.data.AchievementEntity
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed class Screen {
    object Home : Screen()
    object Playing : Screen()
    object GameOver : Screen()
    object HighScores : Screen()
    object Achievements : Screen()
    object HowToPlay : Screen()
}

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val database = GameDatabase.getDatabase(application)
    private val repository = GameRepository(database)

    // UI flows from DB
    val topScores = repository.topScores
    val achievements = repository.achievements

    // Game state states
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards: StateFlow<List<Card>> = _cards.asStateFlow()

    private val _difficulty = MutableStateFlow("Medium") // Easy, Medium, Hard
    val difficulty: StateFlow<String> = _difficulty.asStateFlow()

    private val _cardTheme = MutableStateFlow("Classic") // Classic, Math, Myanmar
    val cardTheme: StateFlow<String> = _cardTheme.asStateFlow()

    private val _colorTheme = MutableStateFlow("Cosmic Dark") // Cosmic Dark, Sunset, Forest, Royal
    val colorTheme: StateFlow<String> = _colorTheme.asStateFlow()

    private val _language = MutableStateFlow("my") // "my" for Burmese, "en" for English
    val language: StateFlow<String> = _language.asStateFlow()

    private val _moves = MutableStateFlow(0)
    val moves: StateFlow<Int> = _moves.asStateFlow()

    private val _matches = MutableStateFlow(0)
    val matches: StateFlow<Int> = _matches.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    private val _comboMultiplier = MutableStateFlow(1)
    val comboMultiplier: StateFlow<Int> = _comboMultiplier.asStateFlow()

    private val _timeSpentSeconds = MutableStateFlow(0)
    val timeSpentSeconds: StateFlow<Int> = _timeSpentSeconds.asStateFlow()

    private val _timeLimitSeconds = MutableStateFlow(120)
    val timeLimitSeconds: StateFlow<Int> = _timeLimitSeconds.asStateFlow()

    private val _soundEnabled = MutableStateFlow(true)
    val soundEnabled: StateFlow<Boolean> = _soundEnabled.asStateFlow()

    private val _vibrationEnabled = MutableStateFlow(true)
    val vibrationEnabled: StateFlow<Boolean> = _vibrationEnabled.asStateFlow()

    private val _playerName = MutableStateFlow("")
    val playerName: StateFlow<String> = _playerName.asStateFlow()

    private val _gameFinishedSuccessfully = MutableStateFlow(false)
    val gameFinishedSuccessfully: StateFlow<Boolean> = _gameFinishedSuccessfully.asStateFlow()

    private val _unlockedAchievementsInMatch = MutableStateFlow<List<String>>(emptyList())
    val unlockedAchievementsInMatch: StateFlow<List<String>> = _unlockedAchievementsInMatch.asStateFlow()

    private var timerJob: Job? = null
    private var firstFlippedCardIndex: Int? = null
    private var isProcessingFlip = false

    // Sound alert trigger flow (so UI can play sound effects)
    private val _soundTrigger = MutableStateFlow<SoundType?>(null)
    val soundTrigger: StateFlow<SoundType?> = _soundTrigger.asStateFlow()

    enum class SoundType {
        FLIP, MATCH, MISMATCH, WIN, GAME_OVER, CLICK
    }

    init {
        viewModelScope.launch {
            // Seed initial achievements if not already present
            repository.seedAchievements()
        }
    }

    fun triggerSoundHandled() {
        _soundTrigger.value = null
    }

    fun triggerSound(type: SoundType) {
        if (_soundEnabled.value) {
            _soundTrigger.value = type
        }
    }

    fun navigateTo(screen: Screen) {
        triggerSound(SoundType.CLICK)
        _currentScreen.value = screen
        if (screen is Screen.Home) {
            stopTimer()
        }
    }

    fun setDifficulty(diff: String) {
        triggerSound(SoundType.CLICK)
        _difficulty.value = diff
        _timeLimitSeconds.value = when (diff) {
            "Easy" -> 60
            "Medium" -> 90
            "Hard" -> 150
            else -> 90
        }
    }

    fun setCardTheme(theme: String) {
        triggerSound(SoundType.CLICK)
        _cardTheme.value = theme
    }

    fun setColorTheme(theme: String) {
        triggerSound(SoundType.CLICK)
        _colorTheme.value = theme
    }

    fun setLanguage(lang: String) {
        triggerSound(SoundType.CLICK)
        _language.value = lang
    }

    fun toggleSound() {
        _soundEnabled.value = !_soundEnabled.value
        triggerSound(SoundType.CLICK)
    }

    fun toggleVibration() {
        _vibrationEnabled.value = !_vibrationEnabled.value
        triggerSound(SoundType.CLICK)
    }

    fun updatePlayerName(name: String) {
        _playerName.value = name
    }

    // Classic Animal Emojis
    private val animalEmojis = listOf(
        "🐼", "🦁", "🐯", "🦊", "🐨", "🐵", "🐸", "🐙", "🦄", "🦖", "🐝", "🦉"
    )

    // Myanmar Emojis and Food
    private val myanmarSymbols = listOf(
        "🍛", "🍜", "🥭", "🏵️", "🛕", "🔔", "🐅", "🎭", "🥥", "🪁", "🏰", "🎻"
    )

    // Math Challenge Pairs (Q, A)
    private val mathPairs = listOf(
        Pair("2 + 3", "5"),
        Pair("4 x 3", "12"),
        Pair("15 - 7", "8"),
        Pair("30 / 10", "3"),
        Pair("9 + 7", "16"),
        Pair("6 x 4", "24"),
        Pair("50 - 10", "40"),
        Pair("18 / 2", "9"),
        Pair("13 + 13", "26"),
        Pair("7 x 5", "35"),
        Pair("14 - 4", "10"),
        Pair("100 / 5", "20")
    )

    fun startGame() {
        triggerSound(SoundType.CLICK)
        _moves.value = 0
        _matches.value = 0
        _score.value = 0
        _comboMultiplier.value = 1
        _timeSpentSeconds.value = 0
        _gameFinishedSuccessfully.value = false
        _unlockedAchievementsInMatch.value = emptyList()
        firstFlippedCardIndex = null
        isProcessingFlip = false

        // Generate and shuffle cards
        val numPairs = when (_difficulty.value) {
            "Easy" -> 6 // 3x4 grid
            "Medium" -> 8 // 4x4 grid
            "Hard" -> 10 // 4x5 grid
            else -> 8
        }

        val cardsList = mutableListOf<Card>()
        when (_cardTheme.value) {
            "Classic" -> {
                val selectedEmojis = animalEmojis.shuffled().take(numPairs)
                selectedEmojis.forEachIndexed { index, emoji ->
                    cardsList.add(Card(id = index * 2, pairId = index, displayValue = emoji))
                    cardsList.add(Card(id = index * 2 + 1, pairId = index, displayValue = emoji))
                }
            }
            "Myanmar" -> {
                val selectedSymbols = myanmarSymbols.shuffled().take(numPairs)
                selectedSymbols.forEachIndexed { index, symbol ->
                    cardsList.add(Card(id = index * 2, pairId = index, displayValue = symbol))
                    cardsList.add(Card(id = index * 2 + 1, pairId = index, displayValue = symbol))
                }
            }
            "Math" -> {
                val selectedMath = mathPairs.shuffled().take(numPairs)
                selectedMath.forEachIndexed { index, pair ->
                    // Question card
                    cardsList.add(Card(id = index * 2, pairId = index, displayValue = pair.first))
                    // Answer card
                    cardsList.add(Card(id = index * 2 + 1, pairId = index, displayValue = pair.second))
                }
            }
        }

        _cards.value = cardsList.shuffled()
        _currentScreen.value = Screen.Playing
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _timeSpentSeconds.value += 1
                if (_timeSpentSeconds.value >= _timeLimitSeconds.value) {
                    onGameOver(success = false)
                    break
                }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun selectCard(index: Int) {
        if (isProcessingFlip) return
        val currentCards = _cards.value.toMutableList()
        val selectedCard = currentCards[index]

        // Ignore if already face up or matched
        if (selectedCard.isFaceUp || selectedCard.isMatched) return

        triggerSound(SoundType.FLIP)

        // Flip card face up
        currentCards[index] = selectedCard.copy(isFaceUp = true)
        _cards.value = currentCards

        if (firstFlippedCardIndex == null) {
            firstFlippedCardIndex = index
        } else {
            val firstIndex = firstFlippedCardIndex!!
            if (firstIndex == index) return // Same card clicked, ignore

            isProcessingFlip = true
            _moves.value += 1

            viewModelScope.launch {
                delay(600) // Small delay for user to see the flipped card

                val firstCard = currentCards[firstIndex]
                val secondCard = currentCards[index]

                if (firstCard.pairId == secondCard.pairId) {
                    // Match!
                    triggerSound(SoundType.MATCH)
                    currentCards[firstIndex] = firstCard.copy(isMatched = true, isFaceUp = true)
                    currentCards[index] = secondCard.copy(isMatched = true, isFaceUp = true)
                    _cards.value = currentCards

                    _matches.value += 1
                    val currentCombo = _comboMultiplier.value
                    _score.value += 100 * currentCombo
                    _comboMultiplier.value += 1

                    // Check win condition
                    val totalPairs = when (_difficulty.value) {
                        "Easy" -> 6
                        "Medium" -> 8
                        "Hard" -> 10
                        else -> 8
                    }

                    if (_matches.value == totalPairs) {
                        onGameOver(success = true)
                    }
                } else {
                    // Mismatch
                    triggerSound(SoundType.MISMATCH)
                    
                    // Brief error animation wiggle state
                    currentCards[firstIndex] = firstCard.copy(isErrorState = true)
                    currentCards[index] = secondCard.copy(isErrorState = true)
                    _cards.value = currentCards
                    
                    delay(500) // Wiggle display duration

                    // Flip back down
                    currentCards[firstIndex] = firstCard.copy(isFaceUp = false, isErrorState = false)
                    currentCards[index] = secondCard.copy(isFaceUp = false, isErrorState = false)
                    _cards.value = currentCards

                    _comboMultiplier.value = 1 // Reset combo
                }

                firstFlippedCardIndex = null
                isProcessingFlip = false
            }
        }
    }

    private fun onGameOver(success: Boolean) {
        stopTimer()
        _gameFinishedSuccessfully.value = success
        if (success) {
            triggerSound(SoundType.WIN)
            checkAndUnlockAchievements()
        } else {
            triggerSound(SoundType.GAME_OVER)
        }
        _currentScreen.value = Screen.GameOver
    }

    private fun checkAndUnlockAchievements() {
        viewModelScope.launch {
            val unlockedNow = mutableListOf<String>()

            // 1. First Win
            repository.unlockAchievement("first_win")
            unlockedNow.add("first_win")

            // 2. Perfect Mind (Accuracy >= 80%)
            val pairs = when (_difficulty.value) {
                "Easy" -> 6
                "Medium" -> 8
                "Hard" -> 10
                else -> 8
            }
            val accuracy = (pairs.toFloat() / _moves.value.toFloat()) * 100
            if (accuracy >= 80f) {
                repository.unlockAchievement("perfect_game")
                unlockedNow.add("perfect_game")
            }

            // 3. Speed Demon (< 45 seconds)
            if (_timeSpentSeconds.value < 45) {
                repository.unlockAchievement("speed_demon")
                unlockedNow.add("speed_demon")
            }

            // 4. Hard Mode Champion
            if (_difficulty.value == "Hard") {
                repository.unlockAchievement("hard_conqueror")
                unlockedNow.add("hard_conqueror")
            }

            // 5. High Score Master (> 1500 score)
            if (_score.value > 1500) {
                repository.unlockAchievement("score_master")
                unlockedNow.add("score_master")
            }

            // Fetch newly unlocked to display inside Game Over screen
            val allAchievements = achievements.first()
            val newlyUnlockedDetails = allAchievements.filter {
                it.id in unlockedNow && !it.isUnlocked
            }.map { if (_language.value == "my") it.titleMy else it.titleEn }

            _unlockedAchievementsInMatch.value = newlyUnlockedDetails
        }
    }

    fun saveHighScore() {
        val name = _playerName.value.trim().ifEmpty { 
            if (_language.value == "my") "မှတ်တမ်းမဲ့သူရဲကောင်း" else "Anonymous Hero" 
        }
        viewModelScope.launch {
            repository.insertScore(
                ScoreEntity(
                    playerName = name,
                    score = _score.value,
                    moves = _moves.value,
                    timeTakenSeconds = _timeSpentSeconds.value,
                    difficulty = _difficulty.value,
                    cardTheme = _cardTheme.value
                )
            )
            triggerSound(SoundType.CLICK)
            _currentScreen.value = Screen.HighScores
        }
    }

    fun clearScores() {
        viewModelScope.launch {
            repository.clearAllScores()
            triggerSound(SoundType.CLICK)
        }
    }

    fun resetAllAchievements() {
        viewModelScope.launch {
            repository.resetAchievements()
            triggerSound(SoundType.CLICK)
        }
    }
}
