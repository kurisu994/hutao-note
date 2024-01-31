import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "cn.kurisu.apps.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    implementation(libs.truth)
}

tasks {
    validatePlugins {
        enableStricterValidation.set(true)
        failOnWarning.set(true)
    }
}


gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "kurisu.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "kurisu.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "kurisu.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "kurisu.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidMpaas") {
            id = "kurisu.android.mpaas"
            implementationClass = "AndroidMpaasConventionPlugin"
        }
    }
}
