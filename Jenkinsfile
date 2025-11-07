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

        stage('Semgrep SAST') {
            steps {
                sh '''
                echo "Running Semgrep…"
                docker run --rm -v $PWD:/src returntocorp/semgrep \
                    semgrep --config=p/owasp-top-ten /src > semgrep-report.json
                '''
                archiveArtifacts artifacts: 'semgrep-report.json'
            }
        }

        stage('SpotBugs Analysis') {
            steps {
                sh 'mvn clean compile spotbugs:check || true'
            }
        }

        stage('Build + Test') {
            steps {
                sh 'mvn clean verify'
            }
        }
        stage('OWASP Dependency-Check Vulnerabilities') {
          steps {
            dependencyCheck additionalArguments: '''
                --scan "./target"
                --enableExperimental
                -f "ALL"
                --prettyPrint
            ''', odcInstallation: 'DP-Check'
            dependencyCheckPublisher pattern: 'dependency-check-report.xml'
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
                    waitForQualityGate abortPipeline: false
                }
            }
        }
    }

    post {
        always {
            emailext(
              to: "yessinebentaher00@gmail.com",
              subject: "Pipeline Result #${env.BUILD_NUMBER}",
              body: "Here's Semgrep attached",
              attachmentsPattern: '**/semgrep-report.json',
              attachLog: true
            )
        }
    }
}
