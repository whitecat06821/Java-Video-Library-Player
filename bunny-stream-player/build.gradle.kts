plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    // id("io.gitlab.arturbosch.detekt") // Removed for build compatibility
}

android {
    namespace = "net.bunnystream.player"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
}
// ... existing dependencies ... 