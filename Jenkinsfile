pipeline {
    agent any

    tools {
        jdk 'JDK 21 Temurin'
        gradle 'Gradle 8.10'
    }

    triggers {
        githubPush()
    }

    stages {
        stage('Build with Gradle') {
            steps {
                sh 'gradle build -x test'
            }
        }

        stage('Test with Gradle') {
            steps {
                sh 'gradle test'
            }
        }
    }
}