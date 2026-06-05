package com.example.theftiness_app.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.theftiness_app.MainActivity

class BiometricActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verificarBiometria()
    }

    private fun verificarBiometria() {

        val biometricManager = BiometricManager.from(this)

        when (
            biometricManager.canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
            )
        ) {

            BiometricManager.BIOMETRIC_SUCCESS -> {
                mostrarPrompt()
            }

            else -> {
                Toast.makeText(
                    this,
                    "El dispositivo no tiene biometría disponible",
                    Toast.LENGTH_LONG
                ).show()

                finish()
            }
        }
    }

    private fun mostrarPrompt() {

        val executor = ContextCompat.getMainExecutor(this)

        val biometricPrompt =
            BiometricPrompt(
                this,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {

                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult
                    ) {
                        super.onAuthenticationSucceeded(result)

                        startActivity(
                            Intent(
                                this@BiometricActivity,
                                MainActivity::class.java
                            )
                        )

                        finish()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()

                        Toast.makeText(
                            this@BiometricActivity,
                            "Huella incorrecta",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onAuthenticationError(
                        errorCode: Int,
                        errString: CharSequence
                    ) {
                        super.onAuthenticationError(
                            errorCode,
                            errString
                        )

                        Toast.makeText(
                            this@BiometricActivity,
                            errString,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )

        val promptInfo =
            BiometricPrompt.PromptInfo.Builder()
                .setTitle("TheFitness App")
                .setSubtitle("Autenticación requerida")
                .setNegativeButtonText("Cancelar")
                .build()

        biometricPrompt.authenticate(promptInfo)
    }
}