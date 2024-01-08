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

        helper("Json")

    }
}

def helper(json_file_path) {
    def file_path = json_file_path

    // Read JSON data from file
    def jsonFile = readJSON file: file_path

    def calnax_setup = jsonFile.calnax_setup ?: [:]
    def (dut_host, dut_port) = (calnax_setup.dut ?: "").split()[1..2]
    def (mcu_host, mcu_port) = (calnax_setup.mcu ?: "").split()[1..2]

    def uarts = (jsonFile.boards ?: [[]])[0].uarts ?: []

    uarts.each { uart ->
        def alias = uart.alias ?: ""
        if (alias == "dut") {
            uart.host = dut_host
            uart.port = dut_port
            uart.connection_url = ${uart.host} + " " + ${uart.port}
        } else if (alias == "mcu") {
            uart.host = mcu_host
            uart.port = mcu_port
            uart.connection_url = ${uart.host} + " " + ${uart.port}
        }
    }

    // Write modified JSON data back to the file
    writeJSON(file: file_path, json: jsonFile, pretty: 4)

    // Read JSON data again from the modified file
    def jsonFile1 = readJSON file: file_path

    println jsonFile1
}
