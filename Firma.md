# Firma Digital de TiendaJuego

## Estado Actual

✅ **Keystore generado**: `keystore.jks`  
✅ **Configuración de firma**: `keystore.properties` y `app/build.gradle.kts`  
⏸️ **Compilación release**: Bloqueada por incompatibilidad JDK-22 (ver solución abajo)

---

## Información del Keystore

### Credenciales
```
Store File:       keystore.jks
Store Password:   tienda2025
Key Alias:        tiendajuego
Key Password:     tienda2025
```

### Huellas Digitales (Certificate Fingerprints)

**SHA-1**:
```
81:98:AF:8C:6D:41:A6:C5:71:A5:EC:DA:FB:F4:49:A6:B9:B7:E7:72
```

**SHA-256** (para Google Play):
```
95:FB:D1:DC:DC:62:86:76:CE:1B:92:92:4C:6D:33:C5:F3:E4:0D:99:EB:7D:40:7D:76:10:25:FB:77:09:9F:21
```

**Algoritmo**: SHA384withRSA  
**Key Size**: 2048-bit RSA  
**Validez**: 10,000 días (27+ años)  
**Certificado**: Self-signed (prueba/desarrollo)

---

## Archivos Generados

### 1. `keystore.jks`
Almacén de claves privadas. **NUNCA** debe ser versionado en Git.

```
# .gitignore (ya configurado)
keystore.jks
keystore.properties
```

### 2. `keystore.properties`
Configuración de credenciales para Gradle.

```properties
storeFile=keystore.jks
storePassword=tienda2025
keyAlias=tiendajuego
keyPassword=tienda2025
```

**NOTA**: En producción, use variables de entorno o gestores de secretos (no versionado).

### 3. `app/build.gradle.kts` (Actualizado)

```kotlin
// Imports
import java.io.FileInputStream
import java.util.Properties

// signingConfigs { create("release") { ... } }
// buildTypes { release { signingConfig = signingConfigs.getByName("release") } }
```

---

## Compilación de APK/AAB Firmado

### Requisito Previo: Resolver Incompatibilidad JDK

**Problema Actual**:
- JDK-22 + AGP 8.1.1 + compileSdk=34 = jlink.exe falla

**Solución**:
Instalar JDK-17 (o JDK-11):

#### Windows (PowerShell)
```powershell
# 1. Descargar JDK-17 desde:
# https://www.oracle.com/java/technologies/javase-jdk17-downloads.html
# o Eclipse Temurin: https://adoptium.net/

# 2. Instalar a C:\Program Files\Java\jdk-17

# 3. Actualizar gradle.properties
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"

# 4. Verificar
java -version  # Debe mostrar "17.x.x"
```

#### Linux/Mac
```bash
# Instalar JDK-17
sudo apt-get install openjdk-17-jdk  # Ubuntu/Debian
# o
brew install openjdk@17  # macOS

# Actualizar PATH
export JAVA_HOME=/usr/libexec/java_home -v 17
```

### Compilar APK (Release Firmado)

Una vez resuelta la versión de Java:

```bash
cd C:\Users\Ignacio Ruiz\Downloads\TiendaJuego-masterRe

# Compilar APK firmado
.\gradlew.bat assembleRelease

# Resultado: app/build/outputs/apk/release/app-release.apk
```

### Compilar AAB (Android App Bundle - para Play Store)

```bash
.\gradlew.bat bundleRelease

# Resultado: app/build/outputs/bundle/release/app-release.aab
```

---

## Verificar Firma del APK/AAB

```bash
# Listar firma del APK
keytool -printcert -jarfile app\build\outputs\apk\release\app-release.apk

# Listar firmas internas
unzip -l app\build\outputs\apk\release\app-release.apk | find "META-INF"
```

---

## Google Play Console: Configuración Final

### 1. Registrar Certificado SHA-256

En **Google Play Console** → App Settings → App signing:

```
SHA-256 (upload key):
95:FB:D1:DC:DC:62:86:76:CE:1B:92:92:4C:6D:33:C5:F3:E4:0D:99:EB:7D:40:7D:76:10:25:FB:77:09:9F:21
```

### 2. Subir AAB Firmado

```
App releases → Create new release → Upload AAB
→ app/build/outputs/bundle/release/app-release.aab
```

### 3. Validar en Google Play

Google automáticamente valida la firma y la registra. Luego Play aplica su propia firma de distribución (diferente a upload key).

---

## Seguridad: Mejores Prácticas

### ⚠️ Para Desarrollo (Actual)

✅ Credenciales en `keystore.properties`  
✅ Almacén local  
✅ Self-signed certificate  

### 🔒 Para Producción

```bash
# 1. Usar variables de entorno (CI/CD)
export KEYSTORE_PASSWORD="..."
export KEY_ALIAS_PASSWORD="..."

# 2. O gestores de secretos (GitHub Secrets, CircleCI Env, etc.)
# 3. Nunca versionear keystore.jks ni keystore.properties
# 4. Usar certificados emitidos por CA (si es aplicación corporativa)
```

### .gitignore (Verificar)

```
# En raíz del proyecto
keystore.jks
keystore.properties
*.jks
*.keystore
```

---

## Troubleshooting

### Error: "jlink.exe transformation failed"
→ Instalar JDK-17 y actualizar `JAVA_HOME`

### Error: "keystore.properties not found"
→ Crear manualmente con las credenciales arriba

### Error: "Invalid alias or password"
→ Verificar `keystore.properties` vs valores en keystore.jks

### Error: "storeFile not found"
→ Asegurarse que `keystore.jks` está en raíz del proyecto

---

## Resumen de Comandos (Cheat Sheet)

```bash
# Generar nuevo keystore (interactivo)
keytool -genkeypair -v -keystore keystore.jks -storepass tienda2025 \
  -keypass tienda2025 -alias tiendajuego -keyalg RSA -keysize 2048 \
  -validity 10000 -dname "CN=TiendaJuego,O=Development,C=ES"

# Ver huellas del keystore
keytool -list -v -keystore keystore.jks -storepass tienda2025 -alias tiendajuego

# Compilar APK debug
./gradlew assembleDebug

# Compilar APK release (firmado)
./gradlew assembleRelease

# Compilar AAB release (Play Store)
./gradlew bundleRelease

# Ver firma de APK
keytool -printcert -jarfile app/build/outputs/apk/release/app-release.apk

# Cambiar versión app
# En app/build.gradle.kts: versionCode += 1, versionName = "1.1.0"
./gradlew clean assembleRelease
```

---

## Próximos Pasos

1. ✅ Instalar JDK-17
2. ✅ Ejecutar `.\gradlew.bat assembleRelease`
3. ✅ Obtener APK firmado en `app/build/outputs/apk/release/app-release.apk`
4. ✅ Registrar SHA-256 en Google Play Console
5. ✅ Subir AAB a Play Store

---

**Documentación generada**: 30-NOV-2025  
**Proyecto**: TiendaJuego  
**Versión**: 1.0 (API 34)
