plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.relojchecadoralupratic"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.relojchecadoralupratic"
        minSdk = 27
        targetSdk = 33
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // AndroidX Libraries
    implementation("androidx.core:core-ktx:1.9.0") // Extensiones de Kotlin para las bibliotecas de AndroidX Core
    implementation("androidx.appcompat:appcompat:1.6.1") // Biblioteca de compatibilidad para utilizar funciones modernas en versiones antiguas de Android
    implementation("com.google.android.material:material:1.11.0") // Componentes de Material Design para Android
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // Biblioteca para diseño de interfaces de usuario en Android

    // AndroidX Lifecycle Components
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0") // LiveData de AndroidX con extensiones de Kotlin
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0") // ViewModel de AndroidX con extensiones de Kotlin

    // AndroidX Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6") // Fragmentos de AndroidX para la navegación
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6") // Biblioteca de IU para la navegación en AndroidX

    // Annotations
    implementation("androidx.annotation:annotation:1.7.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0") // Anotaciones de AndroidX

    // Testing
    testImplementation("junit:junit:4.13.2") // JUnit para pruebas unitarias
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Extensiones de JUnit para pruebas de Android
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Marco de pruebas para UI de Android

    // Retrofit para comunicación de red
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Biblioteca para realizar solicitudes HTTP
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Convertidor Gson para Retrofit (para procesar JSON)
}
