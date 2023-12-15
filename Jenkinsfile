def PowerShell(scriptName) {
    def psCmd = "pwsh -File ${scriptName}"
    sh psCmd
}

pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                PowerShell("./Jenkins_Build.ps1")
            }
        }
        stage('Test') {
            steps {
                PowerShell("./Jenkins_Test.ps1")
            }
        }
    }

    post {    
        always {
            deleteDir()
        }

        success {
            script {
                    if (env.BRANCH_NAME.equals("master")) {
                        emailext (
                            subject: "SUCCESS MASTER",
                            body: "SUCCESS MASTER: Job",
                            to: 'trade.hunter.ai@gmail.com',
                        )
                    }
                    if (env.BRANCH_NAME.equals("develop")) {
                        emailext (
                            subject: "SUCCESS DEVELOP:",
                            body: "SUCCESS DEVELOP: Job",
                            to: 'trade.hunter.ai@gmail.com',
                        )
                    }
            }
        }        
        
        failure {
            script {
                    if (env.BRANCH_NAME.equals("develop")) {
                        emailext (
                            subject: "FAILED DEVELOP",
                            body: "FAILED DEVELOP: Job",
                            to: 'trade.hunter.ai@gmail.com',
                        )
                    }
                    
                    if (env.BRANCH_NAME.equals("master")) {
                        emailext (
                            subject: "FAILED MASTER",
                            body: "FAILED MASTER: Job",
                            to: 'trade.hunter.ai@gmail.com',
                        )
                    }
            }
        }
    }
}
