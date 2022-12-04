import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("reflect"))
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    test {
        useJUnitPlatform()
        testLogging.showExceptions = true
    }
}
