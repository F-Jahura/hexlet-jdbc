plugins {
    application
    id("com.github.ben-manes.versions") version "0.52.0"
    id("io.freefair.lombok") version "8.4"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    // Входная точка
    mainClass.set("hexlet.code.App")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("org.projectlombok:lombok:1.18.22")
    implementation("com.h2database:h2:2.2.220")
}

tasks.test {
    useJUnitPlatform()
}