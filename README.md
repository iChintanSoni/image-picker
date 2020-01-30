# image-picker

[![License](https://img.shields.io/badge/License-Apache%202.0-2196F3.svg?style=for-the-badge)](https://opensource.org/licenses/Apache-2.0)
[![language](https://img.shields.io/github/languages/top/BirjuVachhani/location-extension-android.svg?style=for-the-badge&colorB=f18e33)](https://kotlinlang.org/)
[![Platform](https://img.shields.io/badge/Platform-Android-green.svg?style=for-the-badge)](https://www.android.com/)
[![API](https://img.shields.io/badge/API-17%2B-F44336.svg?style=for-the-badge)](https://android-arsenal.com/api?level=16)

Image Picker, a tiny Kotlin Android library that allows developers to implement image picking stuff, in merely 3 lines of code.

## Features
These 3 lines of code handles everything you for you:
* Storage Permission Model
* Writing File Provider
* Ability to choose image from Camera and Gallery
* Ability to choose images from other Document Providers like Google Photos, etc

## Gradle Dependency
*Step 1.* Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
  ```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
  ```

*Step 2.* Add the dependency
  ```groovy
	dependencies {
	    implementation 'com.github.iChintanSoni:image-picker:2.0.0-alpha01'
	}
  ```
## Usage

#### Get image in your Activity/Fragment:
```kotlin
val imagePicker = ImagePicker()
imagePicker.getImage(this) {
    getBitmap() // Image as Bitmap
    getFile() // Image as File
    getUri() // Image as Uri
}.onFailure {
    // something went wrong!
}
```

#### Changing default configurations:

Method: 1
```kotlin
private val imagePicker = ImagePicker {
    rationaleText = "This is custom rationale text"
    blockedText = "This is custom permission blocked text"
}
```

Method: 2
```kotlin
imagePicker.configure {
    rationaleText = "This is custom rationale text"
    blockedText = "This is custom permission blocked text"
}
```

# License

```
   Copyright © 2019 Chintan Soni

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
