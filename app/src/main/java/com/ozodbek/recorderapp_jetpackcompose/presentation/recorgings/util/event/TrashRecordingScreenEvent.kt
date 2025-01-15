package com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.event

import com.ozodbek.recorderapp_jetpackcompose.domain.recordings.models.TrashRecordingModel


interface TrashRecordingScreenEvent {

	data object PopulateTrashRecordings : TrashRecordingScreenEvent

	data class OnRecordingSelectOrUnSelect(
		val recording: TrashRecordingModel
	) : TrashRecordingScreenEvent

	data object OnSelectTrashRecording : TrashRecordingScreenEvent

	data object OnUnSelectTrashRecording : TrashRecordingScreenEvent

	data object OnSelectItemRestore : TrashRecordingScreenEvent

	data object OnSelectItemDeleteForeEver : TrashRecordingScreenEvent

	data class OnPostDeleteRequest(val message: String = "") : TrashRecordingScreenEvent
}