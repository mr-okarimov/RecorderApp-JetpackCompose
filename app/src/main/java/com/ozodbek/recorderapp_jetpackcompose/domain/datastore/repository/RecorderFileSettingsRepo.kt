package com.ozodbek.recorderapp_jetpackcompose.domain.datastore.repository

import com.ozodbek.recorderapp_jetpackcompose.domain.datastore.enums.AudioFileNamingFormat
import com.ozodbek.recorderapp_jetpackcompose.domain.datastore.models.RecorderFileSettings
import kotlinx.coroutines.flow.Flow

interface RecorderFileSettingsRepo {

	val fileSettingsFlow: Flow<RecorderFileSettings>

	val fileSettings: RecorderFileSettings

	suspend fun onFilePrefixChange(prefix: String)

	suspend fun onFileNameFormatChange(format: AudioFileNamingFormat)

	suspend fun onAllowExternalFileRead(isAllowed: Boolean)
}