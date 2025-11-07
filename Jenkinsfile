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
          sudo docker run --rm -v $PWD:/src returntocorp/semgrep \
            semgrep --config=p/owasp-top-ten /src > semgrep-report.json
        '''
        archiveArtifacts artifacts: 'semgrep-report.json'
      }
    }
        
    stage('SpotBugs Analysis') {
    steps {
        sh '''
            mvn clean compile spotbugs:check 
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
    post {
    always {
      mail to: 'ton-email@example.com',
           subject: "Build #${env.BUILD_NUMBER} - Résultat ${currentBuild.currentResult}",
           body: "Le rapport est attaché.",
           attachmentsPattern: 'semgrep-report.json'
    }
}
