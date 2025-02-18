package com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.composable.RecordingsInteractiveList
import com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.composable.SortOptionsSheetContent
import com.ozodbek.recorderapp_jetpackcompose.R
import com.ozodbek.recorderapp_jetpackcompose.domain.categories.models.RecordingCategoryModel
import com.ozodbek.recorderapp_jetpackcompose.domain.recordings.models.RecordedVoiceModel
import com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.composable.MediaAccessPermissionWrapper
import com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.composable.RecordingsBottomBar
import com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.composable.RecordingsScreenTopBar
import com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.event.RecordingScreenEvent
import com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.state.RecordingsSortInfo
import com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.state.SelectableRecordings
import com.ozodbek.recorderapp_jetpackcompose.presentation.util.LocalSnackBarProvider
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordingsScreen(
	isRecordingsLoaded: Boolean,
	selectedCategory: RecordingCategoryModel,
	sortInfo: RecordingsSortInfo,
	recordings: ImmutableList<SelectableRecordings>,
	categories: ImmutableList<RecordingCategoryModel>,
	onScreenEvent: (RecordingScreenEvent) -> Unit,
	onRecordingSelect: (RecordedVoiceModel) -> Unit,
	modifier: Modifier = Modifier,
	onNavigateToBin: () -> Unit = {},
	onShowRenameDialog: (RecordedVoiceModel?) -> Unit = {},
	onMoveToCategory: (Collection<RecordedVoiceModel>) -> Unit = {},
	onNavigationToCategories: () -> Unit = {},
	navigation: @Composable () -> Unit = {},
) {
	val snackBarProvider = LocalSnackBarProvider.current
	val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

	val isAnySelected by remember(recordings) {
		derivedStateOf { recordings.any(SelectableRecordings::isSelected) }
	}

	val selectedCount by remember(recordings) {
		derivedStateOf {
			recordings.count(SelectableRecordings::isSelected)
		}
	}

	val showRenameOption by remember(selectedCount) {
		derivedStateOf { selectedCount == 1 }
	}

	var showSheet by remember { mutableStateOf(false) }
	val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

	val scope = rememberCoroutineScope()

	if (showSheet) {
		ModalBottomSheet(
			sheetState = sheetState,
			onDismissRequest = { showSheet = false },
			tonalElevation = 2.dp
		) {
			SortOptionsSheetContent(
				sortInfo = sortInfo,
				onSortTypeChange = { onScreenEvent(RecordingScreenEvent.OnSortOptionChange(it)) },
				onSortOrderChange = { onScreenEvent(RecordingScreenEvent.OnSortOrderChange(it)) },
			)
		}
	}

	BackHandler(
		enabled = isAnySelected,
		onBack = { onScreenEvent(RecordingScreenEvent.OnUnSelectAllRecordings) },
	)

	Scaffold(
		topBar = {
			RecordingsScreenTopBar(
				isSelectedMode = isAnySelected,
				selectedCount = selectedCount,
				navigation = navigation,
				onManageCategories = onNavigationToCategories,
				onUnSelectAll = { onScreenEvent(RecordingScreenEvent.OnUnSelectAllRecordings) },
				onSelectAll = { onScreenEvent(RecordingScreenEvent.OnSelectAllRecordings) },
				onSortItems = {
					scope.launch { sheetState.show() }
						.invokeOnCompletion {
							showSheet = true
						}
				},
				onNavigateToBin = onNavigateToBin,
				scrollBehavior = scrollBehavior
			)
		},
		bottomBar = {
			RecordingsBottomBar(
				showRename = showRenameOption,
				isVisible = isAnySelected,
				onShareSelected = { onScreenEvent(RecordingScreenEvent.ShareSelectedRecordings) },
				onItemDelete = { onScreenEvent(RecordingScreenEvent.OnSelectedItemTrashRequest) },
				onStarItem = { onScreenEvent(RecordingScreenEvent.OnToggleFavourites) },
				onRename = {
					// no rename option if option is not available
					if (showRenameOption) {
						val firstSelected = recordings.filter { it.isSelected }
							.map { it.recoding }.firstOrNull()
						// show the rename dialog
						onShowRenameDialog(firstSelected)
					}
				},
				onMoveToCategory = {
					val selectedOnes = recordings.filter { it.isSelected }
						.map { it.recoding }
					onMoveToCategory(selectedOnes)
				},
			)
		},
		snackbarHost = { SnackbarHost(hostState = snackBarProvider) },
		modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
	) { scPadding ->
		MediaAccessPermissionWrapper(
			onLoadRecordings = { onScreenEvent(RecordingScreenEvent.PopulateRecordings) },
			modifier = Modifier.padding(scPadding)
		) {
			RecordingsInteractiveList(
				isRecordingsLoaded = isRecordingsLoaded,
				selectedCategory = selectedCategory,
				recordings = recordings,
				categories = categories,
				onItemClick = onRecordingSelect,
				onCategorySelect = { category ->
					onScreenEvent(RecordingScreenEvent.OnCategoryChanged(category))
				},
				onItemSelect = { record ->
					onScreenEvent(RecordingScreenEvent.OnRecordingSelectOrUnSelect(record))
				},
				contentPadding = PaddingValues(
					horizontal = dimensionResource(id = R.dimen.sc_padding),
					vertical = dimensionResource(id = R.dimen.sc_padding_secondary)
				),
				modifier = Modifier.fillMaxSize(),
			)
		}
	}
}
