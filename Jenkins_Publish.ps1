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
    $doc = [xml](Get-Content ($directorioProyecto + "/pom.xml")) 
    $versionPOM = $doc.project.version
    
    $sourceJar = Join-Path -Path $directorioProyecto -ChildPath "target/tradehunter.demo-$versionPOM.jar"
    #Copy to the Web foled of TradeHunterAI
    $destinationFolder = "/home/trade_hunter_ai/web"
    Move-Item -Path $sourceJar -Destination $destinationFolder -Force

    if ($LASTEXITCODE -eq 0) {
        Get-Item "$destinationFolder/tradehunter.demo-$versionPOM.jar" | Set-Item -Force -Permission 777
        Write-Host "Succesfully moved the file to $destinationFolder and given all permisions." -BackgroundColor Green
    }
    else {
        Write-Host "Error moving the file to $destinationFolder." -BackgroundColor Red
    }
}
else {
    # Si hubo un error en la construcción del JAR, mostrar mensaje de error
    Write-Host "Error during the building of Trade Hunter JAR file." -BackgroundColor Red
    Exit $LASTEXITCODE
}
