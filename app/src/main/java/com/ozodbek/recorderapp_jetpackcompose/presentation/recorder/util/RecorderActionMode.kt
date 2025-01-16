package com.ozodbek.recorderapp_jetpackcompose.presentation.recorder.util

import com.ozodbek.recorderapp_jetpackcompose.domain.recorder.emums.RecorderState

enum class RecorderActionMode {
	INIT,
	RECORDING,
	PREPARING,
}

val RecorderState.toAction: RecorderActionMode
	get() = when (this) {
		RecorderState.IDLE, RecorderState.COMPLETED, RecorderState.CANCELLED -> RecorderActionMode.INIT
		RecorderState.RECORDING, RecorderState.PAUSED -> RecorderActionMode.RECORDING
		else -> RecorderActionMode.PREPARING
	}

val RecorderState.showTopBarActions: Boolean
	get() = this in arrayOf(RecorderState.IDLE, RecorderState.COMPLETED, RecorderState.CANCELLED)