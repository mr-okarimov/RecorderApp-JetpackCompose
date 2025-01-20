package com.ozodbek.recorderapp_jetpackcompose.data.player

import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.ozodbek.recorderapp_jetpackcompose.domain.player.model.AudioFileModel

internal fun AudioFileModel.toMediaItem(): MediaItem {
	// adding much of the metadata available from the given AudioFileModel
	val metaData = MediaMetadata.Builder()
		.setTitle(title)
		.setDisplayTitle(displayName)
		.setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
		.setRecordingDay(lastModified.dayOfMonth)
		.setRecordingMonth(lastModified.monthNumber)
		.setRecordingYear(lastModified.year)
		.setIsBrowsable(false)
		.build()

	val fileUri = fileUri.toUri()
	val mediaId = "$id"
	return MediaItem.Builder()
		.setUri(fileUri)
		.setMediaId(mediaId)
		.setMimeType(mimeType)
		.setMediaMetadata(metaData)
		.build()
}