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
                script {
                    if (isUnix()) {
                        sh 'chmod 777 Jenkins_Sonarqube.sh'
                        sh './Jenkins_Sonarqube.sh'
                    } else {
                        bat 'call Jenkins_Sonarqube.bat'
                    }
                }
            }
        }
        stage('Publish') {
            steps {
                script {
                    if (env.BRANCH_NAME.equals('develop')) {
                        def jarFile = PowerShell('./Jenkins_Publish.ps1')
                        if (isUnix()) {
                            sh "chmod 777 ${jarFile}"
                        }
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
