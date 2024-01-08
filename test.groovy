node {
    stage('Preparation') {
        checkout scm
        echo 'Preparation'
    }

    stage('Test') {

        agent {
            docker {
                image 'python:3.12.1-alpine3.19' 
            }
        }

        def jsonFile = readJSON file: "Json"
        echo "${jsonFile}"

        script {
            sh "python kalp.py"
        } 

        def jsonFile1 = readJSON file: "Json"
        echo "${jsonFile1}"

    }
}