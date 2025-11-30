#!/usr/bin/env pwsh
<#
.SYNOPSIS
    Build release APK/AAB para TiendaJuego con firma digital.

.DESCRIPTION
    Compila APK y AAB firmados con keystore.jks.
    Requiere JDK-17 (o compatible) por incompatibilidad con JDK-22.

.PARAMETER JdkPath
    Ruta al directorio JDK. Si no especifica, usa JAVA_HOME actual.

.EXAMPLE
    .\buildRelease.ps1
    .\buildRelease.ps1 -JdkPath "C:\Program Files\Java\jdk-17"

.NOTES
    Keystore info: keystore.properties
    Output: app/build/outputs/apk|bundle/release/
#>

param(
    [string]$JdkPath
)

$ErrorActionPreference = "Stop"
$VerbosePreference = "Continue"

# Colors
$Green = "`e[32m"
$Red = "`e[31m"
$Yellow = "`e[33m"
$Reset = "`e[0m"

function Write-Header {
    param([string]$Message)
    Write-Host "`n$Green========================================$Reset"
    Write-Host "$Green  $Message$Reset"
    Write-Host "$Green========================================$Reset`n"
}

function Write-Error-Custom {
    param([string]$Message)
    Write-Host "$Red✗ Error: $Message$Reset" -ForegroundColor Red
}

function Write-Success {
    param([string]$Message)
    Write-Host "$Green✓ $Message$Reset" -ForegroundColor Green
}

# Configurar JAVA_HOME si se proporciona
if ($JdkPath) {
    $env:JAVA_HOME = $JdkPath
    Write-Host "$Yellow[INFO] JAVA_HOME configurado a: $JdkPath$Reset"
}

# Verificar Java
Write-Host "`n$Yellow[INFO] Verificando Java...$Reset"
try {
    $javaVersion = java -version 2>&1 | Select-String "version"
    Write-Host "$Yellow[INFO] $javaVersion$Reset"
} catch {
    Write-Error-Custom "Java no encontrado. Asegúrese de que JAVA_HOME está configurado correctamente."
    exit 1
}

# Verificar keystore
Write-Host "$Yellow[INFO] Verificando keystore...$Reset"
if (-not (Test-Path "keystore.jks")) {
    Write-Error-Custom "keystore.jks no encontrado. Ejecute keytool primero."
    exit 1
}
Write-Success "keystore.jks encontrado"

# Compilar APK
Write-Header "Compilando APK Release (Firmado)"
& .\gradlew.bat clean assembleRelease
if ($LASTEXITCODE -ne 0) {
    Write-Error-Custom "Compilación de APK falló"
    exit 1
}
Write-Success "APK compilado"

# Compilar AAB
Write-Header "Compilando AAB Release (Play Store)"
& .\gradlew.bat bundleRelease
if ($LASTEXITCODE -ne 0) {
    Write-Error-Custom "Compilación de AAB falló"
    exit 1
}
Write-Success "AAB compilado"

# Mostrar resultados
Write-Header "✓ Compilación Exitosa"
Write-Host "APK (debug):   $Green$(Resolve-Path 'app/build/outputs/apk/release/app-release.apk' 2>/dev/null)$Reset"
Write-Host "AAB (upload):  $Green$(Resolve-Path 'app/build/outputs/bundle/release/app-release.aab' 2>/dev/null)$Reset"

Write-Host "`n$Yellow[INFO] Próximos pasos:$Reset"
Write-Host "  1. Registrar SHA-256 en Google Play Console"
Write-Host "  2. Subir AAB a Play Store"
Write-Host "  3. O distribuir APK manualmente"
