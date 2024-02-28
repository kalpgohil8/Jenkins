node {
    stage('Preparation') {
        checkout scm
        echo 'Preparation'
    }

    stage('Test') {
        def json_data = readJSON file: "data.json"
        sh """ 
            cat json_data
        """
    }
}