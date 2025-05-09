package com.example.colorsgame.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.colorsgame.R
import com.example.colorsgame.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Animación para el título
        val titleAnimation = AnimationUtils.loadAnimation(requireContext(), android.R.anim.fade_in)
        titleAnimation.duration = 1500
        binding.tvWelcomeTitle.startAnimation(titleAnimation)

        // Animación para la descripción
        val descriptionAnimation = AnimationUtils.loadAnimation(requireContext(), android.R.anim.slide_in_left)
        descriptionAnimation.duration = 1200
        binding.tvWelcomeDescription.startAnimation(descriptionAnimation)

        // Animaciones para las burbujas de colores
        val bubbleAnimation = AnimationUtils.loadAnimation(requireContext(), android.R.anim.slide_in_left)
        bubbleAnimation.duration = 1000
        binding.colorBubbles.startAnimation(bubbleAnimation)

        // Configurar el botón para iniciar el juego
        binding.btnStartGame.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_gameFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}