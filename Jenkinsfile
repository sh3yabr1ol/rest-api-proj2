pipeline {
    agent any
        tools {
            maven 'apache-maven-3.8.4' 
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
            stage ('Install Stage') {
                steps {
                        bat'mvn install'
                }
            }
        }
}
