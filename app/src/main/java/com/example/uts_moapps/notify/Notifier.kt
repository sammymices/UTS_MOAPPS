package com.example.uts_moapps.notify

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.uts_moapps.R

object Notifier {
    private const val CHANNEL_ID = "app_inbox_channel"
    private const val CHANNEL_NAME = "App Inbox"
    private const val CHANNEL_DESC = "Notifications for in-app messages"

    private fun ensureChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = CHANNEL_DESC }
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    /** Kirim push notif. Tap akan membuka NotificationsFragment. */
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun notify(context: Context, title: String, message: String, smallIconRes: Int) {
        ensureChannel(context)

        // Deep link: arahkan ke halaman NotificationsFragment
        val pendingIntent: PendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)            // pastikan id nav_graph benar
            .setDestination(R.id.notificationsFragment)  // pastikan id dest fragment benar
            .createPendingIntent()

        val notif = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(smallIconRes)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val id = (title + message).hashCode()
        NotificationManagerCompat.from(context).notify(id,notif)
        }
}