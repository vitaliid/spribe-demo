plugins {
    java
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.sonarqube") version "4.4.1.3373"
    id("jacoco")
}

group = "com.spribe"
version = "0.0.1-SNAPSHOT"

object Versions {
    const val MAPSTRUCT = "1.5.5.Final"
    const val MAPSTRUCT_LOMBOK_BINDING = "0.2.0"
    const val HYPERSISTENCE_UTILS = "3.8.2"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.bootJar {
    archiveBaseName.set("com.spribe.demo")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.liquibase:liquibase-core")

    implementation("org.projectlombok:lombok-mapstruct-binding:${Versions.MAPSTRUCT_LOMBOK_BINDING}")
    implementation("org.mapstruct:mapstruct:${Versions.MAPSTRUCT}")

    implementation("io.hypersistence:hypersistence-utils-hibernate-63:${Versions.HYPERSISTENCE_UTILS}")

    compileOnly("org.projectlombok:lombok")

    runtimeOnly("org.postgresql:postgresql")
    
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:${Versions.MAPSTRUCT}")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
