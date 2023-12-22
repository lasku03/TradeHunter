@echo off

:: Definir los parámetros
set projectKey=TradeHunter
set projectName=TradeHunter
set sonarHostUrl=http://tradehunter.duckdns.org:9090
set sonarToken=sqp_8d39669dbee2d132b4e24a9cd4a2db45b8d050b7

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
