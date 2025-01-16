package com.ozodbek.recorderapp_jetpackcompose.domain.datastore.models

import com.ozodbek.recorderapp_jetpackcompose.domain.datastore.enums.RecordQuality
import com.ozodbek.recorderapp_jetpackcompose.domain.datastore.enums.RecordingEncoders

data class RecorderAudioSettings(
    val encoders: RecordingEncoders = RecordingEncoders.ACC,
    val quality: RecordQuality = RecordQuality.NORMAL,
    val pauseRecordingOnCall: Boolean = false,
    val skipSilences: Boolean = false,
    val enableStereo: Boolean = false,
    val useBluetoothMic: Boolean = false,
    val addLocationInfoInRecording: Boolean = false,
)
