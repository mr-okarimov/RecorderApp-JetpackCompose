package com.ozodbek.recorderapp_jetpackcompose.presentation.util

import com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.state.SelectableTrashRecordings
import com.ozodbek.recorderapp_jetpackcompose.domain.categories.models.CategoryColor
import com.ozodbek.recorderapp_jetpackcompose.domain.categories.models.CategoryType
import com.ozodbek.recorderapp_jetpackcompose.domain.categories.models.RecordingCategoryModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlin.random.Random
import java.time.LocalDateTime as JLocalDateTime

object PreviewFakes {

	private val now: LocalDateTime
		get() = JLocalDateTime.now().toKotlinLocalDateTime()


	val PREVIEW_RECORDER_AMPLITUDES = List(100) { Random.nextFloat() }.toImmutableList()








	val FAKE_TRASH_RECORDINGS_EMPTY = persistentListOf<SelectableTrashRecordings>()



	val FAKE_CATEGORY_WITH_COLOR_AND_TYPE = RecordingCategoryModel(
		id = 0L,
		name = "Android",
		categoryType = CategoryType.CATEGORY_SONG,
		categoryColor = CategoryColor.COLOR_BLUE
	)

	val FAKE_CATEGORIES_WITH_ALL_OPTION: ImmutableList<RecordingCategoryModel>
		get() = (List(4) {
			RecordingCategoryModel(
				id = 0L,
				name = "Android",
				categoryType = CategoryType.entries.random(),
				categoryColor = CategoryColor.entries.random()
			)
		} + FAKE_CATEGORY_WITH_COLOR_AND_TYPE + RecordingCategoryModel.ALL_CATEGORY).reversed()
			.toImmutableList()



}