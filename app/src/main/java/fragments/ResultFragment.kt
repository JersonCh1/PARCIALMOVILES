package com.example.colorsgame.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.colorsgame.R
import com.example.colorsgame.databinding.FragmentResultBinding
import com.example.colorsgame.utils.GameUtils

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var gameUtils: GameUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar GameUtils
        gameUtils = GameUtils(requireContext())

        // Obtener puntuación del argumento usando Bundle
        val score = arguments?.getInt("score") ?: 0

        // Mostrar puntuación y puntuación más alta
        binding.tvYourScore.text = getString(R.string.your_score, score)
        binding.tvHighScore.text = getString(R.string.high_score, gameUtils.getHighScore())

        // Mostrar mensaje personalizado según la puntuación
        val messageResId = gameUtils.getScoreMessage(score)
        binding.tvResultMessage.text = getString(messageResId)

        // Animar el mensaje de resultado
        val animation = AnimationUtils.loadAnimation(requireContext(), android.R.anim.slide_in_left)
        binding.tvResultMessage.startAnimation(animation)

        // Configurar botones
        binding.btnPlayAgain.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }

        binding.btnBackToMenu.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_welcomeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}