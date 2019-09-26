pipeline{
    agent{
        label 'Android'
    }
    options{
        // Stop the build early in case of compile or test failures
        skipStagesAfterUnstable()
    }
    stages{
        stage("Clone Repo"){
            steps{
                git 'http://192.168.0.40/root/sram-android.git'
            }
        }
        stage("Compile"){
            steps{
                sh 'chmod +x gradlew'
                sh './gradlew compileDebugSources'
            }
        }
        stage("Unit Testing"){
            steps{
                sh './gradlew testDebugUnitTest testDebugUnitTest'
            }
        }
        stage("Build APK"){
            steps{
                sh './gradlew assembleDebug'
                archiveArtifacts '**/*.apk'
            }
            post {
                success {
                    // Notify if the upload succeeded
                        mail to: 'krishnatulasi33.kt@gmail.com', subject: 'New build available!', body: 'Check it out!'
                }
            }
        }
    }
    post {
        failure {
                // Notify developer team of the failure
            mail to: 'suresh.uppapalli@gmail.com,krishnatulasi33.kt@gmail.com', subject: 'Oops!', body: "Build ${env.BUILD_NUMBER} failed; ${env.BUILD_URL}"
        }
    }
}
