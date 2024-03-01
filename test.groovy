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
            
            def version_val_array = iteration['version'].split("\\|")
            version_val_array = version_val_array.collect{ mod_val ->mod_val.trim() }
                
            println "${run_mode_val_array.size()}"
            println "${version_val_array.size()}"

            if(run_mode_val_array.size() != version_val_array.size()){
                println "NOT DONE"
            }
            else {
                println "DONE"
            }
        }
    }
}