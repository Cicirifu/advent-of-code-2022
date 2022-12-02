import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

(1 .. 2).forEach {
    val day = it.toString().padStart(2, '0')
    task("runDay${day}", type = JavaExec::class) {
        group = ApplicationPlugin.APPLICATION_GROUP
        description = "Run the script for puzzle day ${day}."
        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("Launcher")
        args = listOf("Day${day}")
    }
}
