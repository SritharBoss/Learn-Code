plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.srithar.rtspplayer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.srithar.rtspplayer"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation("com.github.alexeyvasilyev:rtsp-client-android:2.0.11")
}