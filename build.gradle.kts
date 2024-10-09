plugins {
    java
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.sonarqube") version "4.4.1.3373"
    id("jacoco")
    id("org.openrewrite.rewrite") version "latest.integration"
}

group = "com.spribe"
version = "0.0.1-SNAPSHOT"

object Versions {
    const val MAPSTRUCT = "1.5.5.Final"
    const val MAPSTRUCT_LOMBOK_BINDING = "0.2.0"
    const val HYPERSISTENCE_UTILS = "3.8.2"
    const val HSQLDB = "2.7.3"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
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
    testImplementation("org.hsqldb:hsqldb:${Versions.HSQLDB}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    rewrite("org.openrewrite.recipe:rewrite-migrate-java:2.26.0")
    rewrite("org.openrewrite.recipe:rewrite-spring:5.20.0")

}

rewrite {
    // Reformats Java Code
    activeRecipe("org.openrewrite.java.format.AutoFormat")

    activeRecipe("org.openrewrite.java.spring.boot3.UpgradeSpringBoot_3_3")
    activeRecipe("org.openrewrite.java.migrate.UpgradeToJava21")

    activeRecipe("org.openrewrite.java.spring.boot3.SpringBoot3BestPractices")
    activeRecipe("org.openrewrite.java.testing.junit5.JUnit5BestPractices")

    activeRecipe("org.openrewrite.java.testing.mockito.MockitoBestPractices")
    activeRecipe("org.openrewrite.java.testing.cleanup.BestPractices")

    activeRecipe("org.openrewrite.maven.BestPractices")

    activeRecipe("org.openrewrite.staticanalysis.JavaApiBestPractices")
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs("-XX:+EnableDynamicAgentLoading")
}