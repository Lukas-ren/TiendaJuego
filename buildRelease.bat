@echo off
REM Build script for TiendaJuego - Firma Digital
REM Usage: buildRelease.bat [jdk-path]
REM Example: buildRelease.bat "C:\Program Files\Java\jdk-17"

setlocal enabledelayedexpansion

if "%1"=="" (
    echo Uso: buildRelease.bat [ruta-jdk]
    echo Ejemplo: buildRelease.bat "C:\Program Files\Java\jdk-17"
    echo.
    echo Si no especifica JDK, se usará el JAVA_HOME actual:
    echo Current JAVA_HOME: %JAVA_HOME%
    echo.
    set /p usejdk="¿Desea continuar con el JAVA_HOME actual? (S/N): "
    if /i not "!usejdk!"=="S" exit /b 1
) else (
    set JAVA_HOME=%1
    echo JAVA_HOME configurado a: %JAVA_HOME%
)

echo.
echo ============================================
echo   TiendaJuego - Build Release Firmado
echo ============================================
echo.

REM Verificar que exista Java
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java no encontrado en PATH
    echo Asegúrese de que JAVA_HOME está configurado correctamente
    exit /b 1
)

echo Versión de Java:
java -version

REM Compilar APK release
echo.
echo ============================================
echo   Compilando APK Release (Firmado)...
echo ============================================
echo.
call .\gradlew.bat clean assembleRelease

if errorlevel 1 (
    echo Error: Compilación falló
    exit /b 1
)

REM Compilar AAB release
echo.
echo ============================================
echo   Compilando AAB Release (Play Store)...
echo ============================================
echo.
call .\gradlew.bat bundleRelease

if errorlevel 1 (
    echo Error: Compilación de AAB falló
    exit /b 1
)

REM Mostrar resultados
echo.
echo ============================================
echo   ✓ Compilación Exitosa
echo ============================================
echo.
echo APK: app\build\outputs\apk\release\app-release.apk
echo AAB: app\build\outputs\bundle\release\app-release.aab
echo.
pause
