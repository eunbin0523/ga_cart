pipeline {
    agent  {
        label 'dind-agent'
    }
    stages {
        stage('Build image') {
            steps {
                script {
                    app = docker.build("eunoia0523/springshoppingcart")
                }
            }
        }
        
        stage("Push image to ar") {
            steps {
                script {
                    docker.withRegistry('https://asia.gcr.io', 'gcr:eunoia0523') {
                        app.push("${env.BUILD_NUMBER}")
                    }
                }
            }
        }

        stage('K8S Manifest Update') {

            steps {

                git credentialsId: 'eunbin0523', 
                    url: 'https://github.com/eunbin0523/shopping-k8s-argo.git',
                    branch: 'master'

                sh "sed -i 's/springshoppingcart:.*\$/springshoppingcart:${env.BUILD_NUMBER}/g' deployment.yaml"
                sh "git add deployment.yaml"
                sh "git commit -m '[UPDATE] springshoppingcart ${env.BUILD_NUMBER} image versioning'"

                withCredentials([gitUsernamePassword(credentialsId: 'eunbin0523')]) {
                    sh "git push -u origin master"
                }
            }
            post {
                    failure {
                    echo 'Update failure !'
                    }
                    success {
                    echo 'Update success !'
                    }
            }
        }

    }             

}
