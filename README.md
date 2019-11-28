Cyface Android Utils Library
=================================

This project contains the Cyface Android Utils Library which contains methods and classes used by multiple Cyface Libraries.

This separation is required to avoid circular dependencies.

## Android Coding Guidelines ##
UI Elements should be represented by their own class implementing the corresponding listener.

## Integration Guide

This library is published to the "Github Packages" Repository.

To use it as a dependency you need to:

1. Setup the Github Packages repository for this dependency: 

    * TODO

2. Add this package as a dependency to your app's `build.gradle`:

    ```
    dependencies {
        implementation "de.cyface:android-utils:$utilsVersion"
    }
    ```

3. Set the `$utilsVersion` gradle variable to the [latest version](https://github.com/cyface-de/android-utils/packages).    

## Development Guide

This library is published to the "Github Packages" Repository.

To publish a new version you need to:

1. Authenticate to Github Packages

    * Have write-access to this Github repository 
    * Create a [personal access token on Github](https://github.com/settings/tokens) with "write:packages" permissions
    * Create or adjust a `local.properties` file in the project root containing:

    ```
    github.user=YOUR_USERNAME
    github.token=YOUR_ACCESS_TOKEN
    ```

2. Publish a new version

* Increment the `build.gradle`'s `ext.version`
* Execute the publish command `./gradlew clean build publish`


License
-------------------
Copyright 2017 Cyface GmbH

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
