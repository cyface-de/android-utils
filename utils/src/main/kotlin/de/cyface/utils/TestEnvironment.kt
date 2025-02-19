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
package de.cyface.utils

import android.os.Build

/**
 * Contains methods used by multiple Cyface libraries to detect and react to test environments.
 *
 * @author Armin Schnabel
 * @version 1.0.3
 * @since 1.1.0
 */
@Suppress("unused") // Part of the API
object TestEnvironment {
    val isEmulator: Boolean
        /**
         * Checks the environment this code runs and identifies Emulators, see
         * https://stackoverflow.com/questions/2799097/how-can-i-detect-when-an-android-application-is-running-in-the-emulator
         *
         * @return `True` is an emulator was detected
         */
        get() {
            // Avoid crashes in unit tests
            if (Build.FINGERPRINT == null) {
                return false
            }
            return (Build.FINGERPRINT.startsWith("generic")
                    || Build.FINGERPRINT.startsWith("unknown")
                    || Build.MODEL.contains("google_sdk")
                    || Build.MODEL.contains("Emulator")
                    || Build.MODEL.contains("Android SDK built for x86")
                    || Build.MANUFACTURER.contains("Genymotion")
                    || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith(
                "generic"
            ))) || "google_sdk" == Build.PRODUCT
        }
}
