pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials-id')
        DOCKER_IMAGE_BACKEND = 'josephmutyaba/todolistapp_backend'
        DOCKER_IMAGE_FRONTEND = 'josephmutyaba/todolistapp_frontend'
    }

    stages {
        stage('Backend Build and Test') {
            steps {
                dir('todo-back-end') {
                    // Backend is in the 'backend' folder
                    echo 'Building Spring Boot backend...'
                    sh 'mvn clean install'

                    echo 'Running backend tests...'
                    sh 'mvn test'
                }
            }
        }

        stage('Frontend Build and Test') {
            steps {
                dir('todo-front-end') {
                    // Frontend is in the 'frontend' folder
                    echo 'Installing dependencies...'
                    sh 'npm install'

                    echo 'Running frontend build...'
                    sh 'npm run build'

                    echo 'Running frontend tests...'
                    sh 'npm test'
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                echo 'Building Docker images for backend and frontend...'

                // Backend Docker image
                dir('backend') {
                    script {
                        sh 'docker build -t $DOCKER_IMAGE_BACKEND .'
                        sh 'docker login -u $DOCKERHUB_CREDENTIALS_USR -p $DOCKERHUB_CREDENTIALS_PSW'
                        sh 'docker push $DOCKER_IMAGE_BACKEND'
                    }
                }

                // Frontend Docker image
                dir('frontend') {
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
