package com.ozodbek.recorderapp_jetpackcompose.presentation.recorder.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.ozodbek.recorderapp_jetpackcompose.domain.recorder.RecorderConstants
import com.ozodbek.recorderapp_jetpackcompose.presentation.recorder.util.RecorderDrawGraphUtil.drawAmplitudeGraph
import com.ozodbek.recorderapp_jetpackcompose.presentation.recorder.util.RecorderDrawGraphUtil.drawRecorderTimeline
import com.ozodbek.recorderapp_jetpackcompose.R
import com.ozodbek.recorderapp_jetpackcompose.presentation.util.RecordingDataPointCallback
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalTime

@Composable
fun RecorderAmplitudeGraph(
	dataPointCallback: RecordingDataPointCallback,
	bookMarks: ImmutableList<LocalTime>,
	modifier: Modifier = Modifier,
	plotColor: Color = MaterialTheme.colorScheme.secondary,
	bookMarkColor: Color = MaterialTheme.colorScheme.tertiary,
	timelineColor: Color = MaterialTheme.colorScheme.outline,
	timelineColorVariant: Color = MaterialTheme.colorScheme.outlineVariant,
	timelineTextStyle: TextStyle = MaterialTheme.typography.labelSmall,
	timelineTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
	containerColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
	shape: Shape = MaterialTheme.shapes.small,
	contentPadding: PaddingValues = PaddingValues(
		horizontal = dimensionResource(id = R.dimen.graph_card_padding),
		vertical = dimensionResource(R.dimen.graph_card_padding_other)
	),
) {
	val textMeasurer = rememberTextMeasurer()
	val tag = painterResource(R.drawable.ic_bookmark)

	Surface(
		shape = shape,
		color = containerColor,
		modifier = modifier.aspectRatio(1.6f),
	) {
		Spacer(
			modifier = Modifier
				.padding(paddingValues = contentPadding)
				.defaultMinSize(minHeight = dimensionResource(id = R.dimen.line_graph_min_height))
				.drawWithCache {
					val blockSize = RecorderConstants.RECORDER_AMPLITUDES_BUFFER_SIZE
					val centerYAxis = size.height / 2

					val spikesWidth = size.width / RecorderConstants.RECORDER_AMPLITUDES_BUFFER_SIZE
					val spikesGap = (spikesWidth - 1.5.dp.toPx()).let { amt ->
						if (amt > 0f) amt else 2.dp.toPx()
					}

					onDrawBehind {
						val result = dataPointCallback()

						val amplitudes = result.map { it.second }

						val timeline = result.map { it.first }
						val paddedTimeline = RecorderConstants.padListWithExtra(timeline, blockSize)

						val translateAmount = if (result.size <= blockSize) 0f
						else (blockSize - result.size) * spikesWidth

						val bookmarksToDraw = if (timeline.isNotEmpty())
						// min is required not to draw extra lines and max ensures it doesn't
						// cross the line
							bookMarks.filter { time -> time > timeline.min() && time < timeline.max() }
						// otherwise its empty
						else emptyList()

						translate(left = translateAmount) {
							drawAmplitudeGraph(
								amplitudes = amplitudes,
								spikesGap = spikesGap,
								centerYAxis = centerYAxis,
								spikesWidth = spikesWidth,
								barColor = plotColor,
							)
							drawRecorderTimeline(
								image = tag,
								timeline = paddedTimeline,
								bookMarks = bookmarksToDraw,
								textMeasurer = textMeasurer,
								spikesWidth = spikesWidth,
								strokeWidthThick = 2.dp.toPx(),
								strokeWidthLight = 1.25.dp.toPx(),
								bookMarkColor = bookMarkColor,
								outlineColor = timelineColor,
								outlineVariant = timelineColorVariant,
								textStyle = timelineTextStyle,
								textColor = timelineTextColor
							)
						}
					}
				},
		)
	}
}
