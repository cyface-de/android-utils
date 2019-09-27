Cyface Android Utils Library
=================================

This project contains the Cyface Android Utils Library which contains methods and classes used by multiple Cyface Libraries.

This separation is required to avoid circular dependencies.

## Android Coding Guidelines ##
UI Elements should be represented by their own class implementing the corresponding listener.

## Running integration tests in Android Studio
* Make sure you use the run config 'Android Instrumented Test'.

## Integration Guide

This library is published to a custom Maven Repository. To it as a dependency you need to:

* Add this custom maven repository in your `build.gradle`:

``` 
repositories {
    // Other maven repositories, e.g.:
    jcenter()
    google()
    // Repository for Cyface Android Utils library
    maven {
        url 'https://dl.bintray.com/cyface-de/android-utils'
    }
}
```

* Add the cyface-utils library as dependency in your app's `build.gradle`:

```
dependencies {
    implementation "de.cyface:android-utils:$utilsVersion"
}
```

* Set the `$utilsVersion` gradle variable to a version. Check the latest version in the [Maven Repository](https://bintray.com/cyface-de/android-utils/de.cyface.utils).  

## Development Guide

This library is published using the [Bintray Plugin](https://github.com/bintray/gradle-bintray-plugin).

To publish a new version you need to:

* Increment the `build.gradle`'s `ext.version`
* Execute the publish command `./gradlew clean build bintrayUpload --info`
* Log in to [Bintray](https://bintray.com/cyface-de/android-utils/de.cyface.utils) (you must be part of the cyface-de Team)
* Click on the `Publish` button of the `Notice` which shows you that a release is ready to be published

### Tutorials

For further details see:

* https://medium.com/swlh/how-to-publish-and-distribute-your-android-library-ce845c68c7f7
* https://github.com/bintray/gradle-bintray-plugin#readme