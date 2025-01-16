package com.ozodbek.recorderapp_jetpackcompose

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.ozodbek.recorderapp_jetpackcompose.common.NotificationConstants
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class RecorderApp : Application() {

    private val notificationManager by lazy { getSystemService<NotificationManager>() }


    override fun onCreate() {
        super.onCreate()

        val channel1 = NotificationChannel(
            NotificationConstants.RECORDER_CHANNEL_ID,
            NotificationConstants.RECORDER_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = NotificationConstants.RECORDER_CHANNEL_DESC
            setShowBadge(false)
            lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
        }

        val channel2 = NotificationChannel(
            NotificationConstants.RECORDING_CHANNEL_ID,
            NotificationConstants.RECORDING_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = NotificationConstants.RECORDING_CHANNEL_DESC
            setShowBadge(false)
            lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
        }

        val channel3 = NotificationChannel(
            NotificationConstants.PLAYER_CHANNEL_ID,
            NotificationConstants.PLAYER_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_MIN
        ).apply {
            description = NotificationConstants.PLAYER_CHANNEL_DESC
            lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
        }

        val channels = listOf(channel1, channel2, channel3)

        notificationManager?.createNotificationChannels(channels)
    }
}