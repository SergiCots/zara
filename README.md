# Proyecto de Gestión de Precios

Este proyecto es una aplicación de ejemplo para gestionar precios de productos en una tienda en línea. Incluye un controlador RESTful que permite buscar precios de productos en un rango de fechas y una base de datos que almacena la información de precios.

## Tecnologías Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven

## Patrones Utilizados

- Patrón de Repositorio
- Patrón de Controlador REST

## Configuración del Proyecto

1. **Clonar el Repositorio**: Para comenzar, clona este repositorio en tu máquina local utilizando el siguiente comando:

   ```bash
   git clone https://github.com/tuusuario/tuproyecto.git
   
Requisitos Previos: Asegúrate de tener Java 8 (o una versión superior) instalado en tu sistema.

Importar en el IDE: Importa el proyecto en tu IDE favorito como un proyecto Maven.

Ejecución del Proyecto
Ejecutar la Aplicación: Abre una terminal en la ubicación del proyecto y ejecuta el siguiente comando para compilar y ejecutar la aplicación:

mvn spring-boot:run
La aplicación estará disponible en http://localhost:8080.

Uso de la API REST
Buscar Precios de Productos
Puedes buscar precios de productos utilizando la API REST de la siguiente manera:

URL: /v1/prices/{brandId}/{productId}/{startDate}/{endDate}
Método: GET
Parámetros:
brandId (Long): ID de la marca.
productId (Integer): ID del producto.
startDate (String, formato "yyyy-MM-dd HH:mm:ss"): Fecha de inicio del rango de búsqueda.
endDate (String, formato "yyyy-MM-dd HH:mm:ss"): Fecha de fin del rango de búsqueda.
Ejemplo de solicitud cURL:

bash
Copy code
curl -X GET "http://localhost:8080/v1/prices/1/35455/2020-06-16%2021:00:00/2020-06-16%2021:00:00" -H "accept: */*"

Ejecución de Pruebas
Para ejecutar las pruebas unitarias del proyecto, utiliza el siguiente comando:
mvn test
