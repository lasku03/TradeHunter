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
        stage('Publish') {
            steps {
                script {
                    if (env.BRANCH_NAME.equals('develop')) {
                        PowerShell('./Jenkins_Publish.ps1')
                    }
                }
            }
        }
    }

    post {
        always {
            deleteDir()
        }
    }
}
