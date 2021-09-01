/*
 * Copyright 2017-2021 Cyface GmbH
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

import java.util.Collection;

import androidx.annotation.Nullable;

/**
 * A class with static methods to check method pre- and post conditions.
 *
 * @author Klemens Muthmann
 * @author Armin Schnabel
 * @version 3.0.0
 * @since 2.2.0
 */
@SuppressWarnings("unused") // Part of the API
public class Validate {

    @SuppressWarnings("unused") // Part of the API
    public static void notEmpty(final String string) {
        notEmpty("String should not be empty.", string);
    }

    @SuppressWarnings("WeakerAccess") // Part of the API
    public static void notEmpty(final String string, final String message) {
        if (string == null || string.isEmpty()) {
            throw new ValidationException(message);
        }
    }

    @SuppressWarnings("unused") // Part of the API
    public static void notEmpty(@SuppressWarnings("rawtypes") final Collection collection) {
        if (collection == null || collection.isEmpty()) {
            throw new ValidationException("Collection may not be empty or null.");
        }
    }

    @SuppressWarnings("unused") // Part of the API
    public static void notEmpty(final Object[] array) {
        if (array == null || array.length == 0) {
            throw new ValidationException("Array may not be empty or null.");
        }
    }

    @SuppressWarnings("WeakerAccess") // Part of the API
    public static void notNull(@Nullable final Object object, final String message) {
        if (object == null) {
            throw new ValidationException(message);
        }
    }

    @SuppressWarnings({"unused", "WeakerAccess"}) // Part of the API
    public static void notNull(@Nullable final Object object) {
        notNull(object, "Object was null.");
    }

    @SuppressWarnings("unused") // Part of the API
    public static void isTrue(final boolean b) {
        isTrue(b, "Expression was not true.");
    }

    @SuppressWarnings("WeakerAccess") // Part of the API
    public static void isTrue(final boolean b, final String message) {
        if (!b) {
            throw new ValidationException(message);
        }
    }
}