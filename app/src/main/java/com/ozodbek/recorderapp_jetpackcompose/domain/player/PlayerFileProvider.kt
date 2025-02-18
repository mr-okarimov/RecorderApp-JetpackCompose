package com.eva.recorderapp.voice_recorder.domain.player

import android.net.Uri
import com.eva.recorderapp.common.Resource
import com.ozodbek.recorderapp_jetpackcompose.domain.player.model.AudioFileModel
import kotlinx.coroutines.flow.Flow

typealias ResourcedDetailedRecordingModel = Resource<AudioFileModel, Exception>

interface PlayerFileProvider {

	fun providesAudioFileUri(audioId: Long): Uri

	fun getAudioFileInfo(id: Long): Flow<ResourcedDetailedRecordingModel>

	suspend fun getPlayerInfoFromAudioId(id: Long): ResourcedDetailedRecordingModel
}