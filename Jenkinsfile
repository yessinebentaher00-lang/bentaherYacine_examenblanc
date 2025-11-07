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
    stage('Debug') {
          steps {
            sh '''
            echo "0000" | sudo -S docker run --rm -v $PWD:/src returntocorp/semgrep \
            semgrep --config=p/owasp-top-ten /src
            '''
        }
    }

        stage('Semgrep SAST') {
            steps {
                sh '''
                    echo "Running Semgrep for Java OWASP Top 10 vulnerabilities..."
                    docker run --rm -v $PWD:/src returntocorp/semgrep semgrep --config=p/java/owasp-top-ten /src
                '''
            }
        }

        stage('Build + Test') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('Sonar Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    sh "mvn sonar:sonar -Dsonar.projectKey=devops_java -Dsonar.host.url=http://192.168.50.4:9000 -Dsonar.login=${SONAR_TOKEN}"
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}
