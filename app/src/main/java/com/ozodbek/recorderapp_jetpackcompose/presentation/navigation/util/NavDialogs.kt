package com.ozodbek.recorderapp_jetpackcompose.presentation.navigation.util

import kotlinx.serialization.Serializable

sealed interface NavDialogs {

	@Serializable
	data object ApplicationInfo : NavDialogs

	@Serializable
	data class RenameRecordingDialog(val recordingId: Long) : NavDialogs
}