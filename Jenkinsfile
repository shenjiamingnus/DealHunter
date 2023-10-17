pipeline {
    agent any

    tools {
        maven 'maven'
        jdk 'java'
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('docker')
        TERRAFORM_CREDENTIALS = credentials('terraform')
    }

    stages {
        stage('Build') {
            steps {
                // 使用Maven编译和打包Spring Boot应用
//                 sh 'mvn clean package'
                sh 'mvn clean package -DskipTests'
            }
        }

//          stage('SonarQube analysis') {
//             steps {
//                 withSonarQubeEnv('sonar') {
//                     sh "mvn sonar:sonar \
//                           -Dsonar.projectKey=dealhunter-backend \
//                           -Dsonar.host.url=http://159.89.205.188:9000 \
//                           -Dsonar.login=sqp_c401d95055ed6f33284d5f88d2e008372e65f9f7"
//                 }
//             }
//          }
//          stage("Quality gate") {
//             steps {
//                 waitForQualityGate abortPipeline: true
//             }
//          }

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
        stage('Deploy'){
            steps {
                script {
                    // 切换到指定路径
                    def targetDirectory = '/root/doterraform'
                    dir(targetDirectory) {
                        // 在目标路径执行cat操作
//                         sh 'export DO_PAT=dop_v1_930e3c5909d8d1cabef471b7bffe7f00b6235cf0ca6ae402ad3d93147684373a'
                        sh 'terraform apply -auto-approve -var "do_token=$TERRAFORM_CREDENTIALS" -var "ssh_private_key=/root/digit" -var "docker_host=128.199.67.95" -var "docker_cert_path=/root/.docker/machine/machines/docker-nginx2"'
                    }
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
