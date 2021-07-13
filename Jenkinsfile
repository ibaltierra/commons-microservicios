pipeline {
    agent any
    
    tools {
        maven 'mavenjenkins'
    }
    
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
			post {
				always {
					echo "Proceso terminado con éxito......"
				}
			}
        }
    }
}
