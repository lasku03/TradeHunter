# Ruta al directorio del proyecto Java
$directorioProyecto = Join-Path -Path $PSScriptRoot -ChildPath "03_Web/tradehunter.demo"

# Cambiar al directorio del proyecto
Set-Location -Path $directorioProyecto

# Compilar el proyecto Java
Write-Host "Testing Trade Hunter project..." -BackgroundColor Cyan
mvn test

if ($LASTEXITCODE -eq 0) {
    # Si los tests se ejecutaron sin errores
    Write-Host "All tests passed successfully" -BackgroundColor Green
} else {
    # Si hubo errores durante la ejecución de los tests
    Write-Host "Some tests failed. Check the output for details." -BackgroundColor Red
    Exit
}