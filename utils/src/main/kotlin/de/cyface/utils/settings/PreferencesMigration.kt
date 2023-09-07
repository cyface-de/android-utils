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
package de.cyface.utils.settings

import android.content.Context
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.migrations.SharedPreferencesView
import de.cyface.utils.Settings

/**
 * Factory for the migration which imports preferences from the previously used SharedPreferences.
 *
 * @author Armin Schnabel
 * @since 3.6.0
 * @version 1.0.0
 */
object PreferencesMigrationFactory {

    /**
     * The filename, keys and defaults of the preferences, historically.
     *
     * *Don't change this, this is migration code!*
     */
    private const val PREFERENCES_NAME = "AppPreferences"
    private const val ACCEPTED_TERMS_KEY = "de.cyface.app.accepted_terms"
    private const val ACCEPTED_REPORTING_KEY = "de.cyface.app.accepted_reporting"
    private const val MODALITY_KEY = "de.cyface.app.modality"
    private const val CENTER_MAP_KEY = "de.cyface.app.zoom_to_location"
    private const val SYNCHRONIZATION_KEY = "de.cyface.app.synchronization_enabled"
    private const val SENSOR_FREQUENCY_KEY = "de.cyface.app.sensor_frequency"
    private const val DEFAULT_CENTER_MAP = true
    private const val DEFAULT_SYNCHRONIZATION = true
    private const val DEFAULT_REPORTING = false
    private const val DEFAULT_ACCEPTED_TERMS = 0
    private const val DEFAULT_SENSOR_FREQUENCY = 100

    /**
     * @param context The context to search and access the old SharedPreferences from.
     * @return The migration code which imports preferences from the SharedPreferences if found.
     */
    fun create(context: Context): SharedPreferencesMigration<Settings> {
        return SharedPreferencesMigration(
            context,
            PREFERENCES_NAME,
            migrate = ::migratePreferences
        )
    }

    private fun migratePreferences(
        preferences: SharedPreferencesView,
        settings: Settings
    ): Settings {
        return settings.toBuilder()
            .setVersion(1) // Ensure the migrated values below are used instead of default values.
            .setCenterMap(preferences.getBoolean(CENTER_MAP_KEY, DEFAULT_CENTER_MAP))
            .setUploadEnabled(preferences.getBoolean(SYNCHRONIZATION_KEY, DEFAULT_SYNCHRONIZATION))
            .setSensorFrequency(preferences.getInt(SENSOR_FREQUENCY_KEY, DEFAULT_SENSOR_FREQUENCY))
            .setReportErrors(preferences.getBoolean(ACCEPTED_REPORTING_KEY, DEFAULT_REPORTING))
            // FIXME: this probably crashes now. Add modality "UNSET" or ""
            .setModality(preferences.getString(MODALITY_KEY, null))
            .setAcceptedTerms(preferences.getInt(ACCEPTED_TERMS_KEY, DEFAULT_ACCEPTED_TERMS))
            .build()
    }
}