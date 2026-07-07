package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.MusicOff
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Vibration
import androidx.compose.material.icons.outlined.VolumeMute
import androidx.compose.material.icons.outlined.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ui.theme.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.AchievementEntity
import com.example.data.Card
import com.example.data.ScoreEntity
import java.text.SimpleDateFormat
import java.util.*

object GameLocales {
    val EN = mapOf(
        "title" to "MIND MATCH",
        "subtitle" to "Brain Training & Memory Puzzle",
        "start_game" to "Start Challenge",
        "how_to_play" to "How to Play",
        "high_scores" to "Leaderboard",
        "achievements" to "Achievements",
        "select_theme" to "Select Style",
        "select_category" to "Card Theme",
        "select_difficulty" to "Board Size",
        "difficulty_easy" to "Easy (3x4)",
        "difficulty_medium" to "Medium (4x4)",
        "difficulty_hard" to "Hard (4x5)",
        "cat_animals" to "Animals 🐼",
        "cat_myanmar" to "Traditional 🍛",
        "cat_math" to "Math Blitz 🔢",
        "moves" to "Moves",
        "matches" to "Matched",
        "score" to "Score",
        "combo" to "Multiplier",
        "time_spent" to "Time Spent",
        "time_limit" to "Time Limit",
        "game_over" to "GAME OVER",
        "victory" to "VICTORY!",
        "defeat" to "TIME OUT!",
        "play_again" to "Play Again",
        "main_menu" to "Home Menu",
        "save_score" to "Save High Score",
        "enter_name" to "Enter Player Name",
        "no_scores" to "No high scores saved yet.",
        "clear_scores" to "Reset Scores",
        "unlocked_achievements" to "Achievements Unlocked!",
        "how_to_play_desc" to "Tap cards to flip them and find matching pairs. In Math Blitz mode, match math equations with their corresponding results (e.g. '2 + 3' matches with '5'). Complete the board before the timer expires! Consecutive matching creates combos that boost your score exponentially.",
        "points" to "pts",
        "accuracy" to "Accuracy",
        "seconds" to "s",
        "quit_game" to "Quit Match",
        "sound" to "Sound",
        "vibe" to "Vibration",
        "theme_cosmic" to "Cosmic Dark",
        "theme_sunset" to "Sunset Peach",
        "theme_forest" to "Forest Breeze",
        "theme_royal" to "Royal Velvet"
    )
    val MY = mapOf(
        "title" to "ဉာဏ်စမ်းကတ်ပြား",
        "subtitle" to "မှတ်ဉာဏ်နှင့်ဦးနှောက်စွမ်းရည် လေ့ကျင့်ခန်း",
        "start_game" to "စတင်ကစားမည်",
        "how_to_play" to "ကစားနည်းလမ်းညွှန်",
        "high_scores" to "အမှတ်စာရင်းဇယား",
        "achievements" to "အောင်မြင်မှုဘွဲ့တံဆိပ်များ",
        "select_theme" to "ဂိမ်းအပြင်အဆင်ရွေးချယ်ရန်",
        "select_category" to "ကတ်ပြားအမျိုးအစား",
        "select_difficulty" to "ခက်ခဲမှုအဆင့်",
        "difficulty_easy" to "လွယ်ကူ (၃ x ၄)",
        "difficulty_medium" to "အလယ်အလတ် (၄ x ၄)",
        "difficulty_hard" to "ခက်ခဲ (၄ x ၅)",
        "cat_animals" to "တိရစ္ဆာန်များ 🐼",
        "cat_myanmar" to "မြန်မာ့ရိုးရာ 🍛",
        "cat_math" to "သင်္ချာဦးနှောက်စမ်း 🔢",
        "moves" to "လှန်ခဲ့သည့်အကြိမ်",
        "matches" to "တွဲဖက်မိမှု",
        "score" to "ရမှတ်",
        "combo" to "ဆင့်ပွားရမှတ်",
        "time_spent" to "ကြာချိန်",
        "time_limit" to "အချိန်ကန့်သတ်ချက်",
        "game_over" to "ဂိမ်းပြီးဆုံးပါပြီ",
        "victory" to "အောင်နိုင်သူ!",
        "defeat" to "အချိန်ကုန်ဆုံးသွားပါပြီ!",
        "play_again" to "ပြန်လည်ကစားမည်",
        "main_menu" to "ပင်မစာမျက်နှာ",
        "save_score" to "အမှတ်စာရင်းသိမ်းဆည်းမည်",
        "enter_name" to "သင့်အမည်ကိုရိုက်ထည့်ပါ",
        "no_scores" to "အမှတ်စာရင်း သိမ်းဆည်းထားခြင်းမရှိသေးပါ။",
        "clear_scores" to "အမှတ်စာရင်းများဖျက်မည်",
        "unlocked_achievements" to "ယခုပွဲတွင် ရရှိခဲ့သောအောင်မြင်မှုများ",
        "how_to_play_desc" to "ကတ်ပြားများကိုလှန်ပြီး တူညီသောအစုံများကို တွဲဖက်လိုက်ပါ။ သင်္ချာဦးနှောက်စမ်း (Math Challenge) တွင်မူ ပုစ္ဆာကတ်ပြားနှင့် အဖြေကတ်ပြားကို တွဲဖက်ရမည်ဖြစ်သည် (ဥပမာ - '2 + 3' ကတ်နှင့် '5' ကတ်)။ သတ်မှတ်ထားသောအချိန်အတွင်း ကတ်အားလုံးတွဲမိအောင် ကစားပါ။ ဆက်တိုက်တွဲမိပါက ဆင့်ပွားရမှတ်များစွာ ရရှိပါမည်။",
        "points" to "မှတ်",
        "accuracy" to "တိကျမှုနှုန်း",
        "seconds" to "စက္ကန့်",
        "quit_game" to "ဂိမ်းမှထွက်မည်",
        "sound" to "အသံ",
        "vibe" to "တုန်ခါမှု",
        "theme_cosmic" to "နဂါးငွေ့တန်း (အမှောင်)",
        "theme_sunset" to "နေဝင်ဆည်းဆာ (အလင်း)",
        "theme_forest" to "စိမ်းလန်းစိုပြေ (အလင်း)",
        "theme_royal" to "နန်းတော်သုံး (အမှောင်)"
    )

