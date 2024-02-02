import cn.kurisu.apps.NiaBuildSuffix
import com.android.build.api.variant.FilterConfiguration
import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.kurisu.android.application)
    alias(libs.plugins.kurisu.android.compose)
    alias(libs.plugins.kurisu.android.hilt)
    alias(libs.plugins.kurisu.android.mpaas)
}

android {
    namespace = "cn.kurisu.hutaonote"

    signingConfigs {
        getByName("debug") {
            enableV1Signing = true
            keyAlias = "debug"
            keyPassword = "123456"
            storeFile = file("./keystore/debug.keystore")
            storePassword = "123456"
        }
        create("preview") {
            enableV1Signing = true
            keyAlias = "preview"
            keyPassword = "123456"
            storeFile = file("./keystore/preview.keystore")
            storePassword = "123456"
        }
        create("release") {
            enableV1Signing = true
            keyAlias = "hutao"
            keyPassword = "123456"
            storeFile = file("./keystore/hutao.keystore")
            storePassword = "mygvo0oq"
        }
    }

    defaultConfig {
        applicationId = "cn.kurisu.hutaonote"
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("boolean", "LOG_ENABLE", "${true}")
        buildConfigField("String", "BUGLY_ID", "\"\"")
        buildConfigField("String", "HOST_URL", "\"https://xxxxx.cn\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        all {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isMinifyEnabled = false
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = NiaBuildSuffix.DEBUG.applicationIdSuffix
            resValue("string", "app_name", "胡桃笔记开发版")
            resValue("string", "corner_desc", "DEBUG")
        }
        val release = getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            applicationIdSuffix = NiaBuildSuffix.RELEASE.applicationIdSuffix

            resValue("string", "app_name", "胡桃笔记")
            resValue("string", "corner_desc", "")
        }
        register("preview") {
            initWith(release)
            signingConfig = signingConfigs.getByName("preview")
            isDebuggable = true
            applicationIdSuffix = NiaBuildSuffix.PREVIEW.applicationIdSuffix
            resValue("string", "app_name", "胡桃笔记预览版")
            resValue("string", "corner_desc", "PREVIEW")
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    applicationVariants.all {
        outputs.all {
            if (this is BaseVariantOutputImpl) {
                var abiName = getFilter(FilterConfiguration.FilterType.ABI.name)
                if (abiName.isNullOrBlank()) abiName = "universal"
                SimpleDateFormat("MMDDHHmm").format(Date())
                val fileName =
                    "胡桃笔记${SimpleDateFormat("MMDDHHmm").format(Date())}_${buildType.name}_${abiName}.apk"
                outputFileName = fileName
            }
        }
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.arr"))))
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(projects.core.network)
    implementation(libs.google.material)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedataKtx)
    implementation(libs.androidx.lifecycle.viewmodelKtx)
    implementation(libs.androidx.lifecycle.runtimeKtx)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.javax.inject)
    implementation(libs.coil.kt)
    // 项目三方依赖
    implementation(libs.timber)
    implementation(libs.toaster)
    implementation(libs.immersionbar)
    implementation(libs.immersionbar.ktx)

    //--------------调试相关包--------------------------------//
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
}