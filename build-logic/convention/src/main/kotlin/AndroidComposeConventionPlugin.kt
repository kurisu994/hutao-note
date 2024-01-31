import cn.kurisu.apps.libs
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {

            extensions.configure<ApplicationExtension> {
                buildFeatures.compose = true
                composeOptions {
                    kotlinCompilerExtensionVersion = libs.findVersion("androidxComposeCompiler").get().toString()
                }
            }

            dependencies {
                val bom = libs.findLibrary("androidx-compose-bom").get()
                add("implementation", platform(bom))
                add("implementation", libs.findLibrary("androidx.compose.ui").get())
                add("implementation", libs.findLibrary("androidx.compose.ui.graphics").get())
                add("implementation", libs.findLibrary("androidx.activity.compose").get())
                add("implementation", libs.findLibrary("androidx.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.compose.material3").get())
                add("implementation", libs.findLibrary("androidx-compose-material-iconsExtended").get())
                add("implementation", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
                add("implementation", libs.findLibrary("androidx-compose-runtime").get())
                add("implementation", libs.findLibrary("coil.kt.compose").get())

                add("androidTestImplementation",platform(bom))
                add("androidTestImplementation", libs.findLibrary("androidx.compose.ui.test").get())
                add("debugImplementation", libs.findLibrary("androidx.compose.ui.tooling").get())
                add("debugImplementation", libs.findLibrary("androidx.compose.ui.testManifest").get())

            }
        }
    }
}