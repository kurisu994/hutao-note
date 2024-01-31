pluginManagement {
    includeBuild("build-logic")
    repositories {
        maven(uri("https://maven.aliyun.com/repository/public"))
        maven(uri("https://maven.aliyun.com/repository/google"))
        maven(uri("https:/https://repo.huaweicloud.com/repository/maven"))
        maven {
            url = uri("https://mvn.cloud.alipay.com/nexus/content/repositories/open/")
            name = "alipay"
        }
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven(uri("https://maven.aliyun.com/repository/public"))
        maven(uri("https://maven.aliyun.com/repository/google"))
        maven(uri("https:/https://repo.huaweicloud.com/repository/maven"))
        maven(uri("https://jitpack.io"))
        maven {
            url = uri("https://mvn.cloud.alipay.com/nexus/content/repositories/open/")
            name = "alipay"
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "HutaoNote"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:network")
include(":core:common")
include(":core:model")
