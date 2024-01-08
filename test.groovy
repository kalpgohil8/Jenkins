node {
    stage('Preparation') {
        echo 'Preparation'
    }

    stage('Test') {

        agent {
            docker {
                image 'python:3.12.1-alpine3.19' 
            }
        }

        script{
            sh """
             ls
             pwd
            """
        }

        def jsonFile = readJSON file: "Json"
        echo "${jsonFile}"

        // script {
        //     sh "python3 get_dynamic_ip.py ${workdir()}/${project()}/input.json B > SYNC_A_IP.log"
        //     def AIP = readFile('SYNC_A_IP.log').trim()
        //     echo "AIP: ${AIP}"
        // } 
    }
}