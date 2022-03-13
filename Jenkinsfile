pipeline {
    agent any
        stages {
            tools {
                 maven 'apache-maven-3.8.4' 
             }
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
