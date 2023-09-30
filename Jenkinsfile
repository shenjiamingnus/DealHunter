pipeline {
    agent any

    tools {
        maven 'maven'
        jdk 'java'
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('docker')
    }

    stages {
        stage('Build') {
            steps {
                // 使用Maven编译和打包Spring Boot应用
//                 sh 'mvn clean package -DskipTests'
//                 sh 'mvn package -DskipTests'
            }
        }

            stage('Login') {
                steps {
                    sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                }
            }

        stage('Build Docker Image') {
            steps {
                // 构建Docker镜像
                script {
                    docker.build("nandonus/dealhunter-backend")
                }
            }
        }

        stage('Push Docker Image to Registry') {
            steps {
                // 推送Docker镜像到Docker仓库
                script {
                    docker.image("nandonus/dealhunter-backend").push()
                }
            }
        }
    }

    post {
        success {
            // 构建成功时的操作，可以添加通知等
            echo 'Build and deployment successful!'
        }
        failure {
            // 构建失败时的操作，可以添加通知等
            echo 'Build or deployment failed!'
        }
    }
}
