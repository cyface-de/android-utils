/*
 * Copyright 2017-2021 Cyface GmbH
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
package de.cyface.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import org.apache.commons.lang3.Validate;

/**
 * Provides a placeholder notification for a background service to show until it gets the real notification from the
 * calling code.
 * <p>
 * This workaround is required since the background services in some of our Cyface libraries are required to call
 * {@code android.app.Service#startForeground(int, Notification)} within the first five seconds of its existence. In
 * this time it first calls {@code Service#onCreate()} and then {@code Service#onStartCommand(Intent, int, int)}, but
 * only the later call receives an {@code Intent} and thus can receive information about the notification to show.
 * However especially on slow devices it seems that {@code Service#onStartCommand(Intent, int, int)} is not reliably
 * called within the first five seconds, which means we need to call {@code Service#startForeground(int, Notification)}
 * from {@code Service#onCreate()}. Since we have can not get information about the notification to show in that method
 * we display this placeholder initially and substitute it by the real notification as soon as possible. On most devices
 * the user should not even notice that step.
 *
 * @author Klemens Muthmann
 * @author Armin Schnabel
 * @version 2.0.2
 * @since 1.1.0
 */
@SuppressWarnings("unused") // Part of the API
public final class PlaceholderNotificationBuilder {
    /**
     * Creates the placeholder notification for the provided {@code Context}.
     *
     * @param context The Android {@code Context} to create the notification for.
     * @return The notification to show, while the background service is loading.
     */
    public static Notification build(@NonNull final Context context) {
        Validate.notNull(context, "No context provided!");
        final var channelId = context.getString(R.string.cyface_notification_channel_id);

        final var notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Validate.notNull(notificationManager);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O
                && notificationManager.getNotificationChannel(channelId) == null) {
            final var channel = new NotificationChannel(channelId, context.getString(R.string.notification_text), NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
        }

        final var builder = new NotificationCompat.Builder(context, channelId);
        builder.setContentTitle(context.getString(R.string.notification_title));
        builder.setSmallIcon(R.drawable.ic_hourglass_empty_black_24dp);
        builder.setContentText(context.getString(R.string.notification_text));
        builder.setOngoing(true);
        builder.setAutoCancel(false);
        return builder.build();
    }
}
