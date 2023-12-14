pipeline {
    agent any

    stages {
        stage('Preperation') {
            steps {
                echo 'Hello World'
            }
        }
    
        stage('Development') {
            steps {
                echo 'Hello World'
            }
        }
    
        stage('Build') {
            steps {
                echo 'Hello World'
            }
        }
    
        stage('Test') {
            steps {
                echo 'Hello World'
            }
        }
    }
}
