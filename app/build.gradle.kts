plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.pdfreader_kotlin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pdfreader_kotlin"
        minSdk = 26
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    //Koin
    implementation("io.insert-koin:koin-core:3.1.5")
    implementation("io.insert-koin:koin-android:3.1.5")
    implementation("io.insert-koin:koin-android-compat:3.1.5")
    //viewModel MVVM
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0-rc01")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0-rc01")
    //viewpager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    //CollapsingToolbarLayout
    implementation("com.google.android.material:material:1.9.0")
    // pdf
    implementation("com.github.mhiew:android-pdf-viewer:3.2.0-beta.3")


    // doc, ppt, xls
    // Thêm Apache POI cho tài liệu Word (DOC, DOCX)
    implementation ("org.apache.poi:poi:5.2.3") // Thư viện chính
    implementation ("org.apache.poi:poi-ooxml:5.2.3")// Hỗ trợ cho DOCX
    implementation ("org.apache.xmlbeans:xmlbeans:5.1.1") // Cần thiết cho POI
//    implementation ("org.apache.poi:poi-ooxml-schemas:4.1.2") // Cần thiết cho file XLSX
//    implementation ("org.apache.poi:poi-ooxml-schemas:4.1.2") // cho Excel
    implementation ("org.apache.poi:poi-scratchpad:5.2.3") // cho PPT
}