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
} else {
    # Si hubo un error en la construcción del JAR, mostrar mensaje de error
    Write-Host "Error during the building of Trade Hunter JAR file." -BackgroundColor Red
    Exit $LASTEXITCODE
}
