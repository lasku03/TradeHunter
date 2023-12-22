# Ruta al directorio del proyecto Java
$directorioProyecto = Join-Path -Path $PSScriptRoot -ChildPath "03_Web/tradehunter.demo"

# Cambiar al directorio del proyecto
Set-Location -Path $directorioProyecto

# Empaquetar el proyecto Java en un archivo JAR
Write-Host "Building Trade Hunter project..." -BackgroundColor Cyan
mvn package

if ($LASTEXITCODE -eq 0) {
    # Si la construcción del JAR fue exitosa, mostrar mensaje
    Write-Host "Successfully built JAR file." -BackgroundColor Green

    # Mover el archivo JAR a la carpeta /home/trade_hunter_ai/web
    # Obtener la versión del archivo POM
    $versionPOM = (Select-String -Path pom.xml -Pattern '<version>(.*?)<\/version>').Matches.Groups[1].Value

    # Buscar el archivo JAR con la versión específica en el directorio 'target'
    $sourceJar = Get-ChildItem -Path "./target/" -Filter ("tradehunter.demo-$versionPOM.jar") -Recurse | Select-Object -First 1
    
    #Copy to the Web foled of TradeHunterAI
    $destinationFolder = "/home/trade_hunter_ai/web"

    Move-Item -Path $sourceJar.FullName -Destination $destinationFolder -Force
}
else {
    # Si hubo un error en la construcción del JAR, mostrar mensaje de error
    Write-Host "Error during the building of Trade Hunter JAR file." -BackgroundColor Red
    Exit $LASTEXITCODE
}
