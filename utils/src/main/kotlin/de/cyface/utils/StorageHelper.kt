/*
 * Copyright 2025 Cyface GmbH
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
import android.os.Environment
import android.util.Log
import de.cyface.utils.Constants.TAG
import java.io.File

/**
 * Helper class for storage-related operations.
 *
 * @author Armin Schnabel
 * @version 1.0.0
 * @since 4.3.0
 */
object StorageHelper {
    /**
     * Gets the storage directory for the app, trying primary external storage first and falling
     * back to internal storage if external is not available or not writable.
     *
     * This method only uses the primary external storage (the first entry in getExternalFilesDirs),
     * which is typically the device's main app-specific external storage directory. It does not use
     * secondary external storage like removable SD cards or USB drives, which appear at higher
     * indices and could be unreliable (e.g., user might unplug them).
     *
     * @param context The Android context.
     * @return The storage directory (primary external if available and writable, otherwise internal).
     */
    fun getStorageDirectoryWithFallback(context: Context): File {
        // Try primary external storage only (first entry)
        val externalDirs = context.getExternalFilesDirs(null)
        if (externalDirs.isNotEmpty() && externalDirs[0] != null) {
            val directory = externalDirs[0]
            val isExternalStorageMounted =
                Environment.getExternalStorageState(directory) == Environment.MEDIA_MOUNTED
            if (isExternalStorageMounted && directory.canWrite()) {
                Log.d(TAG, "Using primary external storage: ${directory.absolutePath}")
                return directory
            }
        }

        // Fall back to internal storage
        Log.d(TAG, "Falling back to internal storage: ${context.filesDir.absolutePath}")
        return context.filesDir
    }
}
