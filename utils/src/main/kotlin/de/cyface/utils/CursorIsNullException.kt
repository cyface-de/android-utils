/*
 * Copyright 2018-2023 Cyface GmbH
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

import android.database.Cursor

// Part of the API
/**
 * A wrapper [Exception] for code thrown for a null [Cursor] after executing
 * `ContentProvider.query`.
 *
 * @author Armin Schnabel
 * @version 1.1.1
 * @since 3.0.0
 */
@Suppress("unused") //Part of the API
class CursorIsNullException : Exception {
    /**
     * Creates a new completely initialized [CursorIsNullException], wrapping another [Exception]
     * from deeper within the system.
     *
     * @param e The wrapped `Exception` containing further details about the error.
     */
    constructor(e: Exception) : super(e)

    /**
     * Creates a new completely initialized [CursorIsNullException].
     *
     * @param message The message to show as a detailed explanation for this [Exception].
     */
    constructor(message: String) : super(message)

    /**
     * Creates a new completely initialized [CursorIsNullException] with a default message.
     */
    constructor() : super("Cursor is null. Unable to perform operation.")

    companion object {
        /**
         * Checks if the [Cursor] is null.
         *
         * @param cursor the `Cursor` to be checked
         * @throws CursorIsNullException If `ContentProvider] was inaccessible. See `ContentResolver#query()`.
         */
        @Throws(CursorIsNullException::class)
        fun softCatchNullCursor(cursor: Cursor?) {
            if (cursor == null) {
                throw CursorIsNullException()
            }
        }
    }
}