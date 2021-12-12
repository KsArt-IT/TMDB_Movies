plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = rootProject.extra["maxSdkVers"] as Int

    defaultConfig {
        applicationId = "ru.ksart.timefocus"
        minSdk = rootProject.extra["minSdkVers"] as Int
        targetSdk = rootProject.extra["maxSdkVers"] as Int
        versionCode = rootProject.extra["codeVers"] as Int
        versionName = rootProject.extra["nameVers"] as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")

    // SplashScreen on devices prior Android 12
    implementation("androidx.core:core-splashscreen:1.0.0-alpha02")
    // Fragment
    val fragmentVers: String by rootProject.extra
    implementation("androidx.fragment:fragment-ktx:$fragmentVers")
    implementation("androidx.activity:activity-ktx:1.4.0")
    // Lifecycle KTX
    val lifecycleVers: String by rootProject.extra
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVers")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVers")
    // Saved State module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVers")
    // Navigation
    val navigationVers: String by rootProject.extra
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVers")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVers")
    // Coroutines and Flow
    val coroutinesVers: String by rootProject.extra
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVers")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVers")
    // Dagger Hilt
    val hiltVers: String by rootProject.extra
    implementation("com.google.dagger:hilt-android:${rootProject.extra["hiltVers"]}")
    kapt("com.google.dagger:hilt-compiler:$hiltVers")
    // Retrofit
    val retrofitVers: String by rootProject.extra
    implementation("com.squareup.retrofit2:retrofit:$retrofitVers")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVers")
    // Okhttp
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3")
    // Moshi
    val moshiVers: String by rootProject.extra
    implementation("com.squareup.moshi:moshi:$moshiVers")
    implementation("com.squareup.moshi:moshi-kotlin:$moshiVers")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshiVers")
    // RoomDao
    val roomVers: String by rootProject.extra
    implementation("androidx.room:room-runtime:$roomVers")
    implementation("androidx.room:room-ktx:$roomVers")
    kapt("androidx.room:room-compiler:$roomVers")
    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    // Coil
    val coilVers: String by rootProject.extra
    implementation("io.coil-kt:coil:$coilVers")
    // PreferenceFragmentCompat
    implementation("androidx.preference:preference-ktx:1.1.1")
    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
