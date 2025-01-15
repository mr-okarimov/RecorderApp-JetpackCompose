package com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ozodbek.recorderapp_jetpackcompose.R

@Composable
fun SortOptionsSheetContent(
	sortInfo: RecordingsSortInfo,
	onSortTypeChange: (SortOptions) -> Unit,
	onSortOrderChange: (SortOrder) -> Unit,
	modifier: Modifier = Modifier,
	contentPadding: PaddingValues = PaddingValues(12.dp)
) {
	Column(
		modifier = modifier.padding(contentPadding),
		verticalArrangement = Arrangement.spacedBy(4.dp)
	) {
		Text(
			text = stringResource(id = R.string.sort_options_title),
			style = MaterialTheme.typography.titleMedium
		)
		SortOptions.entries.forEach { option ->
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.fillMaxWidth()
					.clip(MaterialTheme.shapes.medium)
					.clickable { onSortTypeChange(option) }
			) {
				RadioButton(
					selected = sortInfo.options == option,
					onClick = { onSortTypeChange(option) },
					colors = RadioButtonDefaults
						.colors(selectedColor = MaterialTheme.colorScheme.secondary)
				)
				Text(
					text = option.strRes,
					style = MaterialTheme.typography.bodyLarge
				)
			}
		}
		HorizontalDivider(
			color = MaterialTheme.colorScheme.outlineVariant,
			modifier = Modifier.padding(vertical = 8.dp)
		)
		Text(
			text = stringResource(id = R.string.sort_order_title),
			style = MaterialTheme.typography.titleMedium
		)
		SortOrder.entries.forEach { order ->
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.fillMaxWidth()
					.clip(MaterialTheme.shapes.medium)
					.clickable { onSortOrderChange(order) }
			) {
				RadioButton(
					selected = sortInfo.order == order,
					onClick = { onSortOrderChange(order) },
					colors = RadioButtonDefaults
						.colors(selectedColor = MaterialTheme.colorScheme.secondary)
				)
				Text(
					text = order.strResource,
					style = MaterialTheme.typography.bodyLarge
				)
			}
		}
	}
}

@PreviewLightDark
@Composable
private fun SortOptionsSheetContentPreview() = RecorderAppTheme {
	Surface {
		SortOptionsSheetContent(
			sortInfo = RecordingsSortInfo(),
			onSortTypeChange = {},
			onSortOrderChange = {},
			modifier = Modifier.fillMaxWidth()
		)
	}
}