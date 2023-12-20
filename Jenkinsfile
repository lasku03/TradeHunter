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
    }

    post {
        always {
            deleteDir()
        }

        success {
            script {
                    if (env.BRANCH_NAME.equals('master')) {
                        emailext (
                            subject: "SUCCESS MASTER: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                            body: """<p>SUCCESS MASTER: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                                """,
                            to: 'trade.hunter.ai@gmail.com',
                            recipientProviders: [[$class: 'DevelopersRecipientProvider']]
                        )
                    }
                    if (env.BRANCH_NAME.equals('develop')) {
                        emailext (
                            subject: "SUCCESS DEVELOP: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                            body: """<p>SUCCESS DEVELOP: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                                """,
                            to: 'trade.hunter.ai@gmail.com',
                            recipientProviders: [[$class: 'DevelopersRecipientProvider']]
                        )
                        emailext (
                            subject: "SUCCESS DEVELOP: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                            body: """<p>SUCCESS DEVELOP: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                                """,
                            to: 'unai.laskurain@alumni.mondragon.edu',
                            recipientProviders: [[$class: 'DevelopersRecipientProvider']]
                        )
                    }
            }
        }
        
        failure {
            script {
                    if (env.BRANCH_NAME.equals("develop")) {
                        emailext (
                            subject: "FAILED DEVELOP: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                            body: """<p>FAILED DEVELOP: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                                    <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>
                                """,
                            to: 'trade.hunter.ai@gmail.com',
                            recipientProviders: [[$class: 'DevelopersRecipientProvider']]
                        )
                    }
                    
                    if (env.BRANCH_NAME.equals("master")) {
                        emailext (
                            subject: "FAILED MASTER: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                            body: """<p>FAILED MASTER: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                                    <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>
                                """,
                            to: 'trade.hunter.ai@gmail.com',
                            recipientProviders: [[$class: 'DevelopersRecipientProvider']]
                        )
                    }
            }
        }
    }
}
