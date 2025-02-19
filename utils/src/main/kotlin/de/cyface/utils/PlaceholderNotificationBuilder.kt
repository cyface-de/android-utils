/*
 * Copyright 2017-2025 Cyface GmbH
 *
 * This file is part of the Cyface Utils for Android.
 *
 * The Cyface Utils for Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Cyface Utils for Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Cyface Utils for Android. If not, see <http://www.gnu.org/licenses/>.
 */
package de.cyface.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

/**
 * Provides a placeholder notification for a background service to show until it gets the real notification from the
 * calling code.
 *
 * This workaround is required since the background services in some of our Cyface libraries are required to call
 * `android.app.Service#startForeground(int, Notification)` within the first five seconds of its existence. In
 * this time it first calls `Service#onCreate()` and then `Service#onStartCommand(Intent, int, int)`, but
 * only the later call receives an `Intent` and thus can receive information about the notification to show.
 * However especially on slow devices it seems that `Service#onStartCommand(Intent, int, int)` is not reliably
 * called within the first five seconds, which means we need to call `Service#startForeground(int, Notification)`
 * from `Service#onCreate()`. Since we have can not get information about the notification to show in that method
 * we display this placeholder initially and substitute it by the real notification as soon as possible. On most devices
 * the user should not even notice that step.
 *
 * @author Klemens Muthmann
 * @author Armin Schnabel
 * @version 2.0.4
 * @since 1.1.0
 */
@Suppress("unused") // Part of the API
object PlaceholderNotificationBuilder {
    /**
     * Creates the placeholder notification for the provided `Context`.
     *
     * @param context The Android `Context` to create the notification for.
     * @return The notification to show, while the background service is loading.
     */
    fun build(context: Context): Notification {
        Validate.notNull(context, "No context provided!")
        val channelId = context.getString(R.string.cyface_notification_channel_id)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        Validate.notNull(notificationManager)
        if (notificationManager.getNotificationChannel(channelId) == null) {
            val channel = NotificationChannel(
                channelId,
                context.getString(R.string.notification_text),
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
        builder.setContentTitle(context.getString(R.string.notification_title))
        builder.setSmallIcon(R.drawable.ic_hourglass_empty_black_24dp)
        builder.setContentText(context.getString(R.string.notification_text))
        builder.setOngoing(true)
        builder.setAutoCancel(false)
        return builder.build()
    }
}
