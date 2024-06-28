package com.rspk.internproject.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rspk.internproject.ui.screenUtils.LocalNavController
import com.rspk.internproject.ui.screens.HomeScreen


@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = LocalNavController.current as NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME.name,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .fillMaxSize()
    ) {
        composable(NavRoutes.HOME.name){
            HomeScreen(
                modifier = modifier
            )
        }
    }

}