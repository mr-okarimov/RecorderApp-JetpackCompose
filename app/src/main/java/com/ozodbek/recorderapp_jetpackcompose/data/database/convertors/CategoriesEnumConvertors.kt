package com.ozodbek.recorderapp_jetpackcompose.data.database.convertors

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.ozodbek.recorderapp_jetpackcompose.domain.categories.models.CategoryColor
import com.ozodbek.recorderapp_jetpackcompose.domain.categories.models.CategoryType

@ProvidedTypeConverter
class CategoriesEnumConvertors {

	@TypeConverter
	fun fromCategoryType(type: CategoryType): String = type.name

	@TypeConverter
	fun fromCategoryColor(color: CategoryColor) = color.name

	@TypeConverter
	fun toCategoryType(name: String): CategoryType =
		try {
			CategoryType.valueOf(name)
		} catch (e: IllegalArgumentException) {
			CategoryType.CATEGORY_NONE
		}

	@TypeConverter
	fun toCategoryColor(name: String): CategoryColor =
		try {
			CategoryColor.valueOf(name)
		} catch (e: IllegalArgumentException) {
			CategoryColor.COLOR_UNKNOWN
		}
}