# Proyecto de Microservicios

Este proyecto consta de dos microservicios desplegados usando Docker: **Account** y **Client**. A continuación, se detalla cómo ejecutar estos servicios y cómo realizar pruebas utilizando una colección de Postman.

## Ejecución del Aplicativo

Para ejecutar el aplicativo utilizando Docker Compose, sigue estos pasos:

### Requisitos

- **Docker**: Asegúrate de tener Docker instalado en tu máquina. Puedes descargarlo desde [aquí](https://www.docker.com/get-started).
- **Docker Compose**: Necesitarás Docker Compose para definir y ejecutar aplicaciones multi-contenedor. Puedes encontrar la instalación [aquí](https://docs.docker.com/compose/install/).

### Configuración

1. **Clona el repositorio**: Si aún no lo has hecho, clona el repositorio del proyecto:

    ```bash
    git clone <URL_DEL_REPOSITORIO>
    cd <NOMBRE_DEL_REPOSITORIO>
    ```

2. **Construye y levanta los contenedores**: Asegúrate de estar en el directorio que contiene el archivo `docker-compose.yml`, luego ejecuta:

    ```bash
    docker-compose up --build
    ```

   Esto construirá las imágenes Docker para los servicios **Account** y **Client**, y levantará los contenedores definidos en el archivo `docker-compose.yml`.

3. **Verifica el despliegue**: Una vez que los contenedores estén levantados, puedes acceder a los microservicios en las siguientes URLs:
   - **Account Service**: [http://localhost:8081](http://localhost:8081)
   - **Client Service**: [http://localhost:8080](http://localhost:8080)

## Pruebas con Postman

Para realizar pruebas en los microservicios, se proporciona una colección de Postman en formato JSON. Esta colección incluye las solicitudes predefinidas que puedes usar para verificar el funcionamiento de los servicios.

### Uso de la Colección de Postman

1. **Importar la colección**:
   - Abre [Postman](https://www.postman.com/downloads/).
   - Haz clic en el botón `Import` en la esquina superior izquierda.
   - Selecciona la opción `File` y carga el archivo JSON de la colección de Postman que se encuentra en el directorio del proyecto.

2. **Ejecutar las pruebas**:
   - Una vez importada la colección, encontrarás diferentes solicitudes para probar los endpoints de los microservicios **Account** y **Client**.
   - Selecciona una solicitud y haz clic en el botón `Send` para realizar la prueba.
   - Revisa las respuestas para asegurarte de que los servicios estén funcionando correctamente.

### Archivo de la Colección

La colección de Postman se encuentra en el directorio del proyecto bajo el nombre `postman_collection.json`. Asegúrate de tener este archivo en el mismo directorio que el `docker-compose.yml` para facilitar el acceso.

## Contacto

Para cualquier duda o soporte adicional, por favor contacta a [tu_email@dominio.com](mailto:tu_email@dominio.com).
