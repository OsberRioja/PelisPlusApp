plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android) //kotlin
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.ucb.network"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
        //noinspection EditedTargetSdkVersion
        targetSdk = 35
        //testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.moshi)
    implementation(libs.converter.moshi)
    kapt(libs.moshi.kapt)
    implementation(project(":core:model"))
}

kapt {
    correctErrorTypes = true
}