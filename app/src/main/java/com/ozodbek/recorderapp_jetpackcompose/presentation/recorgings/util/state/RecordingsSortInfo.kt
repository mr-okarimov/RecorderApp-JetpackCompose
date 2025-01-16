package com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.state

data class RecordingsSortInfo(
	val options: SortOptions = SortOptions.NAME,
	val order: SortOrder = SortOrder.ASC
)