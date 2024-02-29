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
            run_mode_val_array = run_mode_val_array.collect{ mod_val ->mod_val.trim() }
            
            if(run_mode_val_array.contains(run_mode.trim())) {
                println "DONE"
            } else {
                println "NOT DONE"
            }
        }
    }
}