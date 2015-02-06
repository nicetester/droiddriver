# droiddriver

clone of https://android.googlesource.com/platform/external/droiddriver

- [Java Docs](http://appium.github.io/droiddriver/javadoc/)
- [DroidDriver examples](https://github.com/appium/droiddriver_examples)
- [Contributing to droiddriver](contributing.md)

#### gradle tasks

List tasks

> ./gradlew tasks

Create the jar `app/build/libs/droiddriver.jar`

> ./gradlew clean compileReleaseJava jar

Create javadoc jar `app/build/libs/droiddriver-javadoc.jar`

> ./gradlew bundleJavadocRelease

Create sources jar `app/build/libs/droiddriver-sources.jar`

> ./gradlew sourceJar

---

#### Import into Android Studio

- Clone from GitHub as `droiddriver_project` otherwise Android Studio will call the module `droiddriver-droiddriver`

> `git clone https://github.com/appium/droiddriver.git droiddriver_project`

- Launch Android Studio and select `Open an existing Android Studio project`
- Navigate to `droiddriver_project/build.gradle` and press Choose
- Ensure that `Use default gradle wrapper (recommended)` is selected
- Android Studio will now import the project successfully
