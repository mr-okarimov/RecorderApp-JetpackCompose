package com.ozodbek.recorderapp_jetpackcompose.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ozodbek.recorderapp_jetpackcompose.data.database.DataBaseConstants
import com.ozodbek.recorderapp_jetpackcompose.data.database.entity.RecordingCategoryEntity

@Entity(
	tableName = DataBaseConstants.RECORDING_METADATA_TABLE,
	foreignKeys = [
		ForeignKey(
			entity = RecordingCategoryEntity::class,
			childColumns = ["CATEGORY_ID"],
			parentColumns = ["CATEGORY_ID"],
			onUpdate = ForeignKey.CASCADE,
			onDelete = ForeignKey.SET_NULL,
		),
	],
	indices = [
		Index("CATEGORY_ID")
	],
)
data class RecordingsMetaDataEntity(

	@PrimaryKey(autoGenerate = false)
	@ColumnInfo(name = "RECORDING_ID")
	val recordingId: Long,

	@ColumnInfo(name = "IS_FAVOURITE")
	val isFavourite: Boolean = false,

	@ColumnInfo(name = "CATEGORY_ID")
	val categoryId: Long? = null,
)