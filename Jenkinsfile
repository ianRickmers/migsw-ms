pipeline {
    agent any
    tools{
        maven 'maven'
    }
    stages {
        stage('Build jar file') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/ianRickmers/migsw-ms']]])
                script{
                    if (isUnix()) {
                        sh 'mvn install -DskipTests'
                    } else {
                        bat 'mvn install -DskipTests'
                    }
                }
            }
        }
        stage('Test') {
            steps {
                script{
                    if (isUnix()) {
                        sh 'mvn test'
                    } else {
                        bat 'mvn test'
                    }
                }
            }
        }
        stage('Build docker image'){
            steps {
                script{
                    if (isUnix()) {
                        sh 'docker build -t ianrickmers/migsw-ms .'
                    } else {
                        bat 'docker build -t ianrickmers/migsw-ms .'
                    }    
                }
            }
        }
        stage('Push docker image'){
            steps {
                script{
                    withCredentials([string(credentialsId: 'dckrhubpassword', variable: 'dckpass')]) {
                            if(isUnix()){
                                sh "docker login -u ianrickmers -p $dckpass"
                                sh 'docker push ianrickmers/migsw-ms'
                            } else {
                                bat "docker login -u ianrickmers -p $dckpass"
                                bat 'docker push ianrickmers/migsw-ms'
                            }
                        }
                }
            }
        }
    }
    post {
		always {
            script{
                if (isUnix()) {
                    sh 'docker logout'
                } else {
                    bat 'docker logout'
                }
            }
		}
	}
}