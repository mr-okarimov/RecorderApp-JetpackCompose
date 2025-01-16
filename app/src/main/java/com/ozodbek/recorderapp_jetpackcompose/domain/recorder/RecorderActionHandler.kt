package com.ozodbek.recorderapp_jetpackcompose.domain.recorder

import com.ozodbek.recorderapp_jetpackcompose.common.Resource
import com.ozodbek.recorderapp_jetpackcompose.domain.recorder.emums.RecorderAction

interface RecorderActionHandler {

	fun onRecorderAction(action: RecorderAction): Resource<Unit, Exception>
}