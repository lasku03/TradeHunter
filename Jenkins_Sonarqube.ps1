$sonarCommand = "mvn clean verify sonar:sonar -f 03_Web/tradehunter.demo/pom.xml -Dsonar.projectKey=TradeHunter -Dsonar.projectName='TradeHunter' -Dsonar.host.url=http://tradehunter.duckdns.org:9090 -Dsonar.token=sqp_446b24c9e7d429a2ab2df19ddd7e3438869ca294"

# Ejecuta el comando Maven
Invoke-Expression -Command $sonarCommand

# Verifica el código de salida del comando
if ($LastExitCode -eq 0) {
    Write-Host "Sonarqube's static analysis of the project has been correct." -BackgroundColor Green
} else {
    Write-Host "Error during Sonarqube's static analysis of the project. Exiting..." -BackgroundColor Red
    Exit
}
