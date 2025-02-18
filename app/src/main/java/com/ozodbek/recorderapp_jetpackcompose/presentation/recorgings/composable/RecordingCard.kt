package com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ozodbek.recorderapp_jetpackcompose.R
import com.ozodbek.recorderapp_jetpackcompose.common.LocalTimeFormats.NOTIFICATION_TIMER_TIME_FORMAT
import com.ozodbek.recorderapp_jetpackcompose.common.LocalTimeFormats.RECORDING_RECORD_TIME_FORMAT
import com.ozodbek.recorderapp_jetpackcompose.domain.recordings.models.RecordedVoiceModel
import com.ozodbek.recorderapp_jetpackcompose.ui.theme.RecorderAppTheme
import kotlinx.datetime.format

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecordingCard(
	music: RecordedVoiceModel,
	onItemClick: () -> Unit,
	onItemSelect: () -> Unit,
	modifier: Modifier = Modifier,
	isSelectable: Boolean = false,
	isSelected: Boolean = false,
	shape: Shape = MaterialTheme.shapes.large,
) {
	val context = LocalContext.current
	val isInspectionMode = LocalInspectionMode.current

	val otherAppText = remember(music.owner, context) {
		if (isInspectionMode || music.owner == context.packageName) return@remember null

		music.owner ?: context.getString(R.string.other_app_subtitle)
	}


	val clickModifier = if (isSelectable)
		Modifier.clickable(onClick = onItemSelect, onClickLabel = "Item Selected")
	else Modifier.combinedClickable(
		onClick = onItemClick,
		onLongClick = onItemSelect,
		onClickLabel = "Item Clicked",
		onLongClickLabel = "Item Selected"
	)

	val cardColor = if (!isSelected) CardDefaults.elevatedCardColors()
	else CardDefaults.cardColors()

	ElevatedCard(
		colors = cardColor,
		shape = shape,
		elevation = CardDefaults.elevatedCardElevation(pressedElevation = 4.dp),
		modifier = modifier
			.clip(shape)
			.then(clickModifier),
	) {
		Row(
			horizontalArrangement = Arrangement.spacedBy(16.dp),
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.fillMaxWidth()
				.padding(all = dimensionResource(id = R.dimen.card_padding)),
		) {
			Crossfade(
				targetState = isSelectable,
				animationSpec = tween(durationMillis = 400),
				label = "Radio Button Animation",
				modifier = Modifier.padding(8.dp)
			) { showSelectOption ->
				if (showSelectOption)
					RadioButton(
						selected = isSelected,
						onClick = onItemSelect,
						colors = RadioButtonDefaults
							.colors(selectedColor = MaterialTheme.colorScheme.secondary),
						modifier = Modifier.size(24.dp)

					)
				else
					Image(
						painter = painterResource(id = R.drawable.ic_microphone),
						contentDescription = null,
						colorFilter = ColorFilter
							.tint(color = MaterialTheme.colorScheme.primary),
						modifier = Modifier.size(24.dp)
					)

			}
			Column(
				modifier = Modifier.weight(1f),
				verticalArrangement = Arrangement.spacedBy(4.dp)
			) {
				Text(
					text = music.displayName,
					style = MaterialTheme.typography.titleMedium,
					color = MaterialTheme.colorScheme.primary
				)
				Text(
					text = music.durationAsLocaltime.format(NOTIFICATION_TIMER_TIME_FORMAT),
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onBackground
				)
				AnimatedVisibility(
					visible = isSelectable && otherAppText != null,
					enter = slideInVertically(),
					exit = slideOutVertically()
				) {
					otherAppText?.let { text ->
						Text(
							text = text,
							style = MaterialTheme.typography.labelMedium,
							color = MaterialTheme.colorScheme.tertiary
						)
					}
				}
			}
			Column(
				horizontalAlignment = Alignment.End,
				verticalArrangement = Arrangement.spacedBy(6.dp)
			) {
				AnimatedVisibility(
					visible = music.isFavorite,
					enter = scaleIn() + fadeIn(),
					exit = scaleOut() + fadeOut()
				) {
					Icon(
						painter = painterResource(R.drawable.ic_star_filled),
						contentDescription = stringResource(R.string.menu_option_favourite),
						tint = MaterialTheme.colorScheme.secondary,
						modifier = Modifier.size(20.dp)
					)
				}
				Text(
					text = music.recordedAt.format(RECORDING_RECORD_TIME_FORMAT),
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onBackground
				)
			}
		}
	}
}