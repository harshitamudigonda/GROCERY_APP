pipeline {
    agent any

    environment {
        TOMCAT_DIR   = "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1"
        FRONTEND_SRC = "FRONTEND\\grocerylist"
        BACKEND_SRC  = "BACKEND\\GroceryListApp"
    }

    stages {
        // ===== PARALLEL BUILD =====
        stage('Build Frontend and Backend') {
            parallel {
                stage('Build Frontend') {
                    steps {
                        dir("${env.FRONTEND_SRC}") {
                            bat 'if not exist node_modules ( npm install )'
                            bat 'npm run build'
                        }
                    }
                }
                stage('Build Backend') {
                    steps {
                        dir("${env.BACKEND_SRC}") {
                            bat 'mvn clean package -DskipTests'
                        }
                    }
                }
            }
        }

        // ===== DEPLOY FRONTEND =====
        stage('Deploy Frontend') {
            steps {
                bat """
                    set DEST=%TOMCAT_DIR%\\webapps\\grocery-list
                    if exist "%DEST%" rmdir /S /Q "%DEST%"
                    mkdir "%DEST%"
                    xcopy ${env.FRONTEND_SRC}\\dist "%DEST%\\" /E /I /Y
                """
            }
        }

        // ===== DEPLOY BACKEND =====
        stage('Deploy Backend') {
            steps {
                bat """
                    set WAR_SRC=${env.BACKEND_SRC}\\target\\GroceryApp.war
                    set WAR_DEST=%TOMCAT_DIR%\\webapps\\GroceryListApp.war
                    if exist "%WAR_DEST%" del /Q "%WAR_DEST%"
                    if exist "%TOMCAT_DIR%\\webapps\\GroceryListApp" rmdir /S /Q "%TOMCAT_DIR%\\webapps\\GroceryListApp"
                    copy "%WAR_SRC%" "%TOMCAT_DIR%\\webapps\\"
                """
            }
        }
    }

    post {
        success {
            echo '✅ Deployment Successful!'
        }
        failure {
            echo '❌ Pipeline Failed!'
        }
    }
}
