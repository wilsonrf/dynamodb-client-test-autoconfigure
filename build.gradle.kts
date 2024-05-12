plugins {
    id("base")
    id("java-library")
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
    id("com.github.ben-manes.versions")
    id("signing")
    id("maven-publish")
}

group = "com.wilsonfranca"

repositories {
    mavenLocal()
    mavenCentral()
}

val awsSdkVersion: String by project

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        mavenBom("software.amazon.awssdk:bom:$awsSdkVersion")
    }
}
dependencies {
    api("com.wilsonfranca:dynamodb-client-autoconfigure:1.0.0-SNAPSHOT")
    api("org.testcontainers:testcontainers")
    api("org.springframework.boot:spring-boot-testcontainers")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-test-autoconfigure")
    implementation("org.springframework:spring-test")
    implementation("software.amazon.awssdk:dynamodb")
    implementation( "software.amazon.awssdk:dynamodb-enhanced")
    implementation("org.junit.jupiter:junit-jupiter-api")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
        }
    }
}