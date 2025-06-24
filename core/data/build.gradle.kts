plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    // SOLO si necesitas KSP en este módulo
   // id("com.google.devtools.ksp") version "1.9.22-1.0.17"
}

android {
    namespace = "com.ucb.data"
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
// Básicos
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Room (versión simplificada)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)       // Solo si usas corrutinas
    //ksp("androidx.room:room-compiler:2.6.1")           // Usar KSP para compilación

    // Eliminar estas dependencias opcionales (a menos que las uses):
    implementation(libs.androidx.room.rxjava2)
    implementation(libs.androidx.room.rxjava3)
    implementation(libs.androidx.room.guava)
    testImplementation(libs.androidx.room.testing)
    implementation(libs.androidx.room.paging)

    // Módulos internos
    implementation(project(":core:model"))
    implementation(project(":core:network"))
}