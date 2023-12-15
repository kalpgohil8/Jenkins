def workdir() {
    return 'workdir'
}

def project() {
    return 'kalp2'
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
        println ("BoardIp_Dynamic : ${jsonFile['boards'][0]['ports'][0]['ip_addr']}")

        def input_before = readJSON file: "${workdir()}/${project()}/input.json"
        println ("Input.json before change : ${input_before}")
        
        copy_ip_to_Json("${workdir()}/${project()}/input.json", jsonFile['boards'][0]['ports'][0]['ip_addr'].trim())
        
        def input_after = readJSON file: "${workdir()}/${project()}/input.json"
        println ("Input.json after change : ${input_before}")

        sh '''
            python3 get_dynamic_ip.py ${workdir()}/${project()}/input.json B > SYNC_A_IP.log
            AIP=\$(cat SYNC_A_IP.log)
            println("AIP: \${AIP}")
        '''
    }
}


def copy_ip_to_Json(json_path, ip_addr) {
    def jsonContent = readJSON file: json_path

    try{
        jsonContent.Input[0].each { syncModeName, syncModeDetails ->
        // println("${syncModeName}" : "${syncModeDetails}")
        if (syncModeDetails.TEST_MODE == 1){
            println ("Changing BoardIp_Dynamic for ${syncModeName}")
            syncModeDetails.BoardIp_Dynamic = ip_addr
        }    
    }
    writeJSON file: json_path, json: jsonContent
    } catch (err){
        println "Error ip (${ip_addr} at ${json_path} : ${err})" 
    }
}