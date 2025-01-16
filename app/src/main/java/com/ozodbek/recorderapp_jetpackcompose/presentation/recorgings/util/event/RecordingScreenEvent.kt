package com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.event

import com.ozodbek.recorderapp_jetpackcompose.domain.categories.models.RecordingCategoryModel
import com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.state.SortOptions
import com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.state.SortOrder
import com.ozodbek.recorderapp_jetpackcompose.domain.recordings.models.RecordedVoiceModel

sealed interface RecordingScreenEvent {

	data object PopulateRecordings : RecordingScreenEvent

	data class OnRecordingSelectOrUnSelect(val recording: RecordedVoiceModel) : RecordingScreenEvent

	data object OnSelectAllRecordings : RecordingScreenEvent

	data object OnUnSelectAllRecordings : RecordingScreenEvent

	data object OnSelectedItemTrashRequest : RecordingScreenEvent

	data object OnToggleFavourites : RecordingScreenEvent

	data class OnSortOptionChange(val sort: SortOptions) : RecordingScreenEvent

	data class OnSortOrderChange(val order: SortOrder) : RecordingScreenEvent

	data object ShareSelectedRecordings : RecordingScreenEvent

	data class OnPostTrashRequest(val message: String = "") : RecordingScreenEvent

	data class OnCategoryChanged(val categoryModel: RecordingCategoryModel) : RecordingScreenEvent
}