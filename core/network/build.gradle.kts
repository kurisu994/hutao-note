plugins {
    alias(libs.plugins.kurisu.android.library)
    alias(libs.plugins.kurisu.android.hilt)
    id("kotlinx-serialization")
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "cn.kurisu.core.network"

    buildTypes {
        debug {
            buildConfigField("String", "HOST_URL", "\"https://testgate.feewee.cn\"")
        }
        create("preview") {
            buildConfigField("String", "HOST_URL", "\"https://testgate.feewee.cn\"")
        }
        release {
            buildConfigField("String", "HOST_URL", "\"https://gate.feewee.cn\"")
        }
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(libs.kotlinx.datetime)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
    implementation(libs.coil.kt.gif)
    implementation(libs.coil.kt.video)
    implementation(libs.coil.kt.svg)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
}