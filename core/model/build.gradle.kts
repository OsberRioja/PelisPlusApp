plugins {
    //alias(libs.plugins.kotlin.android)
    //alias(libs.plugins.ksp) // Debe ir DESPUÉS de Kotlin
    //alias(libs.plugins.kapt) // Para Dagger
    //id("kotlin-kapt")
    alias(libs.plugins.jetbrains.kotlin.android) //kotlin
    //alias(libs.plugins.ucb.android.library)
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.ucb.model"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
        targetSdk = 35

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    //ksp(libs.androidx.room.compiler) // Usa KSP, NO annotationProcessor

    // Dagger
    implementation(libs.dagger)
    //kapt("com.google.dagger:dagger-compiler:2.51.1") // Usa kapt
    // Elimina: implementation("com.google.dagger:dagger-compiler:2.51.1")
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.room.runtime)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)
    // optional - RxJava2 support for Room
    implementation(libs.androidx.room.rxjava2)
    // optional - RxJava3 support for Room
    implementation(libs.androidx.room.rxjava3)
    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation(libs.androidx.room.guava)
    // optional - Test helpers
    testImplementation(libs.androidx.room.testing)
    // optional - Paging 3 Integration
    implementation(libs.androidx.room.paging)
    implementation(platform(libs.androidx.compose.bom.v20231001))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
}