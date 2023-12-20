@echo off

:: Definir los parámetros
set projectKey=TradeHunter
set projectName=TradeHunter
set sonarHostUrl=http://tradehunter.duckdns.org:9090
set sonarToken=sqp_446b24c9e7d429a2ab2df19ddd7e3438869ca294

:: Comando Maven
set sonarCommand=mvn clean verify sonar:sonar -f "03_Web\tradehunter.demo\pom.xml" -Dsonar.projectKey=%projectKey% -Dsonar.projectName="%projectName%" -Dsonar.host.url=%sonarHostUrl% -Dsonar.token=%sonarToken%

:: Ejecutar el comando Maven
%sonarCommand%

:: Verificar el código de salida del comando
if %errorlevel% equ 0 (
    echo Sonarqube's static analysis of the project has been correct.
    exit /b 0
) else (
    echo Error during Sonarqube's static analysis of the project. Exiting...
    exit /b %errorlevel%
)
