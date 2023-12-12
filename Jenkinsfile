
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
                sh './gradlew clean test --tests *Test'
            }
        }

        stage('Integration tests') {
            steps {
                sh './gradlew clean test --tests *TestIntegration'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }

        stage('Publish to Nexus') {
            environment {
                NEXUS_CRED = credentials('nexus_admin')
            }
            steps {
                sh './gradlew publish'
            }
        }
    }
}