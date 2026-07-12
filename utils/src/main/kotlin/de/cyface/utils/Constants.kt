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
     * The minimum space required for sensor-only capturing. Near-full storage slows down SQLite
     * and prevents sync from completing (SyncManager kills operations on sluggish I/O).
     */
    const val MINIMUM_MEGABYTES_REQUIRED = 500L

    /**
     * The minimum space required when camera is capturing. Camera produces ~5 MB/image at ~1 Hz,
     * so 3 GB provides ~10 minutes of buffer before the device becomes unusable.
     */
    const val MINIMUM_MEGABYTES_REQUIRED_CAMERA = 3_000L
}
