package com.ozodbek.recorderapp_jetpackcompose.domain.datastore.models

import com.ozodbek.recorderapp_jetpackcompose.domain.datastore.enums.AudioFileNamingFormat

data class RecorderFileSettings(
	val name: String = "Voice",
	val format: AudioFileNamingFormat = AudioFileNamingFormat.DATE_TIME,
	val allowExternalRead: Boolean = false,
)
