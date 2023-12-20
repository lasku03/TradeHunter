def PowerShell(scriptName) {
    def psCmd = "pwsh -File ${scriptName}"
    sh psCmd
}

pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                PowerShell('./Jenkins_Build.ps1')
            }
        }
        stage('Test') {
            steps {
                PowerShell('./Jenkins_Test.ps1')
            }
        }
        stage('Sonarqube') {
            steps {
                bat 'call Jenkins_Sonarqube.bat'
            }
        }
    }

    post {
        always {
            deleteDir()
        }
    }
}
