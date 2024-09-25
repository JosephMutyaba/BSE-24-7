pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                
                git 'https://github.com/JosephMutyaba/BSE-24-7.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    docker.image('maven:3.8.6-openjdk-21').inside {
                        sh 'mvn clean install'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    docker.image('maven:3.8.6-openjdk-21').inside {
                        sh 'mvn test'
                    }
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    docker.image('maven:3.8.6-openjdk-21').inside {
                        sh 'mvn package'
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build('josephmutyaba/BSE-24-7')
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials-id') {
                        docker.image('josephmutyaba/BSE-24-7').push()
                    }
                }
            }
        }
    }
}
