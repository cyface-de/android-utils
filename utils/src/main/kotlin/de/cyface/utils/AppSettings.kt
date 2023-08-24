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
import androidx.datastore.core.DataStore
import androidx.datastore.core.MultiProcessDataStoreFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File

/**
 * Common settings used by all our UIs.
 *
 * Attention:
 * - Never mix SingleProcessDataStore with MultiProcessDataStore for the same file.
 * - We use MultiProcessDataStore, i.e. the preferences can be accessed from multiple processes.
 * - Only create one instance of `DataStore` per file in the same process.
 * - We use ProtoBuf to ensure type safety. Rebuild after changing the .proto file.
 *
 * @author Armin Schnabel
 * @version 2.0.0
 * @since 3.4.0
 * @param context The context to access the preferences from.
 */
@Suppress("unused") // Part of the API
class AppSettings(private val context: Context) {

    /**
     * The data store with multi-process support.
     */
    private val dataStore: DataStore<Settings> = MultiProcessDataStoreFactory.create(
        serializer = SettingsSerializer,
        produceFile = {
            File("${context.cacheDir.path}/app.settings_pb")
        }
    )

    /**
     * Saves the centerMap boolean value.
     *
     * @param value The boolean value indicating whether the map should be centered.
     */
    @Suppress("unused") // Part of the API
    suspend fun setCenterMap(value: Boolean) {
        dataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setCenterMap(value)
                .build()
        }
    }

    /**
     * @return The boolean value indicating whether the map should be centered.
     */
    val centerMapFlow: Flow<Boolean> = dataStore.data
        .map { settings ->
            settings.centerMap
        }

    /**
     * Saves the upload boolean value.
     *
     * @param value The boolean value indicating whether data should be uploaded or synchronized.
     */
    @Suppress("unused") // Part of the API
    suspend fun setUploadEnabled(value: Boolean) {
        dataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setUploadEnabled(value)
                .build()
        }
    }

    /**
     * @return The boolean value indicating whether data should be uploaded or synchronized.
     */
    @Suppress("unused") // Part of the API
    val uploadEnabledFlow: Flow<Boolean> = dataStore.data
        .map { settings ->
            settings.uploadEnabled
        }

    /**
     * Saves the sensor frequency.
     *
     * @param value The sensor frequency to safe.
     */
    @Suppress("unused") // Part of the API
    suspend fun setSensorFrequency(value: Int) {
        dataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setSensorFrequency(value)
                .build()
        }
    }

    /**
     * @return The maximum frequency with which the IMU sensor should collect data, e.g. 100 Hz.
     */
    @Suppress("unused") // Part of the API
    val sensorFrequencyFlow: Flow<Int> = dataStore.data
        .map { settings ->
            settings.sensorFrequency
        }

    /**
     * Saves if errors show be reported.
     *
     * @param value `True` is errors should be reported.
     */
    @Suppress("unused") // Part of the API
    suspend fun setReportErrors(value: Boolean) {
        dataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setReportErrors(value)
                .build()
        }
    }

    /**
     * @return `True` if errors show be reported.
     */
    @Suppress("unused") // Part of the API
    val reportErrorsFlow: Flow<Boolean> = dataStore.data
        .map { settings ->
            settings.reportErrors
        }

    /**
     * Saves the currently selected modality.
     *
     * @param value The modality.
     */
    @Suppress("unused") // Part of the API
    suspend fun setModality(value: String) {
        dataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setModality(value)
                .build()
        }
    }

    /**
     * @return The currently selected modality, e.g. 'CAR'.
     */
    @Suppress("unused") // Part of the API
    val modalityFlow: Flow<String> = dataStore.data
        .map { settings ->
            settings.modality
        }

    /**
     * Saves the API version of the terms accepted by the user, e.g. 5.
     *
     * @param value The modality.
     */
    @Suppress("unused") // Part of the API
    suspend fun setAcceptedTerms(value: Int) {
        dataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setAcceptedTerms(value)
                .build()
        }
    }

    /**
     * @return The API version of the terms accepted by the user, e.g. 5.
     */
    @Suppress("unused") // Part of the API
    val acceptedTermsFlow: Flow<Int> = dataStore.data
        .map { settings ->
            settings.acceptedTerms
        }
}