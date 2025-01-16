package com.ozodbek.recorderapp_jetpackcompose.domain.recorder.models

data class RecordEncoderAndFormat(
	val encoder: Int,
	val outputFormat: Int,
	val mimeType: String,
)