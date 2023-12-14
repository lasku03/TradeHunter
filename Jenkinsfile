pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh '''
                    pwsh -File ./Jenkins_Build.ps1
                '''
            }
        }
        stage('Test') {
            steps {
                sh '''
                    pwsh -File ./Jenkins_Test.ps1
                '''
            }
        }
    }
}
