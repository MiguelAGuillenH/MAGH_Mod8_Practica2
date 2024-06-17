plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
}

android {
    namespace = "com.magh.mod8practica2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.magh.mod8practica2"
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
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    //Para retrofit y Gson
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //Adicional para el interceptor
    implementation(libs.logging.interceptor)

    //Glide y Picasso
    implementation(libs.glide)
    implementation(libs.picasso)

    //Corrutinas con alcance lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.ktx)

    //ViewModel for Fragments
    implementation(libs.androidx.fragment.ktx)

    //Imágenes con bordes redondeados
    implementation(libs.roundedimageview)
    implementation(libs.androidx.activity.v182)

    //Picasso transformations
    implementation(libs.picasso.transformations)

    //Splash screen
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.firebase.auth)

    //Google Maps
    implementation(libs.play.services.maps)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}