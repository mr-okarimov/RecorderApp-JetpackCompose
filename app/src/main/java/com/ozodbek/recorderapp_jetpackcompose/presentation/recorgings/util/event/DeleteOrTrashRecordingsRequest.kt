package com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.event

import androidx.activity.result.IntentSenderRequest
import com.ozodbek.recorderapp_jetpackcompose.domain.recordings.models.RecordedVoiceModel
import com.ozodbek.recorderapp_jetpackcompose.domain.recordings.models.TrashRecordingModel

sealed interface DeleteOrTrashRecordingsRequest {

	data class OnTrashRequest(
		val recordings: Collection<RecordedVoiceModel>,
		val intentSenderRequest: IntentSenderRequest? = null,
	) : DeleteOrTrashRecordingsRequest

	data class OnDeleteRequest(
		val trashRecordings: Collection<TrashRecordingModel>,
		val intentSenderRequest: IntentSenderRequest? = null,
	) : DeleteOrTrashRecordingsRequest
}