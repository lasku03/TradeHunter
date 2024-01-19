#!/bin/bash

# Definir los parámetros
projectKey="TradeHunter-Rest"
projectName="TradeHunter-Rest"
sonarHostUrl="http://tradehunter.duckdns.org:9090"
sonarToken="sqp_da68d7414f64af769598308acaa1f089006ed0a7"

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
