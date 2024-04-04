plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.candroid_diagnostics"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.candroid_diagnostics"
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

    dependencies {
        implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.1.0")
        implementation("org.eclipse.paho:org.eclipse.paho.android.service:1.1.1")
        //implementation("com.github.PhilJay:MPAndroidChart:v3.1.3")
    }
}


dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.room:room-runtime:2.3.0")
    annotationProcessor("androidx.room:room-compiler:2.3.0")

    implementation ("com.jjoe64:graphview:4.2.2")

}