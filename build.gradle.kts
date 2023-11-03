import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "ru.perm.v"
// change to publishing on change version
version = "0.1.12"
description = "shop_kotlin"

java.sourceCompatibility = JavaVersion.VERSION_11

configurations.create("querydsl")

buildscript {
    var kotlinVersion: String? by extra; kotlinVersion = "1.1.51"
//    var querydslVersion: String? by extra; querydslVersion = "4.4.0"

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

plugins {
    val kotlinVersion = "1.8.0"
    id("org.springframework.boot") version "2.5.6"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("maven-publish")
    id("io.qameta.allure") version "2.8.1"
    id("jacoco")
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("kapt") version "1.7.0"
    idea
//    kotlin("plugin.allopen")
//    kotlin("plugin.noarg")
//	id 'io.qameta.allure' version '2.11.2' // version 2.10.0 WORK! NO WARNINGS for generate allure report
}

apply(plugin = "io.spring.dependency-management")
apply(plugin = "kotlin-kapt")

repositories {
    mavenCentral()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
    gradlePluginPortal()
}

java.sourceSets["main"].java {
    srcDir("build/classes/java/main")
}

dependencies {
//    api("com.querydsl:querydsl-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-cache")
// https://mvnrepository.com/artifact/io.springfox/springfox-boot-starter
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.h2database:h2")
// validator
    implementation("org.hibernate.validator:hibernate-validator")

// https://mvnrepository.com/artifact/com.querydsl/querydsl-apt
    implementation("com.querydsl:querydsl-core:5.0.0")
    implementation("com.querydsl:querydsl-jpa:5.0.0")

// spring-boot-starter-actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")
// prometheus - metrics
    implementation("io.micrometer:micrometer-registry-prometheus")

    api("com.querydsl:querydsl-apt:5.0.0:jpa")
    kapt("jakarta.annotation:jakarta.annotation-api")

    testImplementation ("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.mockito", "mockito-core")
    }
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
    compileOnly("org.projectlombok:lombok:1.18.28")
    compileOnly("org.springframework.boot:spring-boot-starter-actuator")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

kapt {
    javacOptions {
        option("querydsl.entityAccessors", true)
    }
    arguments {
        arg("plugin", "com.querydsl.apt.jpa.JPAAnnotationProcessor")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

//// remove suffix 'plain' in sonar repository
tasks.jar {
    enabled = true
    // Remove `plain` postfix from jar file name
    archiveClassifier.set("")
}

publishing {
    repositories {
        maven {
            url = uri("http://v.perm.ru:8082/repository/ru.perm.v/")
            isAllowInsecureProtocol = true
            //  publish в nexus "./gradlew publish" из ноута и Jenkins проходит
            // export NEXUS_CRED_USR=admin
            // echo $NEXUS_CRED_USR
            credentials {
                username = System.getenv("NEXUS_CRED_USR")
                password = System.getenv("NEXUS_CRED_PSW")
            }
        }
    }
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
        }
    }
}

springBoot {
    mainClass.set("ru.perm.v.shopkotlin.ShopKotlinApplication")
}