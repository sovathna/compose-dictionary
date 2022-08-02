import org.jetbrains.compose.compose
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
    implementation("com.google.code.gson:gson:2.9.0")
//                implementation("androidx.paging:paging-compose:1.0.0-alpha15")
    implementation("com.squareup.sqldelight:sqlite-driver:1.5.3")
//                implementation ("com.squareup.sqldelight:coroutines-extensions-jvm:1.5.3")
    implementation("io.insert-koin:koin-core:3.2.0")

//    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
}


compose.desktop {
    application {
        javaHome = System.getenv("JDK_18")
        mainClass = "io.github.sovathna.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "io.github.sovathna.KhmerDictionary"
            packageVersion = "1.0.0"
            description = "Khmer Dictionary"
            copyright = "© 2022 Sovathna Hong. All rights reserved."
            modules("java.instrument", "java.sql", "jdk.unsupported")
            windows {
                packageName = "Khmer Dictionary"
            }
        }
    }
}

sqldelight {
    database("Database") {
        dialect = "sqlite:3.25"
        packageName = "io.github.sovathna.domain.local"
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

// Define task to obfuscate the JAR and output to <name>.min.jar

tasks.register<proguard.gradle.ProGuardTask>("obfuscate") {
    val packageUberJarForCurrentOS by tasks.getting
    dependsOn(packageUberJarForCurrentOS)
    val files = packageUberJarForCurrentOS.outputs.files
    injars(files)
    outjars(files.map { file -> File(file.parentFile, "${file.nameWithoutExtension}.min.jar") })

    val library = if (System.getProperty("java.version").startsWith("1.")) "lib/rt.jar" else "jmods"
    libraryjars("${System.getProperty("java.home")}/$library")

    configuration("proguard-rules.pro")
}