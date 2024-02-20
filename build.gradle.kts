plugins {
    kotlin("jvm") version "1.9.22"
}

allprojects {
    group = "ltd.matrixstudios"
    version = "0.0.1"

    repositories {
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://oss.sonatype.org/content/repositories/central")
        maven("https://jitpack.io")

        maven {
            name = "matrixStudiosMavenPublic"
            url = uri("https://maven.matrixstudios.ltd/public")
        }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
}
