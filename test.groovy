node {
    stage('Clean Workspace') {
        deleteDir()
    }

    stage('Preparation') {
        git branch: 'test-pipeline', url: 'https://github.com/kalpgohil8/Jenkins.git'
        jenkins = load "utils/jenkins.groovy"
        // git.cloneAndCheckoutBranch("Jenkins", "first-pipeline")
    }

    stage('Test') {
        agent {
            docker {
                image 'python:3.12.1-alpine3.19' 
            }
        }

        println("Parameter : ${tobechanged}")

        // def cfgFile = readFile "workdir/kalp2/tmp.cfg"
        // println("CFG File: ${cfgFile}")

        // cfgFile = cfgFile.replaceAll(/(\[${sectionToUpdate}\]\s*server_ip\s*=\s*).*/, "\$1${newServerIp}")

        // writeFile file: "workdir/kalp2/tmp.cfg", text: cfgFile

        def jsonFile = readJSON file: "workdir/kalp2/Json"
        println("BoardIp_Dynamic: ${jsonFile['boards'][0]['ports'][0]['ip_addr']}")

        def input_before = readJSON file: "workdir/kalp2/input.json"
        println("Input.json before change: ${input_before}")

        copy_ip_to_Json("workdir/kalp2/input.json", jsonFile['boards'][0]['ports'][0]['ip_addr'].trim())

        def str1 = jsonFile['device_name1']
        def str2 = jsonFile['device_name2']
        def str3 = jsonFile['device_name3']

        println("str1 : ${str1}")
        println("str2 : ${str2}")
        println("str3 : ${str3}")
        if (str1.contains("cn10kas") || str1.contains("106")) {
            println("str 1 contains")
        }

        if (str2.contains("cn10kas") || str2.contains("106")) {
            println("str 2 contains")
        }

        if (str3.contains("cn10kas") || str3.contains("106")) {
            println("str 3 contains")
        }

        def input_after = readJSON file: "workdir/kalp2/input.json"
        println("Input.json after change: ${input_after}")

        def AIP = sh returnStdout: true, script: "python3 get_dynamic_ip.py workdir/kalp2/input.json B"
        println("AIP: ${AIP}")
    }
}

def copy_ip_to_Json(json_path, ip_addr) {
    def jsonContent = readJSON file: json_path

    try {
        jsonContent.Input[0].each { syncModeName, syncModeDetails ->
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
