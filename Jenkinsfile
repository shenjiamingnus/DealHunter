pipeline {
    agent any

      tools {
          maven 'maven'
          jdk 'java'
      }

    stages {
        stage('Build') {
            steps {
                // 使用Maven编译和打包Spring Boot应用
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                // 构建Docker镜像
                script {
                    docker.build("DealHunter:${env.BUILD_NUMBER}")
                }
            }
        }

        stage('Push Docker Image to Registry') {
            steps {
                // 推送Docker镜像到Docker仓库
                script {
                    docker.image("DealHunter:${env.BUILD_NUMBER}").push()
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
