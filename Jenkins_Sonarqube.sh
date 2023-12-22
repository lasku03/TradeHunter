#!/bin/bash

# Definir los parámetros
projectKey="TradeHunter"
projectName="TradeHunter"
sonarHostUrl="http://tradehunter.duckdns.org:9090"
sonarToken="sqp_8d39669dbee2d132b4e24a9cd4a2db45b8d050b7"

# Comando Maven
sonarCommand="mvn clean verify sonar:sonar -f '03_Web/tradehunter.demo/pom.xml' -Dsonar.projectKey=${projectKey} -Dsonar.projectName='${projectName}' -Dsonar.host.url=${sonarHostUrl} -Dsonar.login=${sonarToken}"

# Ejecutar el comando Maven
eval "$sonarCommand"

# Verificar el código de salida del comando
if [ $? -eq 0 ]; then
    echo "Sonarqube's static analysis of the project has been correct."
    exit 0
else
    echo "Error during Sonarqube's static analysis of the project. Exiting..."
    exit $?
fi
