package com.rspk.internproject.biometric

import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.rspk.internproject.BiometricAuthentication
import com.rspk.internproject.MainActivity

@RequiresApi(Build.VERSION_CODES.P)
fun authenticateUser(context: FragmentActivity){
    val executor = context.mainExecutor
    val biometricPrompt = BiometricPrompt(
        context,
        executor,
        object: BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationFailed() {
                Toast.makeText(context,"Authentication Failed", Toast.LENGTH_LONG).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                Intent(context, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(it)
                }
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                Toast.makeText(context,"Authentication error : $errString", Toast.LENGTH_SHORT).show()

            }
        }
    )
    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Biometric Authentication")
        .setDescription("Keep your finger on the sensor")
        .setNegativeButtonText("cancel")
        .setAllowedAuthenticators(
            BiometricManager.Authenticators.BIOMETRIC_STRONG)
        .build()

    biometricPrompt.authenticate(promptInfo)
}