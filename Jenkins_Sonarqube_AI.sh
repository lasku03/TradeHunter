#!/bin/bash

# Definir los parámetros
projectKey="TradeHunterAI"
sonarHostUrl="http://tradehunter.duckdns.org:9090"
sonarToken="sqp_6cd9a8d70893a822b4f20768fc900ad55d1c1de3"

cd 03_Web/influxdb
sudo coverage run -m unittest discover

sonarCommand="sonar-scanner -Dsonar.projectKey=${projectKey} -Dsonar.sources=. -Dsonar.host.url=${sonarHostUrl} -Dsonar.token=${sonarToken}"

# Ejecutar el comando Maven
eval "$sonarCommand"

# Verificar el código de salida del comando
if [ $? -eq 0 ]; then
    echo "Sonarqube's static analysis of the project has been correct."
    cd ./../..
    exit 0
else
    echo "Error during Sonarqube's static analysis of the project. Exiting..."
    cd ./../..
    exit $?
fi
