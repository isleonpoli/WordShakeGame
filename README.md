# 🧠 WordShake

WordShake es un juego de palabras interactivo desarrollado en Java usando JavaFX. El objetivo del juego es formar la mayor cantidad de palabras válidas en una cuadrícula de letras en un tiempo limitado. Cada palabra válida otorga puntos, y al finalizar el tiempo, la puntuación se guarda en el perfil del usuario.

---

## 🎮 Características

- ⏱ Temporizador de 3 minutos por partida
- 🔠 Generación aleatoria de letras en una cuadrícula 5x5
- ✅ Verificación de palabras contra una base de datos
- 🧾 Registro en tabla de palabras válidas encontradas
- 📊 Acumulación y guardado de puntajes
- 👤 Integración con perfil de usuario
- 🌙 Interfaz gráfica amigable con JavaFX

---

## 🛠 Requisitos

- Java 17 o superior
- JavaFX 21
- MySQL (para base de datos de palabras y usuarios)
- IDE recomendado: IntelliJ IDEA o VSCode
- Conexión a base de datos configurada (ver `dbMethods.java`)

---

## 🚀 Cómo ejecutar el proyecto

1. **Clona este repositorio:**

```bash
git clone https://github.com/tuusuario/wordshake.git
```

2. **Importa el proyecto en tu IDE.**

3. **Configura tu entorno JavaFX.**

-  Asegúrate de tener instalado JavaFX 21 y que tu IDE lo reconozca.


4. **Configura la base de datos.**

-  Crea una base de datos PostgrestSQL.

-  Asegúrate de tener una tabla de usuarios y una tabla de palabras válidas con sus puntajes.

Modifica la clase conexion/dbMethods.java para establecer tu conexión:

```bash
java
Copiar
Editar
String url = "jdbc:mysql://localhost:3306/tu_base_datos";
String user = "tu_usuario";
String password = "tu_contraseña";
Ejecuta la aplicación desde App.java o la clase principal que use Application.launch().
```

🗂 Estructura del proyecto
WordShake/
│
├── ejemplo/                # Controladores JavaFX (playScreenController, etc.)
├── model/                  # Clases de modelo como wordFound
├── conexion/               # Métodos de acceso a base de datos (dbMethods)
├── resources/              # Archivos FXML, imágenes y recursos
├── App.java                # Clase principal que lanza la aplicación
└── README.md               # Este archivo

✍️ Autores
Ivan Santiago Leon Garcia - Desarrollador.
Juan Camilo Guzman Parra - Desarrollador.
Vanessa Gomez Ruiz - Desarrollador.

🙋‍♂️ ¿Contribuciones?
¡Claro! Si deseas colaborar, abre un pull request o crea un issue. Las ideas para mejorar la interfaz, agregar más palabras, o añadir nuevos modos de juego son bienvenidas.
