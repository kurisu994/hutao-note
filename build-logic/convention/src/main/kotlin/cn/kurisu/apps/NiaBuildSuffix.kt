package cn.kurisu.apps

/**
 * This is shared between :app module to provide configurations type safety.
 */
enum class NiaBuildSuffix(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    PREVIEW(".preview"),
    RELEASE
}
