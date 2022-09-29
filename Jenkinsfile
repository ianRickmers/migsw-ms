pipeline {
    agent any
    tools{
        maven 'maven'
    }
    stages {
        stage('Build jar file') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/ianRickmers/migsw-ms']]])
                if (isUnix()) --> sh 'mvn install -DskipTests'

                 else --> bat 'mvn install -DskipTests'
                
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Build docker image'){
            steps {
                sh 'docker build -t ianrickmers/migsw-ms .'
            }
        }
        stage('Push docker image'){
            steps {
                script{
                    withCredentials([string(credentialsId: 'dckrhubpassword', variable: 'dckpass')]) {
                            sh 'docker login -u ianrickmers -p ${dckpass}'
                        }
                    sh 'docker push ianrickmers/migsw-ms'
                }
            }
        }
    }
    post {
		always {
			sh 'docker logout'
		}
	}
}