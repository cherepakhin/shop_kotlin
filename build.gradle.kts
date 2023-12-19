import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "ru.perm.v"
// change to publishing on change version
version = "0.1.24"
description = "shop kotlin description"

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

repositories {
    mavenCentral()
    mavenLocal()
    maven {

//      ERR  url = uri("http://192.168.1.20:8082/repository/ru.perm.v/ru/perm/v/shop_kotlin_extdto/0.0.2/shop_kotlin_extdto-0.0.2.jar")
//      ERR  url = uri("http://192.168.1.20:8082/ru.perm.v/ru/perm/v/shop_kotlin_extdto/0.0.2/shop_kotlin_extdto-0.0.2.jar")
//      ERR  url = uri("http://192.168.1.20:8082/ru/perm/v/shop_kotlin_extdto/0.0.2/shop_kotlin_extdto-0.0.2.jar")

        url = uri("http://192.168.1.20:8082/repository/ru.perm.v") //OK
        isAllowInsecureProtocol = true
        credentials {
            username = "admin"
            password = "pass"

//            username = System.getenv("NEXUS_CI_USER") ?: extra.properties["nexus-ci-username"] as String?
//            password = System.getenv("NEXUS_CI_PASS") ?: extra.properties["nexus-ci-password"] as String?
        }
    }
}

plugins {
    val kotlinVersion = "1.8.21"
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

// external dependency from private repository
    implementation("ru.perm.v:shop_kotlin_extdto:0.0.2")
//    implementation("ru.perm.v:shop_kotlin_extdto:0.0.2.jar") // jar! ???
//    implementation("ru/perm/v/shop_kotlin_extdto/0.0.2/shop_kotlin_extdto-0.0.2.jar")

    api("com.querydsl:querydsl-apt:5.0.0:jpa")
    kapt("jakarta.annotation:jakarta.annotation-api")

    testImplementation ("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.mockito", "mockito-core")
    }
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
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

//tasks.test{
//    filter{
//////skip classes test with name ends IntegrationTest
//        excludeTestsMatching("**/*IntegrationTest")
//    }
//
//}

// WORKED!
//tasks.withType<Test> {
//    exclude("**/*IntegrationTest**")
//}

tasks.withType<Test> {
    useJUnitPlatform()
    // Show test log
    testLogging {
//        events("standardOut", "started", "passed", "skipped", "failed")
        events("passed", "skipped", "failed")
    }
//    if (project.hasProperty('excludeTests')) {
//        exclude project.property('excludeTests')
//    }
    filter {
        exclude("*IntegrationTest*")
    }

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

tasks.register("myTask1") {
    println("echo from myTask1. For run use: ./gradlew myTask1")
}

tasks.register("myTask2") {
    println("echo from myTask2. For run use: ./gradlew myTask2")
}

tasks.register("helloUserCmd") {
    val user: String? = System.getenv("USER")
    project.exec {
        commandLine("echo", "Hello,", "$user!") // ./gradlew helloUserCmd -> run shell command
    }
}
