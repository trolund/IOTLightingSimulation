// author Troels (s161791)
// author Daniel (s151641)

try {
    def CONTAINER_NAMES = ['payment', 'account', 'token', 'report', 'client']

    node {
        stage ('Package all applications') {
            checkout scm
            sh './mvn_package_all.sh'
        }

        stage ('Build Docker Images') {
            checkout scm
            sh 'docker-compose build'
            sh 'docker-compose up -d'
        }

        stage ('Payment tests') {
            checkout scm
            sh 'mvn -f payment-service/pom.xml test'
        }

        stage ('Account tests') {
            checkout scm
            sh 'mvn -f account-service/pom.xml test'
        }

        stage ('Token tests') {
            checkout scm
            sh 'mvn -f token-service/pom.xml test'
        }

        stage ('Report tests') {
            checkout scm
            sh 'mvn -f report-service/pom.xml test'
        }
        
        stage ('Client tests (end-to-end)') {
            checkout scm
            sh 'mvn -f client/pom.xml test'
        }
    }
} catch (e) {
    node {
       stage ('Deployment failed!') {
          echo e
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