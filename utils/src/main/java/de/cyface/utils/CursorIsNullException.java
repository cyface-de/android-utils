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

import android.content.ContentProvider;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A wrapper {@link Exception} for code thrown for a null {@link Cursor} after executing
 * {@link ContentProvider#query(Uri, String[], Bundle, CancellationSignal)}.
 *
 * @author Armin Schnabel
 * @version 1.0.2
 * @since 3.0.0
 */
@SuppressWarnings("unused") // Part of the API
public final class CursorIsNullException extends Exception {

    /**
     * Creates a new completely initialized {@link CursorIsNullException}, wrapping another {@link Exception}
     * from deeper within the system.
     *
     * @param e The wrapped {@code Exception} containing further details about the error.
     */
    public CursorIsNullException(final @NonNull Exception e) {
        super(e);
    }

    /**
     * Creates a new completely initialized {@link CursorIsNullException}.
     *
     * @param message The message to show as a detailed explanation for this {@link Exception}.
     */
    public CursorIsNullException(final @NonNull String message) {
        super(message);
    }

    /**
     * Creates a new completely initialized {@link CursorIsNullException} with a default message.
     */
    private CursorIsNullException() {
        super("Cursor is null. Unable to perform operation.");
    }

    /**
     * Checks if the {@link Cursor} is null.
     *
     * @param cursor the {@code Cursor} to be checked
     * @throws CursorIsNullException If {@link ContentProvider} was inaccessible. See {@code ContentResolver#query()}.
     */
    public static void softCatchNullCursor(@Nullable Cursor cursor) throws CursorIsNullException {
        if (cursor == null) {
            throw new CursorIsNullException();
        }
    }
}
