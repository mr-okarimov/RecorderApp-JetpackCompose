package com.ozodbek.recorderapp_jetpackcompose.presentation.util

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.staticCompositionLocalOf

val LocalSnackBarProvider = staticCompositionLocalOf { SnackbarHostState() }