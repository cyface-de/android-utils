/*
 * Copyright 2019-2021 Cyface GmbH
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

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

/**
 * Static utility methods.
 *
 * @author Armin Schnabel
 * @version 1.0.2
 * @since 1.0.0
 */
@SuppressWarnings("unused") // Used by app and camera service
public class Utils {

    /**
     * A utility method used to fix the bug that newly created files are not visible via MTP (usb data transfer).
     *
     * @param context The current Android <code>Context</code> used to access media scanner.
     * @param targetFile The changed file to inform the media scanner about.
     */
    @SuppressWarnings("unused") // Used by app and camera service
    public static void informMediaScanner(@NonNull final Context context, @NonNull final File targetFile) {
        final var mediaScannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        final var fileContentUri = Uri.fromFile(targetFile);
        mediaScannerIntent.setData(fileContentUri);
        context.sendBroadcast(mediaScannerIntent);
    }
}
