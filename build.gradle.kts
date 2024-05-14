/*
* Copyright 2024 Wilson da Rocha França
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
 */
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
    if (isRunningInCI()) {
        maven {
            url = uri("https://maven.pkg.github.com/wilsonrf/dynamodb-client-autoconfigure")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GPR_USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GPR_TOKEN")
            }
        }
    }
}

val awsSdkVersion: String by project

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        mavenBom("software.amazon.awssdk:bom:$awsSdkVersion")
    }
}

dependencies {
    api("com.wilsonfranca:dynamodb-client-autoconfigure:1.0.0")
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
    testLogging { exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL }
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

tasks.register<Jar>("javadocJar") {
    dependsOn("javadoc")
    archiveClassifier.set("javadoc")
    from(tasks["javadoc"].outputs)
}

val zipArtifacts by tasks.registering(Zip::class) {
    dependsOn("publishMavenJavaPublicationToInternalRepoRepository")
    from("${layout.buildDirectory.get()}/repo") {
        exclude("**/maven-metadata*.*")
    }
    archiveFileName.set("${project.name}-${version}.zip")
    destinationDirectory.set(file("${layout.buildDirectory.get()}/outputs"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }

            pom {
                name = "DynamoDB Client Test Autoconfigure"
                description = "DynamoDB Client Test Autoconfigure"
                url = "https://github.com/wilsonrf/dynamodb-client-test-autoconfigure"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "wilsonrf"
                        name = "Wilson da Rocha França"
                        email = "wilsonrf@gmail.com"
                    }
                }
                scm {
                    connection = "scm:git:git@github.com:wilsonrf/dynamodb-client-test-autoconfigure.git"
                    developerConnection = "scm:git:git@github.com:wilsonrf/dynamodb-client-test-autoconfigure.git"
                    url = "https://github.com/wilsonrf/dynamodb-client-test-autoconfigure"
                }
            }
        }

        repositories {

            maven {
                name = "internalRepo"
                url = uri("${layout.buildDirectory.get()}/repo")
            }

            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/wilsonrf/dynamodb-client-autoconfigure")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}

signing {
    setRequired {
        gradle.taskGraph.allTasks.any { it is PublishToMavenLocal }.not()
    }

    val signingKey: String? by project
    val signingKeyId: String? by project
    val signingPassword: String? by project

    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)

    sign(publishing.publications["mavenJava"])
}

private fun isRunningInCI(): Boolean = System.getenv("CI") == "true"
