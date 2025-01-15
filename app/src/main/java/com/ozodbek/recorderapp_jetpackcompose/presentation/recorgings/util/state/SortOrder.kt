package com.ozodbek.recorderapp_jetpackcompose.presentation.recorgings.util.state

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ozodbek.recorderapp_jetpackcompose.R

enum class SortOrder(
	@StringRes private val res: Int
) {
	ASC(R.string.sort_order_asc),
	DESC(R.string.sort_order_desc);

	val strResource: String
		@Composable
		get() = stringResource(id = res)
}