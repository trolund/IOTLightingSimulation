// author Troels (s161791)
// author Daniel (s151641)

try {
    node {
        stage ('Package All Applications') {
            script {
                try {
                    checkout scm
                    sh './mvn_package_all.sh'
                } catch(error) {
                    echo err.getMessage()
                }
            }
        }

        stage ('Build Docker Images') {
            script {
                try {
                    checkout scm
                    sh 'docker-compose build'
                    sh 'docker-compose up -d'
                } catch(error) {
                    echo err.getMessage()
                }
            }
        }

         stage ('Payment Tests') {
            script {
                try {
                    checkout scm
                    sh 'mvn -f payment-service/pom.xml test'
                } catch(error) {
                    echo err.getMessage()
                }
            }
        }

        stage ('Account Tests') {
            script {
                try {
                    checkout scm
                    sh 'mvn -f account-service/pom.xml test'
                } catch(error) {
                    echo err.getMessage()
                }
            }
        }

        stage ('Token Tests') {
            script {
                try {
                    checkout scm
                    sh 'mvn -f token-service/pom.xml test'
                } catch(error) {
                    echo err.getMessage()
                }
            }
        }

        stage ('Report Tests') {
            script {
                try {
                    checkout scm
                    sh 'mvn -f report-service/pom.xml test'
                } catch(error) {
                    echo err.getMessage()
                }
            }
        }
        
        stage ('Client Tests (end-to-end)') {
            script {
                try {
                    checkout scm
                    sh 'mvn -f client/pom.xml test'
                } catch(error) {
                    echo err.getMessage()
                }
            }
        }

    }
} finally {
    node {
        stage ('Deployment finished') {
            checkout scm
            sh 'echo "Deployment finished!"'
        }
    }
}