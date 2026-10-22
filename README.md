# StingTech Solutions

**Aplicación móvil Android para gestionar solicitudes de soporte técnico y reparación de computadores.**

StingTech Solutions permite registrar solicitudes de reparación, consultar su historial, acceder a herramientas del dispositivo y utilizar un sistema local de registro e inicio de sesión. Fue desarrollada como proyecto académico para la asignatura **DSY1105**, utilizando **Kotlin, Jetpack Compose, Material 3, arquitectura MVVM y Room**.

> **Alcance:** prototipo académico funcional. Los datos se almacenan localmente en el dispositivo; no existe servidor ni sincronización en la nube.

## Objetivo

Ofrecer una interfaz simple para que una persona pueda informar problemas de su computador y consultar las solicitudes que se han registrado desde la aplicación. El proyecto también demuestra navegación entre pantallas, validación de formularios, persistencia local, administración de estado y uso de funciones nativas de Android.

## Funcionalidades implementadas

### Bienvenida

- Identidad visual **StingTech Solutions**, con logo y colores azul oscuro y naranjo.
- Acceso a **Iniciar sesión**, **Registrarse** y **Probar StingTech (demo)**.

### Registro e inicio de sesión

- Formulario de registro con nombre, correo, contraseña y confirmación de contraseña.
- Validación de campos obligatorios, formato del correo, longitud mínima de contraseña y coincidencia de contraseñas.
- Registro de cuentas en una tabla local de **Room**.
- Comprobación de credenciales al iniciar sesión.
- Almacenamiento de la contraseña mediante **PBKDF2-HMAC-SHA256 con sal aleatoria**, sin guardar la contraseña en texto plano.
- Mensajes ante errores de validación o credenciales incorrectas.

### Menú principal

- Pantalla de inicio con bienvenida personalizada para una sesión iniciada.
- Acceso a nueva solicitud, historial, herramientas del dispositivo y perfil.
- Contador de solicitudes registradas.

### Solicitudes de reparación

- Formulario para registrar equipo, tipo de equipo y descripción del problema.
- Validación de los campos de la solicitud.
- Persistencia de solicitudes mediante **Room**.
- Historial con los registros almacenados y su estado.
- Conservación de los datos tras cerrar y volver a abrir la aplicación.

### Mi perfil

- Visualización del nombre y correo de la cuenta autenticada.
- Acceso al perfil desde el menú principal.
- Botón para cerrar la sesión actual y regresar a la bienvenida.
- El perfil no muestra contraseñas ni hashes.

### Modo demostración

- Acceso al menú sin crear una cuenta.
- Identificación del visitante como **Invitado** cuando no existe una sesión autenticada.
- Acceso a las pantallas principales para probar el funcionamiento de la aplicación.

### Herramientas nativas

- Consulta del nivel de batería del dispositivo mediante **BatteryManager**.
- Prueba de vibración mediante las APIs nativas de Android, cuando el dispositivo es compatible.
- Uso de **AnimatedVisibility** para animaciones de interfaz.

## Tecnologías utilizadas

| Tecnología | Uso |
| --- | --- |
| Kotlin | Lenguaje de programación |
| Android Studio | Entorno de desarrollo |
| Jetpack Compose | Construcción declarativa de interfaces |
| Material 3 | Componentes y estilo visual |
| Navigation Compose | Navegación entre pantallas |
| MVVM | Separación entre vistas, estado y lógica |
| ViewModel y State | Gestión del estado de la interfaz |
| Room | Persistencia de usuarios y solicitudes |
| DAO y Repository | Acceso y organización de datos |
| Kotlin Coroutines y Flow | Operaciones asíncronas y lectura reactiva |
| KSP | Procesamiento de anotaciones de Room |
| BatteryManager / VibratorManager | Acceso a recursos nativos |
| Git y GitHub | Control de versiones |
| Trello | Organización de tareas y épicas |

## Arquitectura

El proyecto utiliza una organización basada en **MVVM (Model–View–ViewModel)** con capas de acceso a datos:

```text
Pantallas (Jetpack Compose)
          |
          v
ViewModels (estado, validaciones y acciones)
          |
          v
Repositories (operaciones de datos)
          |
          v
DAO (consultas e inserciones)
          |
          v
Room (SQLite local)
```

Organización principal del código:

```text
app/src/main/
├── java/com/example/stingtechsolution/
│   ├── MainActivity.kt
│   ├── navigation/
│   │   └── AppNavigation.kt
│   ├── screen/
│   │   ├── LoginScreen.kt
│   │   ├── RegisterScreen.kt
│   │   ├── HomeScreen.kt
│   │   ├── NuevaSolicitudScreen.kt
│   │   ├── MisSolicitudesScreen.kt
│   │   ├── HerramientasScreen.kt
│   │   └── PerfilScreen.kt
│   ├── viewmodel/
│   │   ├── AuthViewModel.kt
│   │   └── SolicitudViewModel.kt
│   ├── model/
│   │   └── Solicitud.kt
│   ├── data/
│   │   ├── entity/
│   │   │   ├── SolicitudEntity.kt
│   │   │   └── UsuarioEntity.kt
│   │   ├── dao/
│   │   │   ├── SolicitudDao.kt
│   │   │   └── UsuarioDao.kt
│   │   ├── database/
│   │   │   └── StingTechDatabase.kt
│   │   ├── repository/
│   │   │   ├── SolicitudRepository.kt
│   │   │   └── UsuarioRepository.kt
│   │   └── security/
│   │       └── PasswordUtils.kt
│   └── ui/theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
├── res/
│   └── drawable/
│       └── stingtech_logo.*
└── AndroidManifest.xml
```

> La estructura representa los archivos principales de la versión desarrollada. Android Studio puede mostrar el código fuente agrupado como `kotlin+java` y los archivos de recursos en otras vistas.

## Flujo de navegación

```text
Bienvenida
├── Iniciar sesión ──> Login correcto ──> Home
├── Registrarse ──> Registro exitoso ──> Login
└── Probar StingTech (demo) ──> Home como Invitado

Home
├── Nueva solicitud
├── Mis solicitudes / Historial
├── Herramientas del dispositivo
└── Mi perfil ──> Salir al inicio
```

## Base de datos local

Se utiliza una base de datos Room denominada `stingtech_database`.

**Tabla `solicitudes`:** identificador, nombre de equipo, tipo de equipo, descripción y estado.

**Tabla `usuarios`:** identificador, nombre, correo único, sal y hash de contraseña.

El proyecto incorpora una migración de la versión 1 a la versión 2 para agregar usuarios sin eliminar la tabla de solicitudes existente.

## Requisitos para ejecutar el proyecto

- **Android Studio** con soporte para Kotlin y Jetpack Compose.
- **JDK 21**, de acuerdo con el entorno utilizado en el desarrollo.
- **Android SDK** y un emulador o dispositivo Android compatibles con la configuración Gradle del proyecto.
- Acceso a Internet durante la primera sincronización de dependencias de Gradle.

Las versiones concretas de Android Gradle Plugin, Kotlin, Compose, Room y SDK se encuentran definidas en los archivos Gradle del repositorio.

## Instalación y ejecución

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/Ridm2025/StingTech-AndroidStudio.git
   ```

2. Abrir **Android Studio**.
3. Seleccionar **File → Open** y elegir la carpeta del proyecto.
4. Esperar a que finalice la sincronización de **Gradle**.
5. Seleccionar un emulador o conectar un dispositivo Android con depuración USB habilitada.
6. Presionar **Run ▶** para instalar y abrir la aplicación.

## Cómo probar la aplicación

1. Abrir la pantalla de bienvenida.
2. Elegir **Registrarse** y crear una cuenta de prueba con un correo válido.
3. Iniciar sesión utilizando ese correo y contraseña.
4. Comprobar que el menú principal muestra el nombre de la cuenta.
5. Crear una solicitud de reparación.
6. Abrir **Mis solicitudes** para revisar el registro.
7. Cerrar y volver a ejecutar la aplicación para verificar que Room conserva los datos.
8. Revisar **Mi perfil**, la lectura de batería y la prueba de vibración.
9. Cerrar sesión y probar el acceso de demostración.

### Pruebas funcionales realizadas

En las pruebas manuales del proyecto se verificó:

- Validaciones en Login y Registro.
- Registro y acceso con cuentas almacenadas localmente.
- Presentación del nombre y correo en el perfil.
- Navegación entre menú, solicitudes, historial y herramientas.
- Persistencia de solicitudes después de reiniciar la aplicación.
- Consulta de batería y respuesta de la herramienta de vibración según compatibilidad del dispositivo.
- Retorno a la bienvenida al salir del perfil.

## Diseño de interfaz

Se definió una identidad visual basada en el logo **StingTech Solutions**:

- Azul oscuro como color principal.
- Naranjo como acento en botones y elementos destacados.
- Fondos claros, tarjetas redondeadas y componentes Material 3.
- Formularios con mensajes de validación.
- Navegación simple entre las funciones principales.

### Capturas de pantalla

Agregar capturas reales de la aplicación en una carpeta `docs/capturas/` y enlazarlas aquí. Se recomienda incluir: **Bienvenida**, **Login**, **Registro**, **Home**, **Nueva solicitud**, **Historial**, **Perfil** y **Herramientas**.

### Mockups

Los mockups de interfaz se utilizan para representar el diseño previsto. Deben distinguirse de las capturas de la aplicación funcional: un mockup puede incluir elementos conceptuales que no estén implementados.

## Gestión del proyecto

El trabajo se organizó mediante un tablero de **Trello** y control de versiones con **Git/GitHub**. Las áreas consideradas en la planificación incluyen:

1. Configuración inicial y planificación.
2. Diseño de interfaces y experiencia de usuario.
3. Registro, inicio de sesión y validaciones.
4. Navegación y arquitectura MVVM.
5. Gestión de solicitudes técnicas.
6. Persistencia local con Room.
7. Recursos nativos y animaciones.
8. Pruebas, documentación y entrega.

Se recomienda incluir aquí el enlace real al tablero de Trello si puede compartirse públicamente y conservar en GitHub los commits auténticos del desarrollo.

## Permisos

La aplicación solicita el permiso Android `VIBRATE` para utilizar la vibración en dispositivos compatibles. La lectura del nivel de batería y el uso de la base de datos local no requieren una cuenta ni conexión con un servicio remoto.

## Limitaciones conocidas

- **Almacenamiento local:** las cuentas y las solicitudes se conservan en el dispositivo o emulador donde se creó la información; no se sincronizan entre teléfonos.
- **Historial compartido:** las solicitudes todavía no están relacionadas con una cuenta específica. Diferentes usuarios del mismo dispositivo pueden ver el historial local compartido.
- **Sesión temporal:** los datos del usuario autenticado se mantienen en memoria mediante el ViewModel; no hay persistencia de sesión tras finalizar el proceso de la aplicación.
- **Autenticación educativa:** no hay verificación por correo, recuperación de contraseña, backend ni servicio de autenticación remoto.
- **Modo demo:** su propósito es recorrer las funciones sin iniciar sesión, no simular una cuenta registrada.
- **Vibración:** depende de la compatibilidad y configuración del dispositivo o emulador.

## Mejoras futuras

- Relacionar cada solicitud con el usuario que la creó.
- Agregar edición, seguimiento y actualización de estados de reparación.
- Crear notificaciones del progreso de los servicios.
- Incorporar persistencia de sesión y recuperación de contraseña.
- Integrar un backend para sincronizar información entre dispositivos.
- Añadir pruebas automatizadas unitarias y de interfaz.
- Mejorar accesibilidad y adaptación a distintos tamaños de pantalla.

## Contexto académico

Proyecto desarrollado para **DSY1105 – Evaluación Parcial 2**, enfocado en interfaces Android funcionales, navegación, formularios validados, MVVM, Room, recursos nativos, organización del proyecto y control de versiones.

**Autoría y contribuciones:** completar con el nombre del estudiante y, si corresponde, los integrantes que efectivamente participaron. No atribuir aportes a personas que no hayan contribuido.

## Estado del proyecto

**Prototipo funcional académico.** Las funciones principales se han probado manualmente; se recomienda ejecutar una última verificación en el computador y dispositivo de entrega.

---

**StingTech Solutions** — Soporte técnico de computadores desde Android.