    fun get(key: String, lang: String): String {
        return if (lang == "my") {
            MY[key] ?: EN[key] ?: key
        } else {
            EN[key] ?: key
        }
    }
}

@Composable
fun MindMatchApp(viewModel: GameViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val colorTheme by viewModel.colorTheme.collectAsStateWithLifecycle()

    MindMatchTheme(themeName = colorTheme) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AnimatedContent(
                    targetState = currentScreen,
                    transitionSpec = {
                        slideInHorizontally { width -> width } + fadeIn() togetherWith
                                slideOutHorizontally { width -> -width } + fadeOut()
                    },
                    label = "screen_transition"
                ) { screen ->
                    when (screen) {
                        is Screen.Home -> HomeScreen(viewModel)
                        is Screen.Playing -> PlayingScreen(viewModel)
                        is Screen.GameOver -> GameOverScreen(viewModel)
                        is Screen.HighScores -> HighScoresScreen(viewModel)
                        is Screen.Achievements -> AchievementsScreen(viewModel)
                        is Screen.HowToPlay -> HowToPlayScreen(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: GameViewModel) {
    val language by viewModel.language.collectAsStateWithLifecycle()
    val difficulty by viewModel.difficulty.collectAsStateWithLifecycle()
    val cardTheme by viewModel.cardTheme.collectAsStateWithLifecycle()
    val colorTheme by viewModel.colorTheme.collectAsStateWithLifecycle()
    val soundEnabled by viewModel.soundEnabled.collectAsStateWithLifecycle()
    val vibrationEnabled by viewModel.vibrationEnabled.collectAsStateWithLifecycle()

    val bgBrush = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.surface
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(bgBrush)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Upper Controls (Language & Audio Settings)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Language Selector
                FilledTonalIconButton(
                    onClick = { viewModel.setLanguage(if (language == "en") "my" else "en") },
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("language_toggle_btn")
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Language,
                        contentDescription = "Change Language",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                // Title Translation / Indication
                Text(
                    text = if (language == "en") "Myanmar 🇲🇲" else "English 🇬🇧",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                // Audio / Sound Toggles
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilledTonalIconButton(
                        onClick = { viewModel.toggleSound() },
                        modifier = Modifier.testTag("sound_toggle_btn")
                    ) {
                        Icon(
                            imageVector = if (soundEnabled) Icons.Outlined.VolumeUp else Icons.Outlined.VolumeMute,
                            contentDescription = "Toggle Sound"
                        )
                    }
                    FilledTonalIconButton(
                        onClick = { viewModel.toggleVibration() },
                        modifier = Modifier.testTag("vibration_toggle_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Vibration,
                            contentDescription = "Toggle Vibration",
                            tint = if (vibrationEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }

        // Animated Brand Header Block
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                Color.Transparent
                            )
                        )
                    )
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Game Logo Badge
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .shadow(8.dp, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Lightbulb,
                            contentDescription = "Game Logo",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(45.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = GameLocales.get("title", language),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = GameLocales.get("subtitle", language),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Selection Settings Panel
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Category Selection
                    Text(
                        text = GameLocales.get("select_category", language),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Classic", "Myanmar", "Math").forEach { cat ->
                            val label = when (cat) {
                                "Classic" -> GameLocales.get("cat_animals", language)
                                "Myanmar" -> GameLocales.get("cat_myanmar", language)
                                else -> GameLocales.get("cat_math", language)
                            }
                            val isSelected = cardTheme == cat
                            Button(
                                onClick = { viewModel.setCardTheme(cat) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("cat_button_$cat"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                                    contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f))

                    // Difficulty Selection
                    Text(
                        text = GameLocales.get("select_difficulty", language),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Easy", "Medium", "Hard").forEach { diff ->
                            val label = when (diff) {
                                "Easy" -> GameLocales.get("difficulty_easy", language)
                                "Medium" -> GameLocales.get("difficulty_medium", language)
                                else -> GameLocales.get("difficulty_hard", language)
                            }
                            val isSelected = difficulty == diff
                            Button(
                                onClick = { viewModel.setDifficulty(diff) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("diff_button_$diff"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                                    contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f))

                    // Color Palette Selector
                    Text(
                        text = GameLocales.get("select_theme", language),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val themeList = listOf("Cosmic Dark", "Sunset Peach", "Forest Breeze", "Royal Velvet")
                        items(themeList.size) { index ->
                            val themeItem = themeList[index]
                            val isSelected = colorTheme == themeItem
                            val label = when (themeItem) {
                                "Cosmic Dark" -> GameLocales.get("theme_cosmic", language)
                                "Sunset Peach" -> GameLocales.get("theme_sunset", language)
                                "Forest Breeze" -> GameLocales.get("theme_forest", language)
                                else -> GameLocales.get("theme_royal", language)
                            }
                            val themeBg = when (themeItem) {
                                "Cosmic Dark" -> CosmicSurface
                                "Sunset Peach" -> SunsetSurface
                                "Forest Breeze" -> ForestSurface
                                else -> RoyalSurface
                            }
                            val themeAccent = when (themeItem) {
                                "Cosmic Dark" -> CosmicPrimary
                                "Sunset Peach" -> SunsetPrimary
                                "Forest Breeze" -> ForestPrimary
                                else -> RoyalPrimary
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp)
                                    .clickable { viewModel.setColorTheme(themeItem) }
                                    .testTag("theme_button_$themeItem"),
                                colors = CardDefaults.cardColors(
                                    containerColor = themeBg
                                ),
                                shape = RoundedCornerShape(12.dp),
                                border = if (isSelected) BorderStroke(
                                    2.dp,
                                    themeAccent
                                ) else BorderStroke(1.dp, Color.Gray.copy(alpha = 0.2f))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(14.dp)
                                            .clip(CircleShape)
                                            .background(themeAccent)
                                    )
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (themeItem == "Cosmic Dark" || themeItem == "Royal Velvet") Color.White else Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Main Navigation / Play Action Buttons
        item {
            Button(
                onClick = { viewModel.startGame() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp))
                    .testTag("play_game_btn"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PlayArrow,
                        contentDescription = "Play Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = GameLocales.get("start_game", language),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Sub Navigation Menu Options
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // High Scores button
                FilledTonalButton(
                    onClick = { viewModel.navigateTo(Screen.HighScores) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("leaderboard_menu_btn"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.History,
                        contentDescription = "High Scores"
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = GameLocales.get("high_scores", language),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Achievements button
                FilledTonalButton(
                    onClick = { viewModel.navigateTo(Screen.Achievements) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("achievements_menu_btn"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.EmojiEvents,
                        contentDescription = "Achievements"
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = GameLocales.get("achievements", language),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            // How to Play button
            OutlinedButton(
                onClick = { viewModel.navigateTo(Screen.HowToPlay) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("how_to_play_menu_btn"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.HelpOutline,
                    contentDescription = "How to play"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = GameLocales.get("how_to_play", language),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun PlayingScreen(viewModel: GameViewModel) {
    val language by viewModel.language.collectAsStateWithLifecycle()
    val cards by viewModel.cards.collectAsStateWithLifecycle()
    val moves by viewModel.moves.collectAsStateWithLifecycle()
    val matches by viewModel.matches.collectAsStateWithLifecycle()
    val score by viewModel.score.collectAsStateWithLifecycle()
    val comboMultiplier by viewModel.comboMultiplier.collectAsStateWithLifecycle()
    val timeSpent by viewModel.timeSpentSeconds.collectAsStateWithLifecycle()
    val timeLimit by viewModel.timeLimitSeconds.collectAsStateWithLifecycle()
    val difficulty by viewModel.difficulty.collectAsStateWithLifecycle()

    val timePercent = (1f - (timeSpent.toFloat() / timeLimit.toFloat())).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Playing Header (Back, Statistics)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateTo(Screen.Home) },
                modifier = Modifier.testTag("back_to_menu_playing_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Quit Game",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = "${GameLocales.get("score", language)}: $score",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary
            )

            if (comboMultiplier > 1) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "x$comboMultiplier ${GameLocales.get("combo", language)}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(50.dp))
            }
        }

        // Stats Display Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Moves Badge
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = GameLocales.get("moves", language),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$moves",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Matches Badge
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = GameLocales.get("matches", language),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    val totalPairs = when (difficulty) {
                        "Easy" -> 6
                        "Medium" -> 8
                        else -> 10
                    }
                    Text(
                        text = "$matches / $totalPairs",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Timer bar
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = GameLocales.get("time_spent", language),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
                Text(
                    text = "${timeLimit - timeSpent} ${GameLocales.get("seconds", language)}",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (timePercent < 0.25f) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            }
            LinearProgressIndicator(
                progress = timePercent,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape),
                color = if (timePercent < 0.25f) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }

        // Cards Grid (Calculates column count dynamically to fit size class)
        val columns = when (difficulty) {
            "Easy" -> 3 // 3x4
            "Medium" -> 4 // 4x4
            "Hard" -> 4 // 4x5
            else -> 4
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(cards) { index, card ->
                    CardItem(
                        card = card,
                        onClick = { viewModel.selectCard(index) },
                        modifier = Modifier
                            .aspectRatio(0.75f)
                            .testTag("card_item_$index")
                    )
                }
            }
        }
    }
}

@Composable
fun CardItem(
    card: Card,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Elegant Card Flip effect using rotation values
    val rotation by animateFloatAsState(
        targetValue = if (card.isFaceUp || card.isMatched) 180f else 0f,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "card_rotation"
    )

    // Wrong Match Wiggle/Wobble Animation using InfiniteTransition
    val isWiggling = card.isErrorState
    val wiggleOffset by animateFloatAsState(
        targetValue = if (isWiggling) 8f else 0f,
        animationSpec = repeatable(
            iterations = 4,
            animation = tween(durationMillis = 100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "card_wiggle"
    )

    Box(
        modifier = modifier
            .offset(x = wiggleOffset.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 14 * density
            }
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable(enabled = !card.isFaceUp && !card.isMatched) { onClick() }
    ) {
        if (rotation <= 90f) {
            // Card Back (Face Down Layout)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
                            )
                        )
                    )
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Futuristic aesthetic design on the back of the card
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterVintage,
                        contentDescription = "Card Decal",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        } else {
            // Card Front (Face Up Layout)
            // Rotate the content back by 180 degrees so it isn't mirrored!
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationY = 180f }
                    .background(
                        if (card.isMatched) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        else MaterialTheme.colorScheme.surface
                    )
                    .border(
                        width = if (card.isMatched) 3.dp else if (card.isErrorState) 3.dp else 1.dp,
                        color = if (card.isMatched) MaterialTheme.colorScheme.tertiary
                        else if (card.isErrorState) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (card.isMatched) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.tertiary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Matched Icon",
                            tint = MaterialTheme.colorScheme.onTertiary,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }

                // Main card displays content (Emoji or short mathematical text expression)
                Text(
                    text = card.displayValue,
                    style = if (card.displayValue.length > 2) MaterialTheme.typography.titleLarge else MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Black,
                    color = if (card.isMatched) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun GameOverScreen(viewModel: GameViewModel) {
    val language by viewModel.language.collectAsStateWithLifecycle()
    val score by viewModel.score.collectAsStateWithLifecycle()
    val moves by viewModel.moves.collectAsStateWithLifecycle()
    val timeSpent by viewModel.timeSpentSeconds.collectAsStateWithLifecycle()
    val success by viewModel.gameFinishedSuccessfully.collectAsStateWithLifecycle()
    val unlockedAchievements by viewModel.unlockedAchievementsInMatch.collectAsStateWithLifecycle()
    val playerName by viewModel.playerName.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Game Over Visual Status Section
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(
                        if (success) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.errorContainer
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (success) Icons.Default.EmojiEvents else Icons.Default.TimerOff,
                    contentDescription = "Status Icon",
                    tint = if (success) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(56.dp)
                )
            }
        }

        item {
            Text(
                text = if (success) GameLocales.get("victory", language) else GameLocales.get("defeat", language),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                color = if (success) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }

        // Stats card details
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = GameLocales.get("score", language),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "$score ${GameLocales.get("points", language)}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = GameLocales.get("moves", language),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "$moves",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = GameLocales.get("time_spent", language),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "$timeSpent ${GameLocales.get("seconds", language)}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Achievements Unlocked Section
        if (success && unlockedAchievements.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = GameLocales.get("unlocked_achievements", language),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        unlockedAchievements.forEach { achievementTitle ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Stars,
                                    contentDescription = "Star badge",
                                    tint = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = achievementTitle,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }
                        }
                    }
                }
            }
        }

        // High Score Logging Block
        if (success) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = GameLocales.get("save_score", language),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        OutlinedTextField(
                            value = playerName,
                            onValueChange = { viewModel.updatePlayerName(it) },
                            placeholder = { Text(text = GameLocales.get("enter_name", language)) },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("score_name_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline
                            )
                        )
                        Button(
                            onClick = { viewModel.saveHighScore() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("save_score_submit_btn"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = GameLocales.get("save_score", language),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Primary Menu Actions
        item {
            Button(
                onClick = { viewModel.startGame() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("play_again_btn"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = GameLocales.get("play_again", language),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            OutlinedButton(
                onClick = { viewModel.navigateTo(Screen.Home) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("back_to_menu_gameover_btn"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = GameLocales.get("main_menu", language),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun HighScoresScreen(viewModel: GameViewModel) {
    val language by viewModel.language.collectAsStateWithLifecycle()
    val scores by viewModel.topScores.collectAsStateWithLifecycle(initialValue = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { viewModel.navigateTo(Screen.Home) },
                modifier = Modifier.testTag("back_to_menu_scores_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = GameLocales.get("high_scores", language),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary
            )

            IconButton(
                onClick = { viewModel.clearScores() },
                modifier = Modifier.testTag("clear_scores_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteForever,
                    contentDescription = "Clear scores",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }

        // Leaderboard table list
        if (scores.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.History,
                        contentDescription = "Empty",
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(56.dp)
                    )
                    Text(
                        text = GameLocales.get("no_scores", language),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(scores) { scoreItem ->
                    ScoreRow(score = scoreItem, language = language)
                }
            }
        }
    }
}

@Composable
fun ScoreRow(score: ScoreEntity, language: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = score.playerName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val labelDiff = when (score.difficulty) {
                        "Easy" -> GameLocales.get("difficulty_easy", language)
                        "Medium" -> GameLocales.get("difficulty_medium", language)
                        else -> GameLocales.get("difficulty_hard", language)
                    }
                    val labelCat = when (score.cardTheme) {
                        "Classic" -> GameLocales.get("cat_animals", language)
                        "Myanmar" -> GameLocales.get("cat_myanmar", language)
                        else -> GameLocales.get("cat_math", language)
                    }
                    Text(
                        text = "$labelDiff • $labelCat",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "${score.score} ${GameLocales.get("points", language)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${score.moves} ${GameLocales.get("moves", language)} • ${score.timeTakenSeconds}s",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
fun AchievementsScreen(viewModel: GameViewModel) {
    val language by viewModel.language.collectAsStateWithLifecycle()
    val achievements by viewModel.achievements.collectAsStateWithLifecycle(initialValue = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { viewModel.navigateTo(Screen.Home) },
                modifier = Modifier.testTag("back_to_menu_achievements_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = GameLocales.get("achievements", language),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary
            )

            IconButton(
                onClick = { viewModel.resetAllAchievements() },
                modifier = Modifier.testTag("reset_achievements_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reset achievements",
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        }

        // Achievements list
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(achievements) { ach ->
                AchievementRow(achievement = ach, language = language)
            }
        }
    }
}

@Composable
fun AchievementRow(achievement: AchievementEntity, language: String) {
    val isUnlocked = achievement.isUnlocked
    val title = if (language == "my") achievement.titleMy else achievement.titleEn
    val desc = if (language == "my") achievement.descriptionMy else achievement.descriptionEn

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        border = if (isUnlocked) BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Badge Circle
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        if (isUnlocked) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isUnlocked) Icons.Default.EmojiEvents else Icons.Default.Lock,
                    contentDescription = "Badge Icon",
                    tint = if (isUnlocked) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Description block
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isUnlocked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.5f
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun HowToPlayScreen(viewModel: GameViewModel) {
    val language by viewModel.language.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateTo(Screen.Home) },
                modifier = Modifier.testTag("back_to_menu_howto_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = GameLocales.get("how_to_play", language),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.HelpOutline,
                        contentDescription = "Help Icon",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Text(
                    text = GameLocales.get("how_to_play", language),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = GameLocales.get("how_to_play_desc", language),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 26.sp
                )
            }
        }
    }
}
