plugins {
    id("com.android.application") version "7.4.0" apply false
    id("com.android.library") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.22.0")
}

detekt {
    toolVersion = "1.22.0"
    source = files(getSrcDirectors())
    config = files("${projectDir}/detekt/detekt.yml")
    reportsDir = file("${projectDir}/build/reports/detekt/")
}

fun getSrcDirectors() = projectDir.walk().filter { file ->
    file.isDirectory && file.absolutePath.endsWith("src")
}.toList()
