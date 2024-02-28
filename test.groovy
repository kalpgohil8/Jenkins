node {
    stage('Preparation') {
        checkout scm
        echo 'Preparation'
    }

    stage('Test') {
        def json_data = readJSON file: "data.json"
        json_data.each { iteration ->
        println iteration
        def run_mode_val_array = iteration['run_mode'].split("\\|")
        echo run_mode_val_array
        }
    }
}