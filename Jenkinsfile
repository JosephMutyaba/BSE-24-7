pipeline {
    agent any

    tools {
        nodejs 'node_20.13.1'
    }

    stages {
        stage('Prepare Environment') {
            steps {
                script {
                    // Clean previous environment files
                    sh 'rm -f todo-front-end/.env'
                    sh 'rm -f todo-back-end/src/main/resources/application.properties'
                }
            }
        }

        stage('Staging Build and Deploy') {
            steps {
                script {
                    // Use staging environment files
                    sh 'cp todo-front-end/.env.staging todo-front-end/.env'
                    sh 'cp todo-back-end/src/main/resources/application-staging.properties todo-back-end/src/main/resources/application.properties'
                }

                // Backend Build and Test
                dir('todo-back-end') {
                    echo 'Setting executable permission for mvnw...'
                    sh 'chmod +x mvnw'
                    echo 'Building Spring Boot backend using Maven Wrapper...'
                    sh './mvnw clean install'
                    echo 'Running backend tests...'
                    sh './mvnw test'
                }

                // Frontend Build and Test
                dir('todo-front-end') {
                    echo 'Installing dependencies...'
                    sh 'npm install'
                    echo 'Running frontend build...'
                    sh 'npm run build'
                }

                // Deploy Backend to AWS Elastic Beanstalk
                dir('todo-back-end/target') {
                    script {
                        echo 'Deploying backend to AWS Elastic Beanstalk (Staging)...'
                        withAWS(credentials: 'aws-credentials-id', region: 'us-east-1') {
                            def jarFile = sh(script: "ls *.jar", returnStdout: true).trim()
                            echo "JAR file found: ${jarFile}"
                            sh "aws s3 cp ${jarFile} s3://todolist-frontend-app/backends/${jarFile}"
                            echo 'Creating application version in Elastic Beanstalk...'
                            sh """
                                aws elasticbeanstalk create-application-version --application-name todolistapp --version-label staging-${BUILD_NUMBER} --source-bundle S3Bucket=todolist-frontend-app,S3Key=backends/${jarFile}
                                echo "Updating environment..."
                                aws elasticbeanstalk update-environment --application-name todolistapp --environment-name Todolistapp-env --version-label staging-${BUILD_NUMBER}
                            """
                        }
                    }
                }

                // Deploy Frontend to AWS S3
                dir('todo-front-end/dist') {
                    script {
                        echo 'Deploying frontend to AWS S3 (Staging)...'
                        withAWS(credentials: 'aws-credentials-id', region: 'us-east-1') {
                            sh 'aws s3 sync . s3://todolist-frontend-app/ --delete'
                        }
                    }
                }
            }
        }

        stage('Production Build and Deploy') {
            steps {
                script {
                    try {
                        // Use production environment files
                        sh 'cp todo-front-end/.env.production todo-front-end/.env'
                        sh 'cp todo-back-end/src/main/resources/application-production.properties todo-back-end/src/main/resources/application.properties'

                        // Backend Build and Test
                        dir('todo-back-end') {
                            echo 'Building Spring Boot backend using Maven Wrapper...'
                            sh './mvnw clean install'
                            echo 'Running backend tests...'
                            sh './mvnw test'
                        }

                        // Frontend Build and Test
                        dir('todo-front-end') {
                            echo 'Installing dependencies...'
                            sh 'npm install'
                            echo 'Running frontend build...'
                            sh 'npm run build'
                        }

                        // Deploy Backend to AWS Elastic Beanstalk
                        dir('todo-back-end/target') {
                            echo 'Deploying backend to AWS Elastic Beanstalk (Production)...'
                            withAWS(credentials: 'aws-credentials-id', region: 'us-east-1') {
                                def jarFile = sh(script: "ls *.jar", returnStdout: true).trim()
                                echo "JAR file found: ${jarFile}"
                                sh "aws s3 cp ${jarFile} s3://todolist-production-bucket/backends/${jarFile}"
                                echo 'Creating application version in Elastic Beanstalk...'
                                sh """
                                    aws elasticbeanstalk create-application-version --application-name todolist-production --version-label production-${BUILD_NUMBER} --source-bundle S3Bucket=todolist-production-bucket,S3Key=backends/${jarFile}
                                    echo "Updating environment..."
                                    aws elasticbeanstalk update-environment --application-name todolist-production --environment-name Todolist-production-env --version-label production-${BUILD_NUMBER}
                                """
                            }
                        }

                        // Deploy Frontend to AWS S3
                        dir('todo-front-end/dist') {
                            echo 'Deploying frontend to AWS S3 (Production)...'
                            withAWS(credentials: 'aws-credentials-id', region: 'us-east-1') {
                                sh 'aws s3 sync . s3://todolist-production-bucket/ --delete'
                            }
                        }

                    } catch (Exception e) {
                        echo "Deployment failed. Rolling back to previous stable version..."
                        def previousStableVersion = sh(
                            script: """
                                aws elasticbeanstalk describe-environments \
                                --application-name todolist-production \
                                --environment-name Todolist-production-env \
                                --query 'Environments[0].VersionLabel' \
                                --output text
                            """, returnStdout: true
                        ).trim()

                        echo "Previous stable version: ${previousStableVersion}"

                        withAWS(credentials: 'aws-credentials-id', region: 'us-east-1') {
                            sh """
                                aws elasticbeanstalk update-environment --application-name todolist-production --environment-name Todolist-production-env --version-label ${previousStableVersion}
                            """
                        }
                        error "Deployment failed and rollback initiated."
                    }
                }
            }
        }
    }
}