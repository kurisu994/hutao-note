import cn.kurisu.apps.libs
import cn.kurisu.apps.mpaas_artifact
import cn.kurisu.apps.mpaas_baseline
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude

/**
 * mpaas插件
 *
 * @author : kurisu
 * @date : 2024-01-30 14:25
 * @version : 1.0
 *
 */
class AndroidMpaasConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.alipay.apollo.baseline.config")
            }

            extensions.configure<ApplicationExtension> {
                configurations.apply {
                    all {
                        exclude("com.mpaas.group.amap", "amap-build")
                        exclude("com.alipay.android.phone.thirdparty", "amap3dmap-build")
                    }
                }
            }
            dependencies {
                add(
                    "implementation",
                    platform("com.mpaas.android:${mpaas_artifact}:${mpaas_baseline}")
                )
                add("implementation", libs.findLibrary("mpaas.mriver").get())
                add("implementation", libs.findLibrary("mpaas.ktx").get())
                add("implementation", libs.findLibrary("mpaas.uccore").get())
                add("implementation", libs.findLibrary("mpaas.framework").get())
                add("implementation", libs.findLibrary("mpaas.tinyapp").get())
                add("implementation", libs.findLibrary("mpaas.scan").get())
                add("implementation", libs.findLibrary("mpaas.scanai").get())
                add("implementation", libs.findLibrary("mpaas.utdid").get())

                add(
                    "implementation",
                    "com.alipay.android.phone.wallet:nebulaucsdk-build:999.3.22.2.66.230817192043@aar"
                )
            }
        }
    }
}