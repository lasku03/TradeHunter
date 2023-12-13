# Ruta al directorio del proyecto Java
$directorioProyecto = Join-Path -Path $PSScriptRoot -ChildPath "03_Web/tradehunter.demo"

# Cambiar al directorio del proyecto
Set-Location -Path $directorioProyecto

# Compilar el proyecto Java
Write-Host "Compiling Trade Hunter project..." -BackgroundColor Cyan
mvn compile

if ($LASTEXITCODE -eq 0) {
    # Si la compilación fue exitosa, ejecutar el programa
    Write-Host "Succesfully compiled" -BackgroundColor Green
} else {
    # Si hubo un error en la compilación, mostrar mensaje
    Write-Host "Error during the compilation of Trade Hunter." -BackgroundColor Red
    Exit
}
