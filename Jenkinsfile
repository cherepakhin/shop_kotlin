
pipeline {

    agent any
    options {
        durabilityHint 'MAX_SURVIVABILITY'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Unit tests') {
            steps {
                sh 'pwd;./gradlew clean test --tests *Test'
            }
        }

        stage('Integration tests') {
            steps {
                sh 'pwd;./gradlew clean test --tests *TestIntegration'
            }
        }

        stage('Build bootJar') {
            steps {
                sh 'pwd;./gradlew -version;./gradlew bootJar'
            }
        }

        stage('Publish to Nexus') {
            environment {
                NEXUS_CRED = credentials('nexus_admin')
            }
            steps {
                sh 'pwd;./gradlew bootJar publish'
            }
        }
    }
}