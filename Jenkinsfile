pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh 'echo Building Token Service'
        sh 'set -e'
        sh 'pushd token-service'
        sh 'mvn package'
        sh 'popd'
      }
    }
    stage('deploy') {
      steps {
      sh 'echo Deploying Token Service'
      sh 'set -e'
      sh 'pushd token-service'
      sh 'docker-compose up --build'
      sh 'popd'
      }
    }
  }
}