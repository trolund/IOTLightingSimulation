// author Troels (s161791)
// author Daniel (s151641)

try {
    node {
        stage ('Package All Applications') {
            checkout scm
            sh './mvn_package_all.sh'
        }

        stage ('Build Docker Images') {
            checkout scm
            sh 'docker-compose build'
            sh 'docker-compose up -d'
        }

/*         stage ('Payment Tests') {
            checkout scm
            sh 'mvn -f payment-service/pom.xml test'
        }

        stage ('Account Tests') {
            checkout scm
            sh 'mvn -f account-service/pom.xml test'
        }

        stage ('Token Tests') {
            checkout scm
            sh 'mvn -f token-service/pom.xml test'
        }

        stage ('Report Tests') {
            checkout scm
            sh 'mvn -f report-service/pom.xml test'
        }
        
        stage ('Client Tests (end-to-end)') {
            checkout scm
            sh 'mvn -f client/pom.xml test'
        } */

    }
} finally {
    node {
        stage ('Deployment finished') {
            checkout scm
            sh 'echo "Deployment finished!"'
        }
    }
}