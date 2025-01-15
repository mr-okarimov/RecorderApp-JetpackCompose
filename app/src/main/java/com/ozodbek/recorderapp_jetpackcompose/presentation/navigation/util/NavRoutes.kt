package com.ozodbek.recorderapp_jetpackcompose.presentation.navigation.util

import kotlinx.serialization.Serializable


sealed interface NavRoutes {

    @Serializable
    data object VoiceRecorder : NavRoutes
}