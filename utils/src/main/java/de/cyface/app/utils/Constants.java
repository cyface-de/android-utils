/*
 * Copyright 2018 Cyface GmbH
 *
 * This file is part of the Cyface SDK for Android.
 *
 * The Cyface SDK for Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Cyface SDK for Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Cyface SDK for Android. If not, see <http://www.gnu.org/licenses/>.
 */
package de.cyface.app.utils;

/**
 * Final static constants used by multiple classes.
 *
 * @author Armin Schnabel
 * @version 1.0.1
 * @since 2.5.0
 */
public final class Constants {

    /**
     * Tag used to identify Logcat messages issued by instances of this package.
     */
    public final static String TAG = "de.cyface.utils";
    /**
     * The minimum space required for capturing. We don't want to use the space full up as this would
     * slow down the device and could get unusable.
     */
    final static long MINIMUM_MEGABYTES_REQUIRED = 100L;

    private Constants() {
        // Nothing to do here.
    }
}
