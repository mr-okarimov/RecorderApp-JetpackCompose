package com.ozodbek.recorderapp_jetpackcompose.presentation.util

import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalTime

typealias RecordingDataPointCallback = () -> List<Pair<LocalTime, Float>>
typealias PlayerGraphData = () -> List<Float>
typealias PlayRation = () -> Float