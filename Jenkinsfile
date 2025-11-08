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
                stage('Run WebApp') {
            steps {
                sh '''
                nohup java -jar target/*.jar > app.log 2>&1 &
                for i in {1..30}; do
                    if curl -s http://localhost:8086 > /dev/null; then
                        echo "Application is up!"
                        exit 0
                    fi
                    echo "Waiting app to be ready..."
                    sleep 2
                done
                echo "Application failed to start!"
                exit 1
                '''
            }
        }

        stage('DAST - ZAP Baseline') {
            steps {
                sh '''
                docker run --rm -t ghcr.io/zaproxy/zaproxy:stable \
                zap-baseline.py -t http://localhost:8080 -m 3 -r zap_report.html
                '''
            }
            post {
                always {
                    archiveArtifacts artifacts: 'zap_report.html'
                }
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
