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
     * Enum representing the type of storage being used.
     */
    enum class StorageType {
        /**
         * Primary external storage (e.g., app-specific directory on external storage).
         */
        EXTERNAL,

        /**
         * Internal storage (app-specific directory on internal storage).
         */
        INTERNAL
    }

    /**
     * Checks if primary external storage is available and writable.
     *
     * @param context The Android context.
     * @return The external storage directory if available and writable, null otherwise.
     */
    private fun getPrimaryExternalStorageIfAvailable(context: Context): File? {
        val externalDirs = context.getExternalFilesDirs(null)
        if (externalDirs.isNotEmpty() && externalDirs[0] != null) {
            val directory = externalDirs[0]
            // Removed canWrite() check because it might be unreliable on API 29+
            // and app-specific external dirs are always writable when mounted.
            val isExternalStorageMounted =
                Environment.getExternalStorageState(directory) == Environment.MEDIA_MOUNTED

            if (isExternalStorageMounted) {
                return directory
            }
        }
        return null
    }

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
    // Part of the API
    fun getStorageDirectoryWithFallback(context: Context): File {
        val externalStorage = getPrimaryExternalStorageIfAvailable(context)
        if (externalStorage != null) {
            Log.d(TAG, "Using primary external storage: ${externalStorage.absolutePath}")
            return externalStorage
        }

        // Fall back to internal storage
        Log.d(TAG, "Falling back to internal storage: ${context.filesDir.absolutePath}")
        return context.filesDir
    }

    /**
     * Gets the path to the storage directory for the app, trying external storage first and falling
     * back to internal storage if external is not available or not writable.
     *
     * This is a convenience method that returns the path as a String.
     *
     * @param context The Android context.
     * @return The storage directory path (external if available and writable, otherwise internal).
     */
    @Suppress("unused") // Part of the API
    fun getStoragePathWithFallback(context: Context): String {
        return getStorageDirectoryWithFallback(context).path
    }

    /**
     * Determines which type of storage is currently being used by the app.
     *
     * This method checks the same conditions as getStorageDirectoryWithFallback() and returns
     * whether external or internal storage would be selected.
     *
     * @param context The Android context.
     * @return StorageType.EXTERNAL if primary external storage is available and writable,
     *         StorageType.INTERNAL otherwise.
     */
    @Suppress("unused") // Part of the API
    fun getStorageType(context: Context): StorageType {
        return if (getPrimaryExternalStorageIfAvailable(context) != null) {
            StorageType.EXTERNAL
        } else {
            StorageType.INTERNAL
        }
    }
}
