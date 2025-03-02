pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/marikraa/Ohjelmistotuotantoprojekti.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Code Coverage') {
            steps {
                sh 'mvn jacoco:report'
            }
        }
        stage('Publish Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Publish Coverage Report') {
            steps {
                jacoco()
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    def app = docker.build("liukkari/ohjelmistotuotantoprojekti:${env.BUILD_ID}")
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
