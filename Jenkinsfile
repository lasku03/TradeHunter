def PowerShell(scriptName) {
    def psCmd = "pwsh -File ${scriptName}"
    sh psCmd
}

pipeline {
    agent any
    stages {
        stage('Build REST') {
            steps {
                PowerShell('./Jenkins_Build_REST.ps1')
            }
        }
        stage('Test REST') {
            steps {
                PowerShell('./Jenkins_Test_REST.ps1')
            }
        }
        stage('Sonarqube REST') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'chmod 777 Jenkins_Sonarqube_Rest.sh'
                        sh './Jenkins_Sonarqube_Rest.sh'
                    } else {
                        bat 'call Jenkins_Sonarqube_Rest.bat'
                    }
                }
            }
        }
        stage('Publish') {
            steps {
                script {
                    if (env.BRANCH_NAME.equals('main')) {
                        PowerShell('./Jenkins_Publish_REST.ps1')
                    }
                }
            }
        }
        stage('Sonarqube AI') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'chmod 777 Jenkins_Sonarqube_AI.sh'
                        sh './Jenkins_Sonarqube_AI.sh'
                    } else {
                        bat 'call Jenkins_Sonarqube_AI.bat'
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
