import org.jetbrains.kotlin.konan.properties.hasProperty
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.appdistribution")
}

android {
    namespace = "br.com.messore.tech.circleci"
    compileSdk = 33

    defaultConfig {
        applicationId = "br.com.messore.tech.circleci"
        minSdk = 24
        targetSdk = 33
        versionCode = 3
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    val properties = Properties().apply {
        val file = rootProject.file("local.properties")
        if (file.exists()) load(file.reader())
    }

    signingConfigs {
        create("release") {

            storeFile = file(System.getenv("keystore") ?: properties.getProperty("keystore"))
            storePassword = System.getenv("keystore_password") ?: properties.getProperty("keystore_password")

            keyAlias = System.getenv("key_alias") ?: properties.getProperty("key_alias")
            keyPassword = System.getenv("key_password") ?: properties.getProperty("key_password")
        }
    }

    buildTypes {
        release {
            firebaseAppDistribution {
                val googleAppId = if (properties.hasProperty("google_app_id")) properties.getProperty("google_app_id")
                else System.getenv("google_app_id")

                appId = googleAppId
                artifactType = "APK"
                releaseNotes = "Another version"
                testers = "ezequielmessore@gmail.com, ezequielmessore.developer@gmail.com"
                serviceCredentialsFile = "./google-services-account.json"
            }

            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.core:core-ktx:1.13.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.5")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(platform("com.google.firebase:firebase-bom:31.2.0"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    //Compose
    implementation("androidx.activity:activity-compose:1.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.3.3")

    //Compose BOM
    implementation(platform("androidx.compose:compose-bom:2022.12.00"))

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}