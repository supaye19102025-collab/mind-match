package com.example

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.GameViewModel
import com.example.ui.GameViewModel.SoundType
import com.example.ui.MindMatchApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: GameViewModel = viewModel()
            val soundTrigger by viewModel.soundTrigger.collectAsStateWithLifecycle()
            val vibrationEnabled by viewModel.vibrationEnabled.collectAsStateWithLifecycle()

            // Observe sound & vibration triggers reactively from ViewModel
            LaunchedEffect(soundTrigger) {
                soundTrigger?.let { type ->
                    // Play synth tone
                    playBeepTone(type)

                    // Vibrate if enabled
                    if (vibrationEnabled) {
                        triggerVibration(this@MainActivity, type)
                    }

                    // Reset sound trigger
                    viewModel.triggerSoundHandled()
                }
            }

            MindMatchApp(viewModel = viewModel)
        }
    }

    private fun playBeepTone(type: SoundType) {
        try {
            // Using standard Android system ToneGenerator for low latency, zero-resource dependency audio beep
            val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 70)
            when (type) {
                SoundType.FLIP -> {
                    // Quick high pitch flip beep
                    toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 70)
                }
                SoundType.MATCH -> {
                    // Cheerful rising sound
                    toneGenerator.startTone(ToneGenerator.TONE_DTMF_3, 150)
                }
                SoundType.MISMATCH -> {
                    // Low warning pitch mismatch buzzy sound
                    toneGenerator.startTone(ToneGenerator.TONE_PROP_NACK, 180)
                }
                SoundType.WIN -> {
                    // Celebratory double-tone victory burst
                    toneGenerator.startTone(ToneGenerator.TONE_PROP_PROMPT, 350)
                }
                SoundType.GAME_OVER -> {
                    // Sad downward-trending buzzer beep
                    toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP2, 350)
                }
                SoundType.CLICK -> {
                    // Microscopic navigation feedback click
                    toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP, 40)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun triggerVibration(context: Context, type: SoundType) {
        try {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (vibrator.hasVibrator()) {
                val duration = when (type) {
                    SoundType.FLIP -> 20L
                    SoundType.MATCH -> 60L
                    SoundType.MISMATCH -> 120L
                    SoundType.WIN -> 250L
                    SoundType.GAME_OVER -> 300L
                    SoundType.CLICK -> 10L
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val amplitude = when (type) {
                        SoundType.MISMATCH -> VibrationEffect.DEFAULT_AMPLITUDE
                        SoundType.WIN -> VibrationEffect.DEFAULT_AMPLITUDE
                        else -> 50 // Soft vibration for regular clicks/flips
                    }
                    vibrator.vibrate(VibrationEffect.createOneShot(duration, amplitude))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(duration)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
