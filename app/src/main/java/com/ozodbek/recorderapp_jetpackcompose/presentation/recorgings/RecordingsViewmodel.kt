package com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings

import android.app.RecoverableSecurityException
import android.os.Build
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.viewModelScope
import com.eva.recorderapp.voice_recorder.presentation.recordings.util.state.sortedResults
import com.ozodbek.recorderapp_jetpackcompose.common.AppViewModel
import com.ozodbek.recorderapp_jetpackcompose.common.UIEvents
import com.ozodbek.recorderapp_jetpackcompose.domain.categories.models.RecordingCategoryModel
import com.ozodbek.recorderapp_jetpackcompose.domain.recordings.models.RecordedVoiceModel
import com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.event.DeleteOrTrashRecordingsRequest
import com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.event.RecordingScreenEvent
import com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.state.RecordingsSortInfo
import com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.state.SelectableRecordings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordingsViewmodel @Inject constructor(

) : AppViewModel() {

	private val _sortInfo = MutableStateFlow(RecordingsSortInfo())
	val sortInfo = _sortInfo.asStateFlow()

	private val _selectedCategory = MutableStateFlow(RecordingCategoryModel.ALL_CATEGORY)
	val selectedCategory = _selectedCategory.asStateFlow()

	private val _recordings = MutableStateFlow(emptyList<SelectableRecordings>())
	val recordings = combine(_recordings, _sortInfo, transform = ::sortedResults)
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5000),
			initialValue = persistentListOf()
		)

	private val _categories = MutableStateFlow(emptyList<RecordingCategoryModel>())
	val categories = _categories
		.map { categories -> categories.toImmutableList() }
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(3000),
			initialValue = persistentListOf()
		)

	private val _isRecordingsLoaded = MutableStateFlow(false)
	private val _isCategoriesLoaded = MutableStateFlow(false)

	val isLoaded = combine(_isRecordingsLoaded, _isCategoriesLoaded) { isRecordings, isCategories ->
		isRecordings && isCategories
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.Eagerly,
		initialValue = false
	)

	private val selectedRecordings: List<RecordedVoiceModel>
		get() = _recordings.value.filter(SelectableRecordings::isSelected)
			.map(SelectableRecordings::recoding)

	private val _trashRecordingEvent = MutableSharedFlow<DeleteOrTrashRecordingsRequest>()
	val trashRequestEvent: SharedFlow<DeleteOrTrashRecordingsRequest>
		get() = _trashRecordingEvent.asSharedFlow()

	private val _uiEvents = MutableSharedFlow<UIEvents>()
	override val uiEvent: SharedFlow<UIEvents>
		get() = _uiEvents.asSharedFlow()

	private var _prepareRecordingsJob: Job? = null

	private fun populateRecordings() {
		// recordings are already loaded no need to again add a collector
		if (isLoaded.value) return
		// prepare the categories
		populateRecordingCategories()
		// populate the records according to requirements
		_selectedCategory.onEach(::populateRecords).launchIn(viewModelScope)
	}

	private fun populateRecords(categoryModel: RecordingCategoryModel?) {
		// cancels the job this the previous collector get cancelled
		_prepareRecordingsJob?.cancel()
		// set it to the new job

	}

	private fun populateRecordingCategories() {
		// load all categories with count

	}


	fun onScreenEvent(event: RecordingScreenEvent) {
		when (event) {
			is RecordingScreenEvent.OnRecordingSelectOrUnSelect -> toggleRecordingSelection(event.recording)
			RecordingScreenEvent.OnSelectAllRecordings -> onSelectOrUnSelectAllRecordings(true)
			RecordingScreenEvent.OnUnSelectAllRecordings -> onSelectOrUnSelectAllRecordings(false)
			RecordingScreenEvent.OnSelectedItemTrashRequest -> onTrashSelectedRecordings()
			is RecordingScreenEvent.OnSortOptionChange -> _sortInfo.update { it.copy(options = event.sort) }
			is RecordingScreenEvent.OnSortOrderChange -> _sortInfo.update { it.copy(order = event.order) }
			RecordingScreenEvent.ShareSelectedRecordings -> {}
			RecordingScreenEvent.PopulateRecordings -> populateRecordings()
			is RecordingScreenEvent.OnCategoryChanged -> _selectedCategory.update { event.categoryModel }
			RecordingScreenEvent.OnToggleFavourites -> onToggleFavourites()
			is RecordingScreenEvent.OnPostTrashRequest -> viewModelScope.launch {
				_uiEvents.emit(UIEvents.ShowToast(event.message))
			}
		}
	}

	private fun onSelectOrUnSelectAllRecordings(select: Boolean = false) {
		_recordings.update { recordings ->
			recordings.map { record -> record.copy(isSelected = select) }
		}
	}

	private fun onTrashSelectedRecordings() {
		// request for trash item

	}


	private fun onToggleFavourites() = viewModelScope.launch {
		val isAllSelectedFavourite = selectedRecordings.all { it.isFavorite }
	}


	private fun handleSecurityExceptionToTrash(
		error: SecurityException,
		recordingsToTrash: Collection<RecordedVoiceModel>? = null,
	) {
		if (recordingsToTrash == null) return

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

			val request = DeleteOrTrashRecordingsRequest.OnTrashRequest(recordingsToTrash)
			viewModelScope.launch {
				_trashRecordingEvent.emit(request)
			}

		} else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
			// TODO: Check the workflow for android 10
			(error as? RecoverableSecurityException)?.let { exp ->
				val pendingIntent = exp.userAction.actionIntent
				val request = IntentSenderRequest.Builder(pendingIntent).build()

				val trashEvent = DeleteOrTrashRecordingsRequest
					.OnTrashRequest(recordings = recordingsToTrash, intentSenderRequest = request)

				viewModelScope.launch {
					_trashRecordingEvent.emit(trashEvent)
				}
			}
		}
	}

	private fun toggleRecordingSelection(recording: RecordedVoiceModel) {
		_recordings.update { recordings ->
			recordings.map { record ->
				if (record.recoding == recording)
					record.copy(isSelected = !record.isSelected)
				else record
			}
		}
	}


}