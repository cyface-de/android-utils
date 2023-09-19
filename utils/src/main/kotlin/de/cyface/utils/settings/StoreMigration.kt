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

import android.util.Log
import androidx.datastore.core.DataMigration
import de.cyface.utils.Constants.TAG
import de.cyface.utils.Settings

/**
 * Migration which ensures DataStore files from all versions are compatible.
 *
 * @author Armin Schnabel
 * @version 1.0.0
 * @since 3.6.0
 */
class StoreMigration : DataMigration<Settings> {

    /**
     * The current version of the datastore schema. Increase this when migration is necessary,
     * e.g. when you add a new field, to ensure a correct default value is set instead of the
     * Protobuf default value for that data type like "" or 0, 0.0, etc.
     */
    private val currentVersion = 1

    override suspend fun shouldMigrate(currentData: Settings): Boolean {
        // When no previous datastore file exists, the version starts at 0, i.e. this ensures
        // that the correct default values are set instead of the Protobuf default value for that
        // data type like "" or 0, 0.0, etc.
        return currentData.version < currentVersion
    }

    override suspend fun migrate(currentData: Settings): Settings {
        val currentVersion = currentData.version
        val targetVersion = currentVersion + 1
        val logTemplate =
            "Migrating ${Settings::class.java.name} from $currentVersion to $targetVersion"
        Log.i(TAG, String.format(logTemplate, currentVersion, targetVersion))

        val builder = currentData.toBuilder()
        when (currentVersion) {
            0 -> {
                @Suppress("UsePropertyAccessSyntax")
                builder
                    .setVersion(targetVersion)
                    .setCenterMap(DEFAULT_CENTER_MAP)
                    .setUploadEnabled(DEFAULT_SYNCHRONIZATION)
                    .setSensorFrequency(DEFAULT_SENSOR_FREQUENCY)
                    .setReportErrors(DEFAULT_REPORTING)
                    .setModality(DEFAULT_MODALITY)
                    .setAcceptedTerms(DEFAULT_ACCEPTED_TERMS)
            }

            else -> {
                throw MigrationException("No migration code for version ${currentVersion}.")
            }
        }
        return builder.build()
    }

    override suspend fun cleanUp() {
        // Is called when migration was successful
        // Currently, there is no cleanup to do, yet
    }

    companion object {
        /**
         * The boolean value indicating whether the map should be centered.
         */
        private const val DEFAULT_CENTER_MAP = true

        /**
         * The boolean value indicating whether data should be uploaded or synchronized.
         */
        private const val DEFAULT_SYNCHRONIZATION = true

        /**
         * `true` if errors should be reported.
         */
        private const val DEFAULT_REPORTING = false

        /**
         * The API version of the terms accepted by the user, e.g. 5.
         */
        private const val DEFAULT_ACCEPTED_TERMS = 0

        /**
         * The maximum frequency for each sensor type to collect data with.
         */
        private const val DEFAULT_SENSOR_FREQUENCY = 100

        /**
         * When no modality is selected yet or when the previous modality is now unsupported.
         */
        private const val DEFAULT_MODALITY = "UNKNOWN"
    }
}