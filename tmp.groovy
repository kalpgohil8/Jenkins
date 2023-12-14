def workdir() {
    return 'workdir'
}

def project() {
    return 'kalp2'
}

def copy_ip_to_Json(json_path, ip_addr) {
    def jsonContent = readJSON file: json_path

    try {
        jsonContent.Input[0].each { syncModeName, syncModeDetails ->
            if (syncModeDetails.TEST_MODE == 1) {
                echo "Changing BoardIp_Dynamic for ${syncModeName}"
                syncModeDetails.BoardIp_Dynamic = ip_addr
            }
        }
        writeJSON file: json_path, json: jsonContent
    } catch (err) {
        echo "Error ip (${ip_addr} at ${json_path} : ${err})"
    }
}

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

        git branch: 'test-pipeline', url: 'https://github.com/kalpgohil8/Jenkins.git'

        def jsonFile = readJSON file: "${workdir()}/${project()}/Json"
        echo "${jsonFile}"
        echo "${jsonFile['boards'][0]['ports'][0]['ip_addr']}"

        def input_before = readJSON file: "${workdir()}/${project()}/input.json"
        echo "${input_before}"

        def userInput = input(
            id: 'userInput',
            message: 'Please review the input JSON file and provide necessary input:',
            parameters: [
                choice(choices: ['Option A', 'Option B'], description: 'Select an option:', name: 'selectedOption')
            ]
        )

        echo "User selected: ${userInput}"

        // Assuming that the input value will be used in your further logic
        // Modify this part based on your actual use case
        def selectedOption = userInput['selectedOption']
        echo "Selected Option: ${selectedOption}"

        evaluate {
            copy_ip_to_Json("${workdir()}/${project()}/input.json", jsonFile['boards'][0]['ports'][0]['ip_addr'].trim())
        }

        def input_after = readJSON file: "${workdir()}/${project()}/input.json"
        echo "${input_after}"

        script {
            sh "python3 get_dynamic_ip.py ${workdir()}/${project()}/input.json ${selectedOption} > SYNC_A_IP.log"
            def AIP = readFile('SYNC_A_IP.log').trim()
            echo "AIP: ${AIP}"
        }
    }
}
