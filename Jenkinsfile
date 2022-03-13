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
                    success {
                      // publish html
                      publishHTML target: [
                          allowMissing: false,
                          alwaysLinkToLastBuild: false,
                          keepAll: true,
                          reportDir: 'coverage',
                          reportFiles: 'index.html',
                          reportName: 'RCov Report'
                        ]
                    }
                 }
            }
        }
}
