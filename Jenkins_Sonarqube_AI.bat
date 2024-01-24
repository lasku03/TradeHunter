@echo off

:: Definir los parámetros
set projectKey=TradeHunterAI
set sonarHostUrl=http://tradehunter.duckdns.org:9090
set sonarToken=sqp_6cd9a8d70893a822b4f20768fc900ad55d1c1de3

sonar-scanner.bat -D"sonar.projectKey=%projectKey%" -D"sonar.sources=." -D"sonar.host.url=%sonarHostUrl%" -D"sonar.token=%sonarToken%"

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
