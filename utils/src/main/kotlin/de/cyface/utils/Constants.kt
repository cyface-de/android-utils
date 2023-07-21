/*
 * Copyright 2018-2023 Cyface GmbH
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

/**
 * Final static constants used by multiple classes.
 *
 * @author Armin Schnabel
 * @version 2.1.0
 * @since 2.5.0
 */
object Constants {
    /**
     * Tag used to identify Logcat messages issued by instances of this package.
     */
    const val TAG = "de.cyface.utils"

    /**
     * The minimum space required for capturing. We don't want to use the space full up as this would
     * slow down the device and could get unusable.
     */
    const val MINIMUM_MEGABYTES_REQUIRED = 100L

    /**
     * The path where files can be stored by the SDK, e.g. image material.
     *
     * **Attention:** This data *is deleted* when the app is uninstalled.
     */
    @Suppress("unused")
    fun externalCyfaceFolderPath(context: Context): String {
        val paths = context.getExternalFilesDirs(null)
        Validate.isTrue(paths.isNotEmpty(), "No external storage available")
        val directory = paths[0]
        val isExternalStorageMounted =
            Environment.getExternalStorageState(directory) == Environment.MEDIA_MOUNTED
        check(isExternalStorageMounted) { "External storage is not mounted" }
        return directory.path
    }
}