plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.foodhub"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.foodhub"
        minSdk = 24
        targetSdk = 35
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //navigation
    implementation(libs.androidx.navigation.compose)
    //coil
    implementation(libs.coil.compose)
    //material-icons-extended
    implementation(libs.androidx.material.icons.extended)
    // Accompanist SwipeRefresh
    implementation(libs.androidx.material)
    //Retrofit & Gson
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    //hilt-dagger
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)// Only if using Jetpack Compose + HiltViewModel
    // Use KSP instead of kapt
    ksp(libs.hilt.compiler)
    //lifecycle-viewmodel-compose
    implementation(libs.lifecycle.viewmodel.compose)
    // For state handling
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.runtime.livedata)
    //Room
    implementation(libs.androidx.room.runtime) // Room runtime
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)        // Room compiler for annotation processing
    implementation(libs.androidx.room.ktx)     // Room KTX for coroutine support
    //datastore
    implementation(libs.androidx.datastore.preferences)
    //Firebase
    implementation(libs.firebase.auth.ktx)
    implementation(libs.play.services.auth)

}