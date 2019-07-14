# image-picker
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
        …
        maven { url 'https://jitpack.io' }
    }
}
  ```

*Step 2.* Add the dependency
  ```groovy
	dependencies {
	    implementation 'com.github.iChintanSoni:image-picker:1.0.1'
	}
  ```
## Usage

#### Apply desired output format in `Configuration`:
```kotlin
// If you want File as output
val configuration = Configuration(target = Target.FileTarget)

// If you want Uri as output
val configuration = Configuration(target = Target.UriTarget)

// If you want Bitmap as output
val configuration = Configuration(target = Target.BitmapTarget)
```

#### Get image in your Activity/Fragment:
```kotlin
imagePicker.getImage(this, configuration) {
    when (it) {
        is Result.Success -> {
            processResult(it.output)
        }
        is Result.Failure -> {
            Toast.makeText(this, it.throwable.message, Toast.LENGTH_SHORT).show()
        }
    }
}
```
#### Process the output:
```kotlin
private fun processResult(output: Output) {
    when (output) {
        is Output.FileOutput -> {
            Glide
                .with(this)
                .load(output.data)
                .into(imageView)
        }
        is Output.UriOutput -> {
            Glide
                .with(this)
                .load(output.data)
                .into(imageView)
        }
        is Output.BitmapOutput -> {
            Glide
                .with(this)
                .load(output.data)
                .into(imageView)
        }
    }
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
