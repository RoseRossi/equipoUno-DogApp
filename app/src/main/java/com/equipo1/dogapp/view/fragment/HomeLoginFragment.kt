package com.equipo1.dogapp.view.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.equipo1.dogapp.R
import com.equipo1.dogapp.databinding.FragmentHomeLoginBinding
import androidx.navigation.fragment.findNavController

class HomeLoginFragment : Fragment() {
    private lateinit var binding: FragmentHomeLoginBinding
    private lateinit var biometricManager: BiometricManager
    private lateinit var biometricPrompt: BiometricPrompt

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeLoginBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Define Vars
        val myImageView = binding.biometricImg
        biometricManager = BiometricManager.from(requireContext())

        myImageView.setOnClickListener { hanldlerBiometric(biometricManager) }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun hanldlerBiometric(biometricManager: BiometricManager) {
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                checkBiometricPermission()
            }
            else -> {
                Toast.makeText(requireContext(), ERROR_MESSAGE_BIOMETRIC , Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun checkBiometricPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.USE_BIOMETRIC), REQUEST_BIOMETRIC_PERMISSION)
        } else {
            showBiometricPrompt()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun showBiometricPrompt() {
        biometricPrompt = BiometricPrompt(requireActivity(), ContextCompat.getMainExecutor(requireContext()), object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(requireContext(), "Error de autenticación: $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(requireContext(), "¡Autenticación exitosa!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_homeLoginFragment_to_appointmentSchedulerFragment)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(requireContext(), "Autenticación fallida", Toast.LENGTH_SHORT).show()
            }
        })
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación biométrica")
            .setSubtitle("Por favor, use su huella dactilar para iniciar sesión")
            .setNegativeButtonText("Cancelar")
            .build()
        biometricPrompt.authenticate(promptInfo)
    }

    companion object {
        private const val REQUEST_BIOMETRIC_PERMISSION = 1001
        private const val ERROR_MESSAGE_BIOMETRIC = "La autenticación biométrica no está disponible"
    }
}