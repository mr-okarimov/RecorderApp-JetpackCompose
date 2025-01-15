package com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.state

import com.ozodbek.recorderapp_jetpackcompose.domain.recordings.models.RecordedVoiceModel


data class SelectableRecordings(
	val recoding: RecordedVoiceModel,
	val isSelected: Boolean = false,
)

fun List<RecordedVoiceModel>.toSelectableRecordings(): List<SelectableRecordings> =
	map(::SelectableRecordings)