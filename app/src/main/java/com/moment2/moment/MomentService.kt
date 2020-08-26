package com.moment2.moment

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.IBinder
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import com.moment2.moment.RecordActivity2.Companion.VIDEO_CAPTURE
import com.moment2.moment.R

class MomentService : Service() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val intent4 = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        val pendingIntent3: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent4,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification =
            NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setContentTitle("Moment")
                .setSmallIcon(R.drawable.camera_mic)
                .setContentIntent(pendingIntent)
                .setColor(Color.MAGENTA)
                .addAction(R.mipmap.voice_cam, "RECORD VIDEO", pendingIntent3)
                .build()
        startForeground(1, notification)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}