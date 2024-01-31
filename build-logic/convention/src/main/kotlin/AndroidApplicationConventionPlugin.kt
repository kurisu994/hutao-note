import cn.kurisu.apps.buildVersion
import cn.kurisu.apps.compileVersion
import cn.kurisu.apps.configureKotlinAndroid
import cn.kurisu.apps.ndkVer
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * application插件
 *
 * @author : kurisu
 * @date : 2024-01-30 14:22
 * @version : 1.0
 *
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                buildToolsVersion = buildVersion
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = compileVersion

                defaultConfig.ndk {
                    abiFilters.clear()
                    abiFilters.add("armeabi-v7a")
                    abiFilters.add("arm64-v8a")
                }


                buildFeatures {
                    buildConfig = true
                    viewBinding = true
                    renderScript = true
                    aidl = true
                }

                splits {
                    abi {
                        isEnable = true
                        reset()
                        include("armeabi-v7a", "arm64-v8a")
                        isUniversalApk = true
                    }
                }
            }
        }
    }

}
