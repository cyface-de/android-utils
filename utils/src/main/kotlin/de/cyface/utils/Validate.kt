/*
 * Copyright 2017-2023 Cyface GmbH
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

/**
 * A class with static methods to check method pre- and post conditions.
 *
 * @author Klemens Muthmann
 * @author Armin Schnabel
 * @version 3.0.1
 * @since 2.2.0
 */
object Validate {
    @Suppress("unused") // Part of the API
    fun notEmpty(string: String?) {
        notEmpty("String should not be empty.", string)
    }

    @Suppress("MemberVisibilityCanBePrivate") // Part of the API
    fun notEmpty(string: String?, message: String?) {
        if (string == null || string.isEmpty()) {
            throw ValidationException(message)
        }
    }

    @Suppress("unused") // Part of the API
    fun notEmpty(collection: Collection<*>?) {
        if (collection == null || collection.isEmpty()) {
            throw ValidationException("Collection may not be empty or null.")
        }
    }

    @Suppress("unused") // Part of the API
    fun notEmpty(array: Array<Any?>?) {
        if (array == null || array.isEmpty()) {
            throw ValidationException("Array may not be empty or null.")
        }
    }

    @JvmOverloads  // Part of the API
    fun notNull(`object`: Any?, message: String? = "Object was null.") {
        if (`object` == null) {
            throw ValidationException(message)
        }
    }

    @Suppress("unused") // Part of the API
    fun isTrue(b: Boolean) {
        isTrue(b, "Expression was not true.")
    }

    @Suppress("MemberVisibilityCanBePrivate") // Part of the API
    fun isTrue(b: Boolean, message: String?) {
        if (!b) {
            throw ValidationException(message)
        }
    }
}