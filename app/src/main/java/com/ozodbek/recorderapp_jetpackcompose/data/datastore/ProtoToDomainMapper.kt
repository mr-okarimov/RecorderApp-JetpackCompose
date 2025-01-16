package com.ozodbek.recorderapp_jetpackcompose.data.datastore

import com.ozodbek.recorderapp_jetpackcompose.domain.datastore.enums.AudioFileNamingFormat
import com.ozodbek.recorderapp_jetpackcompose.domain.datastore.enums.RecordQuality
import com.ozodbek.recorderapp_jetpackcompose.domain.datastore.enums.RecordingEncoders
import com.ozodbek.recorderapp_jetpackcompose.domain.datastore.models.RecorderAudioSettings
import com.ozodbek.recorderapp_jetpackcompose.domain.datastore.models.RecorderFileSettings

fun RecorderSettingsProto.toDomain(): RecorderAudioSettings = RecorderAudioSettings(
	encoders = encoder.toDomain,
	quality = quality.toDomain,
	pauseRecordingOnCall = pauseDuringCalls,
	enableStereo = isStereoMode,
	skipSilences = skipSilences,
	useBluetoothMic = useBluetoothMic,
	addLocationInfoInRecording = allowLocationInfoIfAvailable,
)

fun FileSettingsProto.toDomain(): RecorderFileSettings = RecorderFileSettings(
	name = prefix,
	format = format.toDomain,
	allowExternalRead = allowExternalRead
)

val RecorderQualityProto.toDomain: RecordQuality
	get() = when (this) {
		RecorderQualityProto.QUALITY_NORMAL -> RecordQuality.NORMAL
		RecorderQualityProto.QUALITY_HIGH -> RecordQuality.HIGH
		RecorderQualityProto.QUALITY_LOW -> RecordQuality.LOW
		RecorderQualityProto.UNRECOGNIZED -> RecordQuality.NORMAL
	}

val NamingFormatProto.toDomain: AudioFileNamingFormat
	get() = when (this) {
		NamingFormatProto.FORMAT_VIA_DATE -> AudioFileNamingFormat.DATE_TIME
		NamingFormatProto.FORMAT_VIA_COUNT -> AudioFileNamingFormat.COUNT
		NamingFormatProto.UNRECOGNIZED -> AudioFileNamingFormat.DATE_TIME
	}

val RecorderEncodingFormatsProto.toDomain: RecordingEncoders
	get() = when (this) {
		RecorderEncodingFormatsProto.ENCODER_AMR_NB -> RecordingEncoders.AMR_NB
		RecorderEncodingFormatsProto.ENCODER_AMR_WB -> RecordingEncoders.AMR_WB
		RecorderEncodingFormatsProto.ENCODER_ACC -> RecordingEncoders.ACC
		RecorderEncodingFormatsProto.ENCODER_OPTUS -> RecordingEncoders.OPTUS
		RecorderEncodingFormatsProto.UNRECOGNIZED -> RecordingEncoders.ACC
	}