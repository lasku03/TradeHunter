#!/bin/bash

# Definir los parámetros
projectKey="TradeHunterAI"
projectName="TradeHunterAI"
sonarHostUrl="http://tradehunter.duckdns.org:9090"
sonarToken="sqp_e1f7da136ff093930448ea1c8711c02363792401"

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
