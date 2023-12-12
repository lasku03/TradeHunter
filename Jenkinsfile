pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                PowerShell('.//Jenkins_Build.ps1')
            }
        }
        stage('Test') {
            steps {
                //
            }
        }
        stage('Sonarqube') {
            steps {
                //
            }
        }
    }
}