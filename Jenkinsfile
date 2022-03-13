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
                post {
                    always { 
                        junit 'test-results.xml'   
                    }   
                 }
            }
        }
}
