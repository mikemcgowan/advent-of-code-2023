import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.adarshr.test-logger") version "4.0.0"
    id("org.sonarqube") version "4.4.1.3373"
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20"
    jacoco
}

group = "com.github.mikemcgowan"
version = "1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

extra["springShellVersion"] = "3.2.0-RC1"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.shell:spring-shell-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.shell:spring-shell-dependencies:${property("springShellVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}

jacoco {
    toolVersion = "0.8.9"
}

tasks.named("jacocoTestReport").get().dependsOn("test")
tasks.named("sonar").get().dependsOn("jacocoTestReport")
