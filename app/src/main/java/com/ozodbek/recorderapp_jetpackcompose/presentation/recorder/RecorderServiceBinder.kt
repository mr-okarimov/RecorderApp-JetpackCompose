package com.ozodbek.recorderapp_jetpackcompose.presentation.recorder

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ozodbek.recorderapp_jetpackcompose.data.service.VoiceRecorderService

@Composable
fun RecorderServiceBinder(
	content: @Composable (isBounded: Boolean, service: VoiceRecorderService?) -> Unit,
) {

	val lifeCycleOwner = LocalLifecycleOwner.current
	val context = LocalContext.current

	var isBounded by remember { mutableStateOf(false) }
	var mService by remember { mutableStateOf<VoiceRecorderService?>(null) }

	val serviceConnection = remember {
		object : ServiceConnection {
			override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
				val binder = service as? VoiceRecorderService.LocalBinder
				mService = binder?.getService()
				isBounded = true
			}

			override fun onServiceDisconnected(name: ComponentName?) {
				isBounded = false
				mService = null
			}
		}
	}

	DisposableEffect(key1 = lifeCycleOwner) {
		val observer = LifecycleEventObserver { _, event ->
			if (event == Lifecycle.Event.ON_START) {
				// bind the service on start
				Intent(context, VoiceRecorderService::class.java).apply {
					context.bindService(this, serviceConnection, Context.BIND_AUTO_CREATE)
				}
			}
			if (event == Lifecycle.Event.ON_STOP) {
				// unbind it on stop
				context.unbindService(serviceConnection)
			}
		}
		// add the lifecyle observer
		lifeCycleOwner.lifecycle.addObserver(observer)

		onDispose {
			// remove the lifecyle observer
			lifeCycleOwner.lifecycle.removeObserver(observer)
		}
	}

	content(isBounded, mService)

}