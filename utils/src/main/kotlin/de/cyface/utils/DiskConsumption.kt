/*
 * Copyright 2018 Cyface GmbH
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

import android.os.Environment
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import android.os.StatFs
import android.util.Log
import de.cyface.utils.Constants.MINIMUM_MEGABYTES_REQUIRED
import de.cyface.utils.Constants.TAG
import java.util.Locale

/**
 * Objects of this class represent the current disk (or rather SD card) space used and available. This space is mostly
 * filled with unsynchronized measurements. To avoid filling up the users SD card it is advisable to delete
 * measurements as soon as they use up too much space.
 *
 * @author Klemens Muthmann
 * @author Armin Schnabel
 * @version 2.0.1
 * @since 1.0.0
 */
class DiskConsumption : Parcelable {
    /**
     * The count of bytes currently used by this app.
     */
    private val consumedBytes: Long

    /**
     * The count of bytes still available for this app.
     */
    private val availableBytes: Long

    /**
     * Creates a new completely initialized `DiskConsumption` object.
     *
     * @param consumedBytes  The count of bytes currently used by this app.
     * @param availableBytes The count of bytes still available for this app.
     */
    @Suppress("unused") // Part of the API
    constructor(consumedBytes: Long, availableBytes: Long) {
        require(consumedBytes >= 0) {
            String.format(
                Locale.US,
                "Illegal value for consumed bytes. May not be smaller then 0 but was %d",
                consumedBytes
            )
        }
        require(availableBytes >= 0) {
            String.format(
                Locale.US,
                "Illegal value for available bytes. May not be smaller then 0 but was %d",
                availableBytes
            )
        }

        this.consumedBytes = consumedBytes
        this.availableBytes = availableBytes
    }

    /*
     * MARK: Parcelable Interface
     */
    /**
     * Constructor as required by `Parcelable` implementation.
     *
     * @param in A `Parcel` that is a serialized version of a `DiskConsumption`.
     */
    private constructor(`in`: Parcel) {
        consumedBytes = `in`.readLong()
        availableBytes = `in`.readLong()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(consumedBytes)
        dest.writeLong(availableBytes)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as DiskConsumption
        return consumedBytes == that.consumedBytes && availableBytes == that.availableBytes
    }

    override fun hashCode(): Int {
        var result = 1
        result = 31 * result + (consumedBytes xor (consumedBytes ushr 32)).toInt()
        result = 31 * result + (availableBytes xor (availableBytes ushr 32)).toInt()
        return result
    }

    companion object {
        /**
         * The `Parcelable` creator as required by the Android Parcelable specification.
         */
        @Suppress("unused") // Required for Parcelable
        @JvmField
        val CREATOR: Creator<DiskConsumption> = object : Creator<DiskConsumption> {
            override fun createFromParcel(`in`: Parcel): DiskConsumption {
                return DiskConsumption(`in`)
            }

            override fun newArray(size: Int): Array<DiskConsumption?> {
                return arrayOfNulls(size)
            }
        }

        /**
         * Checks how much storage is left.
         *
         * @return The number of bytes of space available.
         */
        private fun bytesAvailable(): Long {
            val stat = StatFs(Environment.getExternalStorageDirectory().path)
            val bytesAvailable = stat.blockSizeLong * stat.availableBlocksLong
            Log.v(TAG, "Space available: " + (bytesAvailable / (1024 * 1024)) + " MB")
            return bytesAvailable
        }

        /**
         * Checks if at last `MINIMUM_MEGABYTES_REQUIRED` of space is available.
         *
         * @return True if enough space is available.
         */
        @Suppress("unused") // Part of the API
        fun spaceAvailable(): Boolean {
            return (bytesAvailable() / (1024 * 1024)) > MINIMUM_MEGABYTES_REQUIRED
        }
    }
}
