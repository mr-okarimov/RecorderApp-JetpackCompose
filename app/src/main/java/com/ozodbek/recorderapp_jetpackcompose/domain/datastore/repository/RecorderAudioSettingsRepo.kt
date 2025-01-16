package com.ozodbek.recorderapp_jetpackcompose.domain.datastore.repository

import com.ozodbek.recorderapp_jetpackcompose.domain.datastore.enums.RecordQuality
import com.ozodbek.recorderapp_jetpackcompose.domain.datastore.enums.RecordingEncoders
import com.ozodbek.recorderapp_jetpackcompose.domain.datastore.models.RecorderAudioSettings
import kotlinx.coroutines.flow.Flow

interface RecorderAudioSettingsRepo {

	val audioSettingsFlow: Flow<RecorderAudioSettings>

	val audioSettings: RecorderAudioSettings

	suspend fun onEncoderChange(encoder: RecordingEncoders)

	suspend fun onQualityChange(quality: RecordQuality)

	suspend fun onStereoModeChange(mode: Boolean)

	suspend fun onSkipSilencesChange(skipAllowed: Boolean)

	suspend fun onUseBluetoothMicEnabled(isAllowed: Boolean)

	suspend fun onPauseRecorderOnCallEnabled(isEnabled: Boolean)

	suspend fun onAddLocationEnabled(isEnabled: Boolean)
}