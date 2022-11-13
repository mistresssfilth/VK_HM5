plugins {
    id("java")
}

group = "ru.vk.mistressfilth"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    compileOnly("org.projectlombok:lombok:1.18.24")

    implementation("org.flywaydb:flyway-core:9.6.0")
    implementation("org.postgresql:postgresql:42.5.0")
    implementation("org.jetbrains:annotations:23.0.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")


}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}