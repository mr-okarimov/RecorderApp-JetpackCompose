package com.ozodbek.recorderapp_jetpackcompose.domain.recordings.models

data class ExtraRecordingMetadataModel(
	val recordingId: Long,
	val isFavourite: Boolean,
	val categoryId: Long? = null,
) 