node {

    stage('Clean Workspace') {
        deleteDir()
    }

    stage('Preparation') {
        git.cloneAndCheckoutBranch("Jenkins", "test-pipeline", "$WORKSPACE", "git@github.com:kalpgohil8")
        jenkins = load "$WORKSPACE/Jenkins/utils/jenkins.groovy"
    }

    stage('Test') {

        agent {
            docker {
                image 'python:3.12.1-alpine3.19' 
            }
        }
        def jsonFile = readJSON file: "workdir/kalp2/Json"
        println ("BoardIp_Dynamic : ${jsonFile['boards'][0]['ports'][0]['ip_addr']}")

        def input_before = readJSON file: "workdir/kalp2/input.json"
        println ("Input.json before change : ${input_before}")
        
        copy_ip_to_Json("workdir/kalp2/input.json", jsonFile['boards'][0]['ports'][0]['ip_addr'].trim())
        
        def input_after = readJSON file: "workdir/kalp2/input.json"
        println ("Input.json after change : ${input_before}")

        def AIP = sh returnStdout: true, script: "python3 get_dynamic_ip.py workdir/kalp2/input.json B"
        println("AIP: ${AIP}")
    }
}


def copy_ip_to_Json(json_path, ip_addr) {
    def jsonContent = readJSON file: json_path

    try {
        jsonContent.Input[0].each { syncModeName, syncModeDetails ->
            // println("${syncModeName}" : "${syncModeDetails}")
            if (syncModeDetails.TEST_MODE == 1) {
                println("Changing BoardIp_Dynamic for ${syncModeName}")
                syncModeDetails.BoardIp_Dynamic = ip_addr
            }
        }
        writeJSON file: json_path, json: jsonContent
    } catch (err) {
        println "Error ip (${ip_addr} at ${json_path} : ${err})"
    }
}
