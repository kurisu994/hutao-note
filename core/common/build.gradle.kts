plugins {
    alias(libs.plugins.kurisu.android.library)
    alias(libs.plugins.kurisu.android.hilt)
}

android {
    namespace = "cn.kurisu.core.common"

    buildTypes {
        create("preview") {
        }
    }
}

dependencies {
    androidTestImplementation(libs.androidx.test.ext)
}