pipeline {
    agent any
        tools {
            maven 'Maven_3.8.4'
            jdk 'JDK_17'
            
        }
        stages {
            stage ('Compile Stage') {
                steps {
                      bat'mvn clean compile'
                }
            }
            stage ('Testing Stage') {
                steps {
                        bat'mvn test'
                }
            }
            
            stage ('Sonarqube Analysis') {
                agent any
                    steps {
                      withSonarQubeEnv('sonarqube') {
                        bat 'mvn clean package sonar:sonar'
                      }
                    }
            }
        }
}
