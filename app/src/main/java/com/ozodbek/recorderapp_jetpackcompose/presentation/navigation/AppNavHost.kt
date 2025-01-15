package com.ozodbek.recorderapp_jetpackcompose.presentation.navigation


import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ozodbek.recorderapp_jetpackcompose.presentation.navigation.routes.recordingsRoute
import com.ozodbek.recorderapp_jetpackcompose.presentation.navigation.util.NavRoutes
import com.ozodbek.recorderapp_jetpackcompose.presentation.util.LocalSnackBarProvider

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val snackBarProvider = remember { SnackbarHostState() }

    CompositionLocalProvider(
        value = LocalSnackBarProvider provides snackBarProvider
    ) {
        NavHost(
            navController = navController,
            startDestination = NavRoutes.VoiceRecorder,
            modifier = modifier
        ) {
            // screens
            recordingsRoute(controller = navController)
        }
    }
}


