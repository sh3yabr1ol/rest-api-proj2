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
                        bat 'mvn clean package sonar:sonar -D sonar.login=admin -D sonar.password=Shey05121998! -D sonar.projectKey=sonarqubetest -D sonar.sources=src/main/java -D sonar.java.binaries=target/classes -D sonar.host.url=http://localhost:9000'
                      }
                    }
            }
        }
}
