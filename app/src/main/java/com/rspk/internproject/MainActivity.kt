package com.rspk.internproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rspk.internproject.ui.screenUtils.LocalValuesProvider
import com.rspk.internproject.ui.theme.InternProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InternProjectTheme {
                LocalValuesProvider()
            }
        }
    }
}