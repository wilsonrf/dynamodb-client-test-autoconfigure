plugins {
    id("base")
    id("java-library")
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
    id("com.github.ben-manes.versions")
    id("maven-publish")
}

group = "com.wilsonfranca"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

val optional = configurations.create("optional") {
    isCanBeConsumed = false
    isCanBeResolved = true
}

val awsSdkVersion: String by project

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        mavenBom("software.amazon.awssdk:bom:$awsSdkVersion")
    }
}
dependencies {
    implementation("com.wilsonfranca:dynamodb-client-autoconfigure:1.0.0-SNAPSHOT")
    implementation("org.testcontainers:testcontainers")
    add("optional", "org.testcontainers:testcontainers")
    implementation("org.springframework.boot:spring-boot-testcontainers")
    add("optional", "org.springframework.boot:spring-boot-testcontainers")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-test-autoconfigure")
    add("optional", "org.springframework.boot:spring-boot-test-autoconfigure")
    implementation("org.springframework:spring-test")
    add("optional", "org.springframework:spring-test")
    api("software.amazon.awssdk:dynamodb")
    add("optional", "software.amazon.awssdk:dynamodb")
    api( "software.amazon.awssdk:dynamodb-enhanced")
    add("optional", "software.amazon.awssdk:dynamodb-enhanced")
    implementation("org.junit.jupiter:junit-jupiter-api")
    add("optional", "org.junit.jupiter:junit-jupiter-api")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}