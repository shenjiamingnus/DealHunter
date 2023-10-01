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
                sh 'mvn package -DskipTests'
            }
        }

         stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('sonar') {
                    sh "mvn package org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar"
//                     sh "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:4.8.0.2856:sonar \
//                           -Dsonar.projectKey=dealhunter-backend \
//                           -Dsonar.host.url=http://159.89.205.188:9000 \
//                           -Dsonar.login=sqp_c401d95055ed6f33284d5f88d2e008372e65f9f7"
                }
            }
         }
         stage("Quality gate") {
            steps {
                waitForQualityGate abortPipeline: true
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
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'

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
