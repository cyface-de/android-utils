/*
 * Copyright 2023 Cyface GmbH
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
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * A helper class for managing and interacting with the SharedPreferences in the application.
 *
 * @author Armin Schnabel
 * @version 1.0.0
 * @since 3.4.0
 * @param context The context to access the SharedPreferences from.
 */
@Suppress("unused") // Part of the API
open class AppPreferences(context: Context) {

    /**
     * The preferences used to store the user's preferred settings.
     */
    @Suppress("MemberVisibilityCanBePrivate") // Required by CustomPreferences extensions
    val preferences: SharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    /**
     * Saves the centerMap boolean value to SharedPreferences.
     *
     * @param centerMap The boolean value indicating whether the map should be centered.
     */
    @Suppress("unused") // Part of the API
    fun saveCenterMap(centerMap: Boolean) {
        preferences.edit {
            putBoolean(PREFERENCES_CENTER_MAP_KEY, centerMap)
            apply()
        }
    }

    /**
     * Retrieves the centerMap boolean value from SharedPreferences.
     *
     * @return The boolean value indicating whether the map should be centered.
     */
    @Suppress("unused") // Part of the API
    fun getCenterMap(): Boolean {
        return preferences.getBoolean(PREFERENCES_CENTER_MAP_KEY, true)
    }

    /**
     * Saves the upload boolean value to SharedPreferences.
     *
     * @param upload The boolean value indicating whether data should be uploaded or synchronized.
     */
    @Suppress("unused") // Part of the API
    fun saveUpload(upload: Boolean) {
        preferences.edit {
            putBoolean(PREFERENCES_SYNCHRONIZATION_KEY, upload)
            apply()
        }
    }

    /**
     * Retrieves the upload boolean value from SharedPreferences.
     *
     * @return The boolean value indicating whether data should be uploaded or synchronized.
     */
    @Suppress("unused") // Part of the API
    fun getUpload(): Boolean {
        return preferences.getBoolean(PREFERENCES_SYNCHRONIZATION_KEY, true)
    }

    /**
     * Saves the sensor frequency to SharedPreferences.
     *
     * @param sensorFrequency The sensor frequency to safe.
     */
    @Suppress("unused") // Part of the API
    fun saveSensorFrequency(sensorFrequency: Int) {
        preferences.edit {
            putInt(PREFERENCES_SENSOR_FREQUENCY_KEY, sensorFrequency)
            apply()
        }
    }

    /**
     * Retrieves the sensor frequency from the SharedPreferences.
     *
     * @return The sensor frequency.
     */
    @Suppress("unused") // Part of the API
    fun getSensorFrequency(): Int {
        return preferences.getInt(PREFERENCES_SENSOR_FREQUENCY_KEY, DEFAULT_SENSOR_FREQUENCY)
    }

    /**
     * Saves if errors show be reported to SharedPreferences.
     *
     * @param reportingAccepted `True` is errors should be reported.
     */
    @Suppress("unused") // Part of the API
    fun saveReportingAccepted(reportingAccepted: Boolean) {
        preferences.edit {
            putBoolean(ACCEPTED_REPORTING_KEY, reportingAccepted)
            apply()
        }
    }

    /**
     * Retrieves if errors should be reported from the SharedPreferences.
     *
     * @return `True` if errors show be reported.
     */
    @Suppress("unused") // Part of the API
    fun getReportingAccepted(): Boolean {
        return preferences.getBoolean(ACCEPTED_REPORTING_KEY, false)
    }

    /**
     * see [PREFERENCES_MODALITY_KEY]
     */
    @Suppress("unused") // Part of the API
    fun saveModality(modality: String) {
        preferences.edit {
            putString(PREFERENCES_MODALITY_KEY, modality)
            apply()
        }
    }

    /**
     * see [PREFERENCES_MODALITY_KEY]
     */
    @Suppress("unused") // Part of the API
    fun getModality(): String? {
        return preferences.getString(PREFERENCES_MODALITY_KEY, null)
    }

    /**
     * see [ACCEPTED_TERMS_KEY]
     */
    @Suppress("unused") // Part of the API
    fun saveAcceptedTerms(acceptedTerms: Int) {
        preferences.edit {
            putInt(ACCEPTED_TERMS_KEY, acceptedTerms)
            apply()
        }
    }

    /**
     * see [ACCEPTED_TERMS_KEY]
     */
    @Suppress("unused") // Part of the API
    fun getAcceptedTerms(): Int {
        return preferences.getInt(ACCEPTED_TERMS_KEY, 0)
    }

    companion object {
        /**
         * Default value in Hz when there is no user preference stored yet.
         */
        const val DEFAULT_SENSOR_FREQUENCY = 100

        /*
         * Preferences keys
         *
         * *Don't change the values, as this resets the user's preferences!*
         */

        /**
         * The prefix used in the shared preferences, historically.
         *
         * *Don't change this, as this resets the user's preferences!*
         */
        private const val KEY_PACKAGE = "de.cyface.app"

        /**
         * Identifies the accepted terms version in the preferences.
         */
        const val ACCEPTED_TERMS_KEY = "$KEY_PACKAGE.accepted_terms"

        /**
         * Identifies if the user opted-in to error reporting in the preferences.
         */
        const val ACCEPTED_REPORTING_KEY = "$KEY_PACKAGE.accepted_reporting"

        const val PREFERENCES_MODALITY_KEY = "$KEY_PACKAGE.modality"

        const val PREFERENCES_CENTER_MAP_KEY = "$KEY_PACKAGE.zoom_to_location"
        const val PREFERENCES_SYNCHRONIZATION_KEY = "$KEY_PACKAGE.synchronization_enabled"

        /**
         * The key for the `SharedPreferences`. This entry contains the preferred sensor frequency.
         */
        const val PREFERENCES_SENSOR_FREQUENCY_KEY = "$KEY_PACKAGE.sensor_frequency"
    }
}