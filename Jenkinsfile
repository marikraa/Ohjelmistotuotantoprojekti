pipeline {
    agent any

    environment {
        // Define Docker Hub credentials ID
        DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'
        // Define Docker Hub repository name
        DOCKERHUB_REPO = 'villeschulz/notewizard'
        // Define Docker image tag
        DOCKER_IMAGE_TAG = 'latest_v1'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'ville', url: 'https://github.com/marikraa/Ohjelmistotuotantoprojekti.git'
            }
        }
        stage('Run Tests') {
            steps {
                // Run the tests first to generate data for Jacoco and JUnit
                bat 'mvn clean test' // For Windows agents
                // sh 'mvn clean test' // Uncomment if on a Linux agent
            }
        }
        stage('Code Coverage') {
            steps {
                // Generate Jacoco report after the tests have run
                bat 'mvn jacoco:report'
            }
        }
        stage('Publish Test Results') {
            steps {
                // Publish JUnit test results
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Publish Coverage Report') {
            steps {
                // Publish Jacoco coverage report
                jacoco()
            }
        }
        stage('Build') {
            steps {
                // Run mvn clean install
                bat 'mvn clean install' // For Windows agents
                // sh 'mvn clean install' // Uncomment if on a Linux agent
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${env.DOCKERHUB_REPO}:${env.DOCKER_IMAGE_TAG}")
                }
            }
        }
        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', env.DOCKERHUB_CREDENTIALS_ID) {
                        docker.image("${env.DOCKERHUB_REPO}:${env.DOCKER_IMAGE_TAG}").push()
                    }
                }
            }
        }
    }
}
