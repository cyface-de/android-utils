/*
 * Copyright 2019-2025 Cyface GmbH
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

import android.content.Context
import android.media.MediaScannerConnection
import java.io.File

/**
 * Static utility methods.
 *
 * @author Armin Schnabel
 * @version 1.0.3
 * @since 1.0.0
 */
@Suppress("unused") // Used by app and camera service
object Utils {
    /**
     * A utility method used to fix the bug that newly created files are not visible via MTP (usb data transfer).
     *
     * @param context The current Android `Context` used to access media scanner.
     * @param targetFile The changed file to inform the media scanner about.
     */
    @Suppress("unused") // Used by app and camera service
    fun informMediaScanner(context: Context, targetFile: File) {
        MediaScannerConnection.scanFile(context, arrayOf(targetFile.absolutePath), null) { _, _ ->
            // File is automatically updated in the MediaStore
        }
    }
}
