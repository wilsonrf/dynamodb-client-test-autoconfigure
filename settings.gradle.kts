rootProject.name = "dynamodb-client-test-autoconfigure"

pluginManagement {
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val dependencyManagementVersion: String by settings
    val owaspDependencyCheckVersion: String by settings

    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
        id("com.github.ben-manes.versions") version dependencyManagementVersion
        id("org.owasp.dependencycheck") version owaspDependencyCheckVersion
        kotlin("jvm") version "1.9.23"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}