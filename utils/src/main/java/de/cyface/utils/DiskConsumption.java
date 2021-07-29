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
package de.cyface.utils;

import static de.cyface.utils.Constants.MINIMUM_MEGABYTES_REQUIRED;
import static de.cyface.utils.Constants.TAG;

import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StatFs;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Locale;

/**
 * Objects of this class represent the current disk (or rather SD card) space used and available. This space is mostly
 * filled with unsynchronized measurements. To avoid filling up the users SD card it is advisable to delete
 * measurements as soon as they use up too much space.
 *
 * @author Klemens Muthmann
 * @author Armin Schnabel
 * @version 2.0.0
 * @since 1.0.0
 */
public final class DiskConsumption implements Parcelable {

    /**
     * The count of bytes currently used by this app.
     */
    private final long consumedBytes;
    /**
     * The count of bytes still available for this app.
     */
    private final long availableBytes;

    /**
     * Creates a new completely initialized {@code DiskConsumption} object.
     *
     * @param consumedBytes  The count of bytes currently used by this app.
     * @param availableBytes The count of bytes still available for this app.
     */
    @SuppressWarnings("unused") // Part of the API
    public DiskConsumption(final long consumedBytes, final long availableBytes) {
        if (consumedBytes < 0) {
            throw new IllegalArgumentException(String.format(Locale.US,
                    "Illegal value for consumed bytes. May not be smaller then 0 but was %d", consumedBytes));
        }
        if (availableBytes < 0) {
            throw new IllegalArgumentException(String.format(Locale.US,
                    "Illegal value for available bytes. May not be smaller then 0 but was %d", availableBytes));
        }

        this.consumedBytes = consumedBytes;
        this.availableBytes = availableBytes;
    }

    /*
     * MARK: Parcelable Interface
     */

    /**
     * Constructor as required by <code>Parcelable</code> implementation.
     *
     * @param in A <code>Parcel</code> that is a serialized version of a <code>DiskConsumption</code>.
     */
    private DiskConsumption(final @NonNull Parcel in) {
        consumedBytes = in.readLong();
        availableBytes = in.readLong();
    }

    /**
     * The <code>Parcelable</code> creator as required by the Android Parcelable specification.
     */
    public static final Creator<DiskConsumption> CREATOR = new Creator<>() {
        @Override
        public DiskConsumption createFromParcel(final Parcel in) {
            return new DiskConsumption(in);
        }

        @Override
        public DiskConsumption[] newArray(final int size) {
            return new DiskConsumption[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeLong(consumedBytes);
        dest.writeLong(availableBytes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DiskConsumption that = (DiskConsumption) o;
        return consumedBytes == that.consumedBytes && availableBytes == that.availableBytes;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (int) (consumedBytes ^ (consumedBytes >>> 32));
        result = 31 * result + (int) (availableBytes ^ (availableBytes >>> 32));
        return result;
    }

    /**
     * Checks how much storage is left.
     *
     * @return The number of bytes of space available.
     */
    private static long bytesAvailable() {
        final var stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        final var bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        Log.v(TAG, "Space available: " + (bytesAvailable / (1024 * 1024)) + " MB");
        return bytesAvailable;
    }

    /**
     * Checks if at last {@code MINIMUM_MEGABYTES_REQUIRED} of space is available.
     *
     * @return True if enough space is available.
     */
    @SuppressWarnings("unused") // Part of the API
    public static boolean spaceAvailable() {
        return (bytesAvailable() / (1024 * 1024)) > MINIMUM_MEGABYTES_REQUIRED;
    }
}
