pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                bat 'powershell.exe -File .\\Jenkins_Build.ps1'
            }
        }
        stage('Test') {
            steps {
                bat 'powershell.exe -File .\\Jenkins_Test.ps1'
            }
        }
    }
}