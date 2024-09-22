plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.cinemamanagerapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cinemamanagerapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

// Glide - thư viện tải và hiển thị hình ảnh
    implementation("com.github.bumptech.glide:glide:4.16.0")

// Chip Navigation Bar - thư viện tạo thanh điều hướng với chip
    implementation("com.github.ismaeldivita:chip-navigation-bar:1.4.0")

// BlurView - thư viện tạo hiệu ứng mờ cho các view
    implementation("com.github.Dimezis:BlurView:2.0.5")
}
