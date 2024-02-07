package cn.kurisu.apps

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")


val minVersion
    get(): Int = 24
val compileVersion
    get(): Int = 34
val buildVersion
    get(): String = "34.0.0"

val ndkVer
    get(): String = "24.0.8215888"

val mpaas_baseline
    get(): String = "10.2.3-42"

val mpaas_artifact
    get(): String = "mpaas-baseline"