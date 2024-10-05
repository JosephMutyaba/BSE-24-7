pipeline {
    agent any

     tools {
        nodejs 'node_20.13.1'
    }
    
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials-id')
        DOCKER_IMAGE_BACKEND = 'josephmutyaba/todolistapp_backend'
        DOCKER_IMAGE_FRONTEND = 'josephmutyaba/todolistapp_frontend'
    }

    stages {

        stage('Backend Build and Test') {
            steps {
                dir('todo-back-end') {
                    // Ensure Maven Wrapper has execute permissions
                    echo 'Setting executable permission for mvnw...'
                    sh 'chmod +x mvnw'

                    echo 'Building Spring Boot backend using Maven Wrapper...'
                    sh './mvnw clean install'

                    echo 'Running backend tests...'
                    sh './mvnw test'
                }
            }
        }

        stage('Frontend Build and Test') {
            steps {
                dir('todo-front-end') {
                    echo 'Installing dependencies...'
                    sh 'npm install'

                    echo 'Running frontend build...'
                    sh 'npm run build'

                    echo 'Running frontend tests...'
                    //sh 'npm test'
                }
            }
        }
        
        stage('Build Docker Images') {
            steps {
                echo 'Building Docker images for backend and frontend...'

                // Backend Docker image
                dir('todo-back-end') {
                    script {
                        sh 'docker build -t $DOCKER_IMAGE_BACKEND .'
                        sh 'docker login -u $DOCKERHUB_CREDENTIALS_USR -p $DOCKERHUB_CREDENTIALS_PSW'
                        sh 'docker push $DOCKER_IMAGE_BACKEND'
                    }
                }

                // Frontend Docker image
                dir('todo-front-end') {
                    script {
                        sh 'docker build -t $DOCKER_IMAGE_FRONTEND .'
                        sh 'docker login -u $DOCKERHUB_CREDENTIALS_USR -p $DOCKERHUB_CREDENTIALS_PSW'
                        sh 'docker push $DOCKER_IMAGE_FRONTEND'
                    }
                }
            }
        }

        
    }
}
