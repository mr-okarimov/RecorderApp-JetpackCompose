package com.ozodbek.recorderapp_jetpackcompose.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ozodbek.recorderapp_jetpackcompose.data.database.DataBaseConstants
import com.ozodbek.recorderapp_jetpackcompose.domain.categories.models.CategoryColor
import com.ozodbek.recorderapp_jetpackcompose.domain.categories.models.CategoryType
import kotlinx.datetime.LocalDateTime

@Entity(
	tableName = DataBaseConstants.RECORDING_CATEGORY_TABLE,
	indices = [
		Index(value = arrayOf("CATEGORY_NAME"), unique = true)
	]
)
data class RecordingCategoryEntity(

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "CATEGORY_ID")
	val categoryId: Long? = null,

	@ColumnInfo(name = "CATEGORY_NAME")
	val categoryName: String,

	@ColumnInfo(name = "CREATED_AT")
	val createdAt: LocalDateTime,

	@ColumnInfo(name = "COLOR")
	val color: CategoryColor? = null,

	@ColumnInfo(name = "type")
	val type: CategoryType? = null,
)