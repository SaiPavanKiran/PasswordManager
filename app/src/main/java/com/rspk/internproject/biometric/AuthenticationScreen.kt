package com.rspk.internproject.biometric

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AuthenticationScreen() {
    var supportsBiometric by remember { mutableStateOf(false) }
    val context = LocalContext.current as FragmentActivity
    val biometricManager = BiometricManager.from(context)
    supportsBiometric = when (biometricManager.canAuthenticate(
        BiometricManager.Authenticators.BIOMETRIC_STRONG
    )) {
        BiometricManager.BIOMETRIC_SUCCESS -> true
        else -> {
            Toast.makeText(context, "Biometric authentication unavailable", Toast.LENGTH_LONG)
                .show()
            false
        }
    }
    LaunchedEffect(key1 = Unit) {
        authenticateUser(context)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BiometricButton(
            state = supportsBiometric,
            onClick = { authenticateUser(context) },
            text = "Authenticate"
        )
    }
}

@Composable
fun BiometricButton(
    state:Boolean,
    onClick: () -> Unit ,
    text: String
){
    Button(
        enabled = state,
        onClick = onClick,
        modifier = Modifier.padding(8.dp)) {
        Text(text = text)
    }

}