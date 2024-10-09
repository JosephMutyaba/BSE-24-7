pipeline {
    agent any

    tools {
        nodejs 'node_20.13.1'
    }

    environment {
        S3_BUCKET_NAME = 'todolist-frontend-app'
        ELASTIC_BEANSTALK_APP_NAME = 'todolistapp'
        ELASTIC_BEANSTALK_ENV_NAME = 'Todolistapp-env'
        REGION = 'us-east-1'  // Modify as per your AWS region
    }

    stages {
    

        stage('Deploy Backend to AWS Elastic Beanstalk') {
    steps {
        dir('todo-back-end/target') {
            script {
                echo 'Deploying backend to AWS Elastic Beanstalk...'

                // Upload JAR file to S3
                withAWS(credentials: 'aws-credentials-id', region: "${REGION}") {

                    // echo 'Uploading JAR file to S3...'

                    // Prepare JAR file
                    def jarFile = sh(script: "ls *.jar", returnStdout: true).trim()
                    echo "JAR file found: ${jarFile}"

                    // // Upload the JAR file to S3
                    // withAWS(credentials: 'aws-credentials-id', region: "${REGION}") {
                    //      // Ensure the jarFile variable is correctly referenced
                    //     sh "aws s3 cp ${jarFile} s3://${S3_BUCKET_NAME}/backends/${jarFile}"
                    // }


                    echo 'Deploying backend to AWS Elastic Beanstalk...'

                     // Now create the application version
                        echo 'Creating application version in Elastic Beanstalk...'
                        withAWS(credentials: 'aws-credentials-id', region: "${REGION}") {
                            sh '''
                            aws elasticbeanstalk create-application-version --application-name ${ELASTIC_BEANSTALK_APP_NAME} --version-label ${BUILD_NUMBER} --source-bundle S3Bucket=${S3_BUCKET_NAME},S3Key=backends/${jarFile}
                            echo "Updating environment..."
                            aws elasticbeanstalk update-environment --application-name ${ELASTIC_BEANSTALK_APP_NAME} --environment-name ${ELASTIC_BEANSTALK_ENV_NAME} --version-label ${BUILD_NUMBER}
                            '''
                        }

                    // Update Elastic Beanstalk environment
                   // echo "Updating environment..."
                    //sh '''
                    //aws elasticbeanstalk update-environment --application-name ${ELASTIC_BEANSTALK_APP_NAME} --environment-name ${ELASTIC_BEANSTALK_ENV_NAME} --version-label ${BUILD_NUMBER}
                    //'''
                }
            }
        }
    }
}


        stage('Deploy Frontend to AWS S3') {
            steps {
                dir('todo-front-end/dist') {
                    script {
                        echo 'Deploying frontend to AWS S3...'

                        // Sync the dist/ directory to S3 bucket using AWS credentials
                        withAWS(credentials: 'aws-credentials-id', region: "${REGION}") {
                            sh '''
                            echo "Syncing to S3..."
                            aws s3 sync . s3://${S3_BUCKET_NAME}/ --delete
                            '''
                        }
                    }
                }
            }
        }
    }
}
