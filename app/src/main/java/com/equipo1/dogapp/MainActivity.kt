package com.equipo1.dogapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt


class MainActivity : AppCompatActivity() {
    private lateinit var biometricPrompt: BiometricPrompt

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home_login)

        // Define Vars
        val myImageView = findViewById<ImageView>(R.id.biometricImg)
        val biometricManager = BiometricManager.from(this)

        myImageView.setOnClickListener {this.hanldlerBiometric(biometricManager)}
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun hanldlerBiometric(biometricManager: BiometricManager) {
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                checkBiometricPermission()
            }
            else -> {
                Toast.makeText(this,ERROR_MESSAGE_BIOMETRIC , Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun checkBiometricPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.USE_BIOMETRIC), REQUEST_BIOMETRIC_PERMISSION)
        } else {
            showBiometricPrompt()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun showBiometricPrompt() {
        biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this), object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(applicationContext, "Error de autenticación: $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(applicationContext, "¡Autenticación exitosa!", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext, "Autenticación fallida", Toast.LENGTH_SHORT).show()
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