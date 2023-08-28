import org.jetbrains.kotlin.konan.properties.hasProperty

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("com.android.library") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    id("org.jetbrains.kotlinx.kover") version "0.7.3" apply false
    id("io.gitlab.arturbosch.detekt") version ("1.23.1") apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
    id("com.google.firebase.crashlytics") version "2.9.8" apply false
    id("com.google.firebase.appdistribution") version "4.0.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1" apply false
}

allprojects {
    apply {
        plugin("org.jetbrains.kotlinx.kover")
        plugin("io.gitlab.arturbosch.detekt")
        plugin("org.jlleitschuh.gradle.ktlint")
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
