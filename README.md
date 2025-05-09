# Juego de Colores - Aplicación Android en Kotlin

## Descripción
Este proyecto es una aplicación para Android desarrollada en Kotlin que implementa un juego interactivo de colores. El usuario debe presionar el botón que coincida con el color o texto que aparece en pantalla, con el objetivo de hacer la mayor cantidad de aciertos en 30 segundos.

## Características Principales

### Pantalla de Bienvenida
- Título atractivo del juego
- Explicación clara de las reglas
- Botón para iniciar el juego
- Animaciones de entrada para los elementos visuales

### Pantalla de Juego
- Cuadro principal que muestra colores (rojo, verde, azul, amarillo, morado, naranja)
- Botones interactivos para seleccionar el color
- Temporizador de cuenta regresiva de 30 segundos
- Contador de puntuación
- Efecto Stroop: el texto muestra un nombre de color en un color diferente, desafiando la percepción del jugador

### Pantalla de Resultados
- Muestra la puntuación final obtenida
- Muestra el récord guardado (puntuación más alta)
- Mensaje personalizado según el rendimiento
- Opciones para volver a jugar o regresar al menú principal

## Funcionalidades Adicionales Implementadas

- **Efecto Stroop**: Implementación del efecto psicológico Stroop, donde el nombre del color y el color mostrado son diferentes, añadiendo complejidad al juego
- **Efectos de Sonido**: Retroalimentación auditiva para aciertos, errores y finalización del juego
- **Animaciones**: Efectos visuales para los botones y cambios de colores
- **Almacenamiento de Puntuación**: Guarda la puntuación más alta utilizando SharedPreferences

## Tecnologías y Componentes Utilizados

- **Kotlin**: Lenguaje principal de programación
- **Navigation Component**: Para la navegación entre fragmentos
- **View Binding**: Para acceso seguro a las vistas
- **CountDownTimer**: Para implementar el temporizador del juego
- **SharedPreferences**: Para almacenar la puntuación más alta
- **MediaPlayer**: Para reproducir efectos de sonido
- **ConstraintLayout**: Para layouts flexibles y adaptables

## Requisitos

- Android Studio Hedgehog | 2023.1.1 o superior
- Dispositivo/emulador con Android 7.0 (API 24) o superior
- Kotlin 1.9.0 o superior

## Instalación

1. Clona este repositorio: `git clone https://github.com/JersonCh1/PARCIALMOVILES.git`
2. Abre el proyecto en Android Studio
3. Sincroniza el proyecto con los archivos Gradle
4. Ejecuta la aplicación en un emulador o dispositivo físico

## Estructura del Proyecto
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/colorsgame/
│   │   │   ├── MainActivity.kt
│   │   │   ├── fragments/
│   │   │   │   ├── WelcomeFragment.kt
│   │   │   │   ├── GameFragment.kt
│   │   │   │   └── ResultFragment.kt
│   │   │   └── utils/
│   │   │       └── GameUtils.kt
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml
│   │   │   │   ├── fragment_welcome.xml
│   │   │   │   ├── fragment_game.xml
│   │   │   │   └── fragment_result.xml
│   │   │   ├── navigation/
│   │   │   │   └── nav_graph.xml
│   │   │   ├── values/
│   │   │   │   ├── colors.xml
│   │   │   │   ├── strings.xml
│   │   │   │   └── themes.xml
│   │   │   ├── anim/
│   │   │   │   ├── pulse_animation.xml
│   │   │   │   └── fade_animation.xml
│   │   │   ├── drawable/
│   │   │   │   └── circle_shape.xml
│   │   │   └── raw/
│   │   │       ├── correct.mp3
│   │   │       ├── wrong.mp3
│   │   │       └── game_over.mp3

## Autor

- Jerson Ernesto Chura Pacci
- Universidad La Salle
- Arequipa, Perú

## Licencia

Este proyecto está licenciado bajo la Licencia MIT - consulta el archivo LICENSE para más detalles.

---

*Desarrollado como parte del examen parcial de Desarrollo de Aplicaciones Móviles, Mayo 2025.*
