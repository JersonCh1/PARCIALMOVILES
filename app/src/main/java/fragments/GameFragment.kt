package com.example.colorsgame.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.colorsgame.R
import com.example.colorsgame.databinding.FragmentGameBinding
import com.example.colorsgame.utils.GameUtils
import com.example.colorsgame.utils.GameUtils.Companion.COUNTDOWN_INTERVAL
import com.example.colorsgame.utils.GameUtils.Companion.GAME_DURATION_MS

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private lateinit var gameUtils: GameUtils
    private lateinit var timer: CountDownTimer

    private var score = 0
    private var timeRemaining = GAME_DURATION_MS / 1000
    private var currentColor: GameUtils.GameColor? = null
    private var textColor: GameUtils.GameColor? = null
    private var gameMode = GameUtils.GameMode.NORMAL

    // Mapa para asociar cada botón con su color correspondiente
    private val colorButtons = mutableMapOf<GameUtils.GameColor, Button>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar GameUtils
        gameUtils = GameUtils(requireContext())
        gameUtils.initSounds()

        // Inicializar los botones de colores
        initColorButtons()

        // Inicializar nivel de dificultad (Efecto Stroop)
        // Para el examen, elegimos la funcionalidad adicional del Efecto Stroop
        gameMode = GameUtils.GameMode.STROOP

        // Configurar botones de colores
        setupColorButtons()

        // Iniciar el juego
        startGame()
    }

    private fun initColorButtons() {
        colorButtons[GameUtils.GameColor.RED] = binding.btnRed
        colorButtons[GameUtils.GameColor.GREEN] = binding.btnGreen
        colorButtons[GameUtils.GameColor.BLUE] = binding.btnBlue
        colorButtons[GameUtils.GameColor.YELLOW] = binding.btnYellow
        colorButtons[GameUtils.GameColor.PURPLE] = binding.btnPurple
        colorButtons[GameUtils.GameColor.ORANGE] = binding.btnOrange
    }

    private fun setupColorButtons() {
        colorButtons.forEach { (color, button) ->
            button.setOnClickListener {
                checkAnswer(color)
            }
        }
    }

    private fun startGame() {
        // Resetear puntuación
        score = 0
        updateScoreDisplay()

        // Iniciar temporizador
        startTimer()

        // Mostrar el primer color
        displayNextColor()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(GAME_DURATION_MS, COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished / 1000
                updateTimeDisplay()
            }

            override fun onFinish() {
                timeRemaining = 0
                updateTimeDisplay()
                gameOver()
            }
        }.start()
    }

    private fun updateTimeDisplay() {
        binding.tvTimeRemaining.text = getString(R.string.time_remaining, timeRemaining)
    }

    private fun updateScoreDisplay() {
        binding.tvScore.text = getString(R.string.score, score)
    }

    private fun displayNextColor() {
        currentColor = gameUtils.getRandomColor()

        if (gameMode == GameUtils.GameMode.STROOP) {
            // En modo Stroop, el color del texto debe ser diferente al color que representa
            textColor = gameUtils.getRandomTextColor(currentColor!!)

            // Establecer el texto al nombre del color, pero en un color diferente
            binding.tvColorText.text = getString(textColor!!.nameResId)
            binding.cardColorDisplay.setCardBackgroundColor(
                ContextCompat.getColor(requireContext(), currentColor!!.colorResId)
            )
        } else {
            // En modo normal, simplemente mostrar el color
            binding.tvColorText.text = getString(R.string.color_text)
            binding.cardColorDisplay.setCardBackgroundColor(
                ContextCompat.getColor(requireContext(), currentColor!!.colorResId)
            )
        }

        // Animar el cambio de color
        gameUtils.applyFadeAnimation(binding.cardColorDisplay)
    }

    private fun checkAnswer(selectedColor: GameUtils.GameColor) {
        val correctColor = if (gameMode == GameUtils.GameMode.STROOP) {
            // En modo Stroop, la respuesta correcta es el color NOMBRADO (textColor)
            textColor
        } else {
            // En modo normal, la respuesta correcta es el color MOSTRADO (currentColor)
            currentColor
        }

        if (selectedColor == correctColor) {
            // Respuesta correcta
            score++
            updateScoreDisplay()
            gameUtils.playCorrectSound()
            gameUtils.applyPulseAnimation(colorButtons[selectedColor]!!)
        } else {
            // Respuesta incorrecta
            gameUtils.playWrongSound()
        }

        // Mostrar el siguiente color
        displayNextColor()
    }

    private fun gameOver() {
        // Guardar puntuación alta si corresponde
        gameUtils.saveHighScore(score)

        // Reproducir sonido de fin de juego
        gameUtils.playGameOverSound()

        // Navegar a la pantalla de resultados (usando Bundle en lugar de SafeArgs)
        val bundle = Bundle()
        bundle.putInt("score", score)
        findNavController().navigate(R.id.action_gameFragment_to_resultFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Cancelar el temporizador para evitar fugas de memoria
        if (::timer.isInitialized) {
            timer.cancel()
        }

        // Liberar recursos de sonido
        gameUtils.releaseSounds()

        _binding = null
    }
}