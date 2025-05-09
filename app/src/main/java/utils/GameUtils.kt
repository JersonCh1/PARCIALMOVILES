package com.example.colorsgame.utils

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import com.example.colorsgame.R

/**
 * Clase de utilidades para el juego de colores
 */
class GameUtils(private val context: Context) {

    // Colores del juego
    enum class GameColor(val colorResId: Int, val nameResId: Int) {
        RED(R.color.game_red, R.string.color_red),
        GREEN(R.color.game_green, R.string.color_green),
        BLUE(R.color.game_blue, R.string.color_blue),
        YELLOW(R.color.game_yellow, R.string.color_yellow),
        PURPLE(R.color.game_purple, R.string.color_purple),
        ORANGE(R.color.game_orange, R.string.color_orange)
    }

    // Modos de juego
    enum class GameMode {
        NORMAL,   // Muestra el color y el jugador debe presionar el botón del color mostrado
        STROOP    // Muestra el nombre de un color en otro color, el jugador debe presionar el color NOMBRADO, no el mostrado
    }

    // Sonidos del juego
    private var correctSoundPlayer: MediaPlayer? = null
    private var wrongSoundPlayer: MediaPlayer? = null
    private var gameOverSoundPlayer: MediaPlayer? = null

    // SharedPreferences para guardar puntajes
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "colors_game_prefs", Context.MODE_PRIVATE
    )

    // Constante para guardar el high score
    companion object {
        const val HIGH_SCORE_KEY = "high_score"
        const val GAME_DURATION_MS = 30000L  // 30 segundos
        const val COUNTDOWN_INTERVAL = 1000L // 1 segundo
        private const val TAG = "GameUtils"
    }

    /**
     * Obtiene un color aleatorio del juego
     */
    fun getRandomColor(): GameColor {
        return GameColor.values().random()
    }

    /**
     * En modo Stroop, obtiene un color aleatorio diferente para el texto
     */
    fun getRandomTextColor(displayColor: GameColor): GameColor {
        val colors = GameColor.values().toMutableList()
        colors.remove(displayColor)
        return colors.random()
    }

    /**
     * Guarda el puntaje más alto
     */
    fun saveHighScore(score: Int) {
        try {
            val currentHighScore = getHighScore()
            if (score > currentHighScore) {
                sharedPreferences.edit().putInt(HIGH_SCORE_KEY, score).apply()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error saving high score: ${e.message}")
        }
    }

    /**
     * Obtiene el puntaje más alto guardado
     */
    fun getHighScore(): Int {
        try {
            return sharedPreferences.getInt(HIGH_SCORE_KEY, 0)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting high score: ${e.message}")
            return 0
        }
    }

    /**
     * Inicializa los sonidos del juego
     */
    fun initSounds() {
        try {
            // Intentar cargar los archivos de sonido
            if (hasRawResource("correct")) {
                correctSoundPlayer = MediaPlayer.create(context, R.raw.correct)
            }

            if (hasRawResource("wrong")) {
                wrongSoundPlayer = MediaPlayer.create(context, R.raw.wrong)
            }

            if (hasRawResource("game_over")) {
                gameOverSoundPlayer = MediaPlayer.create(context, R.raw.game_over)
            }

            Log.d(TAG, "Sound resources initialized successfully")
        } catch (e: Exception) {
            // Si hay un error, registrarlo pero no crashear
            Log.e(TAG, "Error initializing sound resources: ${e.message}")
        }
    }

    /**
     * Verifica si un recurso raw específico existe
     */
    private fun hasRawResource(resourceName: String): Boolean {
        val resourceId = context.resources.getIdentifier(
            resourceName, "raw", context.packageName
        )
        return resourceId != 0
    }

    /**
     * Reproduce el sonido de acierto
     */
    fun playCorrectSound() {
        try {
            correctSoundPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                    it.prepare()
                }
                it.start()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error playing correct sound: ${e.message}")
        }
    }

    /**
     * Reproduce el sonido de error
     */
    fun playWrongSound() {
        try {
            wrongSoundPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                    it.prepare()
                }
                it.start()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error playing wrong sound: ${e.message}")
        }
    }

    /**
     * Reproduce el sonido de fin de juego
     */
    fun playGameOverSound() {
        try {
            gameOverSoundPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                    it.prepare()
                }
                it.start()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error playing game over sound: ${e.message}")
        }
    }

    /**
     * Libera los recursos de MediaPlayer
     */
    fun releaseSounds() {
        try {
            correctSoundPlayer?.release()
            wrongSoundPlayer?.release()
            gameOverSoundPlayer?.release()

            correctSoundPlayer = null
            wrongSoundPlayer = null
            gameOverSoundPlayer = null

            Log.d(TAG, "Sound resources released successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error releasing sound resources: ${e.message}")
        }
    }

    /**
     * Aplica animación de pulso a una vista
     */
    fun applyPulseAnimation(view: View) {
        try {
            val pulseAnimation = AnimationUtils.loadAnimation(context, R.anim.pulse_animation)
            view.startAnimation(pulseAnimation)
        } catch (e: Exception) {
            Log.e(TAG, "Error applying pulse animation: ${e.message}")
        }
    }

    /**
     * Aplica animación de desvanecimiento y aparición a una vista
     */
    fun applyFadeAnimation(view: View) {
        try {
            val fadeAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_animation)
            view.startAnimation(fadeAnimation)
        } catch (e: Exception) {
            Log.e(TAG, "Error applying fade animation: ${e.message}")
        }
    }

    /**
     * Obtiene mensaje personalizado según el puntaje
     */
    fun getScoreMessage(score: Int): Int {
        return when {
            score >= 25 -> R.string.result_excellent
            score >= 15 -> R.string.result_good
            score >= 10 -> R.string.result_average
            else -> R.string.result_try_again
        }
    }
}