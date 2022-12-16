import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("com.squareup.sqldelight")
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

group = "io.github.sovathna"
version = "1.0-SNAPSHOT"

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("com.google.code.gson:gson:2.10")
//                implementation("androidx.paging:paging-compose:1.0.0-alpha15")
    implementation("com.squareup.sqldelight:sqlite-driver:1.5.4")
//                implementation ("com.squareup.sqldelight:coroutines-extensions-jvm:1.5.3")
    implementation("io.insert-koin:koin-core:3.3.0")

//    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.4")
}


compose.desktop {
    application {

        javaHome = System.getenv("JDK_17")
        mainClass = "io.github.sovathna.MainKt"

        buildTypes.release {
            proguard {
                configurationFiles.from(project.file("proguard-rules.pro"))
            }
        }

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Rpm, TargetFormat.Deb)
            packageName = "io.github.sovathna.KhmerDictionary"
            packageVersion = "1.0.0"
            description = "Khmer Dictionary"
            copyright = "© 2022 Sovathna Hong. All rights reserved."
            licenseFile.set(project.file("LICENSE"))
            vendor = "Sovathna Hong"
            modules("java.instrument", "java.sql", "jdk.unsupported")

            windows {
                packageName = "Khmer Dictionary"
                dirChooser = true
                installationPath = System.getenv("LOCALAPPDATA")
            }

            linux {
                packageName = "khmer-dictionary"
                appRelease = "1"
            }
        }
    }
}

sqldelight {
    database("WordsDatabase") {
        packageName = "io.github.sovathna.domain.wordsdb"
        sourceFolders = listOf("wordsdb")
        dialect = "sqlite:3.25"
        //verifyMigrations = true
    }

    database("LocalDatabase") {
        packageName = "io.github.sovathna.domain.localdb"

        // An array of folders where the plugin will read your '.sq' and '.sqm'
        // files. The folders are relative to the existing source set so if you
        // specify ["db"], the plugin will look into 'src/main/db' or 'src/commonMain/db' for KMM.
        // Defaults to ["sqldelight"]
        sourceFolders = listOf("localdb")
        dialect = "sqlite:3.25"
        //verifyMigrations = true
    }
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.guardsquare:proguard-gradle:7.2.0")
    }
}