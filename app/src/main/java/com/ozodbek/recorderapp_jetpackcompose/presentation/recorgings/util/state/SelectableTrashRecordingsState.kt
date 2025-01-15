package com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.state

import com.ozodbek.recorderapp_jetpackcompose.domain.recordings.models.TrashRecordingModel


data class SelectableTrashRecordings(
	val trashRecording: TrashRecordingModel,
	val isSelected: Boolean = false,
)

fun List<TrashRecordingModel>.toSelectableRecordings(): List<SelectableTrashRecordings> =
	map(::SelectableTrashRecordings)