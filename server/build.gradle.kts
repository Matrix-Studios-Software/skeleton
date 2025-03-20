val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    id("io.ktor.plugin") version "2.3.8"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
    application
}

dependencies {
    // ktor libraries
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-default-headers-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")

    // docker
    implementation("com.github.docker-java:docker-java-transport-okhttp:3.3.4")

    // gson
    implementation("com.google.code.gson:gson:2.10.1")

    // databases
    implementation("redis.clients:jedis:2.8.0")

    // amber
    implementation("com.github.98ping:amber:0736407e62")
    implementation("com.github.Carleslc.Simple-YAML:Simple-Yaml:1.8.4")

    // tests
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

}

application {
    mainClass.set("ltd.matrixstudios.skeleton.SkeletonServer")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
