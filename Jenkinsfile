pipeline {
    agent any

    tools {
        maven 'M2_HOME'
        jdk 'JAVA_HOME'
    }

    environment {
        SONAR_TOKEN = credentials('lunarCube')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/yessinebentaher00-lang/bentaherYacine_examenblanc'
            }
        }

        stage('Build + Test') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('Sonar Analysis') {
            steps {
                withSonarQubeEnv('LunarCubeServer') {
                    sh "mvn clean verify sonar:sonar -Dsonar.projectKey=devops_java"
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}
