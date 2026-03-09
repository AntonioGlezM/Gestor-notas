# 📝 Sistema de Gestión de Notas (Aplicación de Escritorio en Java)

## Descripción

Esta aplicación es un gestor de notas desarrollado en Java con interfaz gráfica (Swing).

Permite a los usuarios:

- Registrarse en el sistema.
- Iniciar sesión de forma segura.
- Crear, editar y eliminar notas.
- Buscar notas por título en tiempo real.
- Limpiar los campos del formulario.
- Borrar todas las notas con confirmación.
- Cerrar sesión y cambiar de usuario.

El sistema es multiusuario y utiliza persistencia mediante ficheros de texto.

---

## Cómo Ejecutar el Proyecto

1. Abrir el proyecto en un entorno como Visual Studio Code, IntelliJ IDEA o Eclipse.
2. Ejecutar la clase `Main`.
3. Se abrirá la ventana de inicio de sesión. He creado el usuario josue@gmail.com para que lo uses si quieres testear. La contraseña es 1234.
4. Desde ahí se puede registrar un usuario o iniciar sesión.

No se necesita ninguna configuración adicional.

Requiere tener instalado Java (JDK 8 o superior).

---

## Estructura del Proyecto

El proyecto está organizado por paquetes siguiendo una estructura modular:

### 🔹 `main`
Contiene la clase `Main`, que es el punto de entrada del programa.

### 🔹 `ui`
Contiene las interfaces gráficas:
- `LoginFrame` → Ventana de inicio de sesión y registro.
- `NotasFrame` → Ventana principal para gestionar las notas.

### 🔹 `service`
Contiene la lógica del sistema:
- `UsuarioService` → Registro y autenticación de usuarios.
- `NotaService` → Gestión de notas (crear, leer, editar, eliminar).

### 🔹 `model`
Contiene las clases de datos:
- `Usuario` → Representa un usuario del sistema.

### 🔹 `utils`
Contiene clases de utilidad:
- `EmailUtils` → Sanitización del email para nombres de carpeta.
- `HashUtils` → Generación de hash SHA-256 para contraseñas.
- `Validaciones` → Métodos auxiliares de validación.

---

## 💾 Persistencia de Datos

La información se almacena en ficheros de texto dentro de la carpeta:
