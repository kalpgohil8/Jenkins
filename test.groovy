node {
    stage('Preparation') {
        checkout scm
        echo 'Preparation'
    }

    stage('Test') {
        echo 'Test'
    }

}