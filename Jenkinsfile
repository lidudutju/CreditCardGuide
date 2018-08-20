node {
    stage 'Clone the project'
    git 'https://github.com/lidudutju/CreditCardGuide'

    dir('CreditCardGuide') {
        stage('Compilation and Analysis') {
            parallel 'Compilation': {
                sh "./gradlew clean"
            }, 'Echoing': {
                sh "echo -----> hello"
            }
        }
    }
}