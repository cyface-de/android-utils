Cyface Android Utils Library
=================================

[<img src="https://github.com/cyface-de/android-utils/workflows/Gradle%20Build/badge.svg">](https://github.com/cyface-de/android-utils/actions)
[<img src="https://github.com/cyface-de/android-utils/workflows/Gradle%20Publish/badge.svg">](https://github.com/cyface-de/android-utils/actions)

This project contains the Cyface Android Utils Library which contains methods and classes used by multiple Cyface Libraries.
This separation is required to avoid circular dependencies.

Integration Guide
---------------------

This library is published to the Github Package Registry.

To use it as a dependency you need to:

1. Make sure you are authenticated to the repository:

    * You need a Github account with read-access to this Github repository
    * Create a [personal access token on Github](https://github.com/settings/tokens) with "read:packages" permissions
    * Create or adjust a `local.properties` file in the project root containing:

    ```
    github.user=YOUR_USERNAME
    github.token=YOUR_ACCESS_TOKEN
    ```

    * Add the custom repository to your `build.gradle`:

    ```
    def properties = new Properties()
    properties.load(new FileInputStream("local.properties"))

    repositories {
        // Other maven repositories, e.g.:
        jcenter()
        google()
        // Repository for this library
        maven {
            url = uri("https://maven.pkg.github.com/cyface-de/android-utils")
            credentials {
                username = properties.getProperty("github.user")
                password = properties.getProperty("github.token")
            }
        }
    }
    ```

2. Add this package as a dependency to your app's `build.gradle`:

    ```
    dependencies {
        implementation "de.cyface:android-utils:$utilsVersion"
    }
    ```

3. Set the `$utilsVersion` gradle variable to the [latest version](https://github.com/cyface-de/android-utils/releases).

Development Guide
--------------------

### Release a new version

This library is published to the Github Package Registry.

To publish a new version you need to:

1. Make sure you are authenticated to the repository:

    * You need a Github account with write-access to this Github repository
    * Create a [personal access token on Github](https://github.com/settings/tokens) with "write:packages" permissions
    * Create or adjust a `local.properties` file in the project root containing:

    ```
    github.user=YOUR_USERNAME
    github.token=YOUR_ACCESS_TOKEN
    ```

2. Publish a new version

    * Increment the `build.gradle`'s `ext.version`
    * Execute the publish command `./gradlew publish`


License
-------------------
Copyright 2017-2021 Cyface GmbH

This file is part of the Cyface Utils for Android.

The Cyface Utils for Android is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

The Cyface Utils for Android is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with the Cyface Utils for Android. If not, see <http://www.gnu.org/licenses/>.
