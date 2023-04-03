plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

kotlin {

    jvmToolchain(17)
}

android {
    namespace = "com.example.adsapplication"
    compileSdk = 33

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }

    defaultConfig {
        applicationId = "com.example.adsapplication"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    //
    // COMPOSE
    //

    // Material Design 3
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material)
   // implementation(libs.androidx.material3.window.size.class)

    // Android Studio Preview support
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //
    // NAVIGATION
    //

    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.navigation.animation)

    //
    // UTILITIES
    //

    // Room

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt("androidx.room:room-compiler")
    // retrofit

    implementation(libs.retrofit)

    // GSON
    implementation(libs.converter.gson)

    // coroutine
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // Coil
    implementation(libs.coil)
    implementation(libs.coil.compose)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    kapt(libs.androidx.room.compiler)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.junit)
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.androidx.room.testing)

    // for JVM:
    testImplementation(libs.kluent)
    // for Android:
    testImplementation(libs.kluent.android)
    // ...with Kotlin.
    kaptTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}