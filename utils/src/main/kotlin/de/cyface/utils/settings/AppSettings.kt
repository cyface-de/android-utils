/*
 * Copyright 2023-2025 Cyface GmbH
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
import androidx.datastore.core.DataStore
import androidx.datastore.core.MultiProcessDataStoreFactory
import de.cyface.utils.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File

/**
 * Common settings used by both, UIs and libraries.
 *
 * We currently don't use a repository to abstract the interface of the data types from the data
 * source. The reason for this is the class is very simple and we don't plan multiple data sources.
 * If this changes, consider using the standard Android Architecture, see `MeasurementRepository`.
 *
 * @author Armin Schnabel
 * @version 3.0.0
 * @since 3.4.0
 * @param context The context to access the preferences from.
 */
@Suppress("unused") // Part of the API
class AppSettings private constructor(context: Context) {

    /**
     * Use Singleton to ensure only one instance per process is created. [LEIP-294]
     *
     * It should be okay to use a Singleton as this is also suggested in the documentation:
     * https://developer.android.com/topic/libraries/architecture/datastore#multiprocess
     */
    companion object {
        @Volatile
        private var instance: AppSettings? = null

        fun getInstance(context: Context): AppSettings {
            return instance ?: synchronized(this) {
                instance ?: AppSettings(context.applicationContext).also { instance = it }
            }
        }
    }

    /**
     * This avoids leaking the context when this object outlives the Activity of Fragment.
     */
    private val appContext = context.applicationContext

    /**
     * The data store with multi-process support.
     *
     * The reason for multi-process support is that the upload enabled flag is accessed by a
     * background service which may run in another process.
     *
     * Attention:
     * - Never mix SingleProcessDataStore with MultiProcessDataStore for the same file.
     * - We use MultiProcessDataStore, i.e. the preferences can be accessed from multiple processes.
     * - Only create one instance of `DataStore` per file in the same process.
     * - We use ProtoBuf to ensure type safety. Rebuild after changing the .proto file.
     */
    private val dataStore: DataStore<Settings> = MultiProcessDataStoreFactory.create(
        serializer = SettingsSerializer,
        produceFile = {
            // With cacheDir the settings are lost on app restart [RFR-799]
            File("${appContext.filesDir.path}/settings.pb")
        },
        migrations = listOf(
            PreferencesMigrationFactory.create(appContext),
            StoreMigration()
        )
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
            settings.centerMap // FIXME: Use versioning to set default to true
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
            settings.uploadEnabled // FIXME: Use versioning to set default to true
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
            settings.sensorFrequency // FIXME: Use versioning to set default value
        }

    /**
     * Saves if errors show be reported.
     *
     * @param value `true` if errors should be reported.
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
     * @return `true` if errors should be reported.
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