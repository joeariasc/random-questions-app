import org.jetbrains.kotlin.konan.properties.hasProperty

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.kotlin) apply false
    alias(libs.plugins.google.ksp) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.google.firebase.crashlytics) apply false
    alias(libs.plugins.google.firebase.appdistribution) apply false
    alias(libs.plugins.kotlinx.kover) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ktlint) apply false
}

allprojects {
    apply {
        plugin(rootProject.libs.plugins.kotlinx.kover.get().pluginId)
        plugin(rootProject.libs.plugins.detekt.get().pluginId)
        plugin(rootProject.libs.plugins.ktlint.get().pluginId)
    }
}

val projectSource = file(projectDir)
val kotlinFiles = "**/*.kt"
val resourceFiles = "**/resources/**"
val buildFiles = "**/build/**"

tasks.register("detektAll", io.gitlab.arturbosch.detekt.Detekt::class) {
    config.setFrom(file("$rootDir/config/detekt/detekt.yml"))
    basePath = projectSource.path
    description = "Custom DETEKT build for all modules"
    parallel = false
    ignoreFailures = true
    autoCorrect = false
    buildUponDefaultConfig = true
    setSource(projectSource)
    include(kotlinFiles)
    exclude(resourceFiles, buildFiles)
    reports {
        sarif.required.set(true)
        html.required.set(true)
        xml.required.set(false)
        txt.required.set(false)
    }
}

private val localProperties =
    com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)

val keystoreFilePath: String by extra {
    if (localProperties.hasProperty("keystoreFilePath")) {
        localProperties.getProperty("keystoreFilePath")
    } else {
        System.getenv("KEYSTORE_PATH")
    }
}

val keystorePassword: String by extra {
    if (localProperties.hasProperty("keystorePassword")) {
        localProperties.getProperty("keystorePassword")
    } else {
        System.getenv("KEYSTORE_PASSWORD")
    }
}

val keyAlias: String by extra {
    if (localProperties.hasProperty("keyAlias")) {
        localProperties.getProperty("keyAlias")
    } else {
        System.getenv("KEY_ALIAS")
    }
}

val keyPassword: String by extra {
    if (localProperties.hasProperty("keyPassword")) {
        localProperties.getProperty("keyPassword")
    } else {
        System.getenv("KEY_PASSWORD")
    }
}

val firebaseAppId: String by extra {
    if (localProperties.hasProperty("firebaseAppId")) {
        localProperties.getProperty("firebaseAppId")
    } else {
        System.getenv("FIREBASE_APP_ID")
    }
}

val serviceCredentialsFilePath: String by extra {
    if (localProperties.hasProperty("firebaseAppId")) {
        localProperties.getProperty("firebaseAppId")
    } else {
        System.getenv("SERVICE_CREDENTIALS_PATH")
    }
}
