package com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ozodbek.recorderapp_jetpackcompose.presentation.composables.ListLoadingAnimation
import com.ozodbek.recorderapp_jetpackcompose.R
import com.ozodbek.recorderapp_jetpackcompose.domain.recordings.models.RecordedVoiceModel
import com.ozodbek.recorderapp_jetpackcompose.domain.recordings.models.TrashRecordingModel
import com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.state.SelectableTrashRecordings
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecordingsInteractiveList(
	isRecordingsLoaded: Boolean,
	recordings: ImmutableList<SelectableTrashRecordings>,
	onItemSelect: (TrashRecordingModel) -> Unit,
	modifier: Modifier = Modifier,
	contentPadding: PaddingValues = PaddingValues(0.dp)
) {
	val isLocalInspectionMode = LocalInspectionMode.current

	val isAnySelected by remember(recordings) {
		derivedStateOf { recordings.any { it.isSelected } }
	}

	val keys: ((Int, SelectableTrashRecordings) -> Any)? = remember {
		if (isLocalInspectionMode) null
		else { _, device -> device.trashRecording.id }
	}

	val contentType: ((Int, SelectableTrashRecordings) -> Any?) = remember {
		{ _, _ -> RecordedVoiceModel::class.simpleName }
	}

	ListLoadingAnimation(
		isLoaded = isRecordingsLoaded,
		items = recordings,
		contentPadding = contentPadding,
		modifier = modifier,
		onDataReady = {
			LazyColumn(
				modifier = Modifier.fillMaxSize(),
				verticalArrangement = Arrangement.spacedBy(6.dp)
			) {
				stickyHeader {
					TrashInfoCard()
				}
				itemsIndexed(
					items = recordings,
					key = keys,
					contentType = contentType
				) { _, record ->
					TrashRecordingsCard(
						trashRecording = record.trashRecording,
						isSelected = record.isSelected,
						isSelectable = isAnySelected,
						onClick = { onItemSelect(record.trashRecording) },
						modifier = Modifier
							.fillMaxWidth()
							.animateItem(),
					)
				}
			}
		},
		onNoItems = {
			Column(
				modifier = Modifier.fillMaxSize(),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Image(
					painter = painterResource(id = R.drawable.ic_bin),
					contentDescription = stringResource(R.string.no_recordings),
					colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.secondary)
				)
				Spacer(modifier = Modifier.height(20.dp))
				Text(
					text = stringResource(id = R.string.no_recordings),
					style = MaterialTheme.typography.titleMedium,
					color = MaterialTheme.colorScheme.tertiary
				)
			}

		},
	)
}

@Composable
private fun TrashInfoCard(modifier: Modifier = Modifier) {
	Card(
		shape = MaterialTheme.shapes.medium,
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.tertiaryContainer,
			contentColor = MaterialTheme.colorScheme.onTertiaryContainer
		),
		modifier = modifier,
	) {
		Row(
			modifier = Modifier.padding(dimensionResource(id = R.dimen.card_padding)),
			horizontalArrangement = Arrangement.spacedBy(8.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Icon(
				painter = painterResource(id = R.drawable.ic_info),
				contentDescription = stringResource(R.string.extras_info)
			)
			Text(
				text = stringResource(R.string.recording_bin_explain_text),
				style = MaterialTheme.typography.labelLarge,
				color = MaterialTheme.colorScheme.tertiary,
			)
		}
	}
}

