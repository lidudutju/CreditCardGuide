node {
    stage('Clone the project') {
        git 'https://github.com/lidudutju/CreditCardGuide'
    }

    dir('pipeline-demo') {
        stage('Compilation and Analysis') {
            parallel 'Compilation': {
                sh ./compilation.sh
            }, 'Testing': {
                sh "echo ----- hello"
            }
        }
    }
}