# RecetaYa - Microservicio de Inventario

Microservicio desarrollado con Java 17 y Spring Boot para la Evaluacion Parcial 2.

## Funcionalidades

- Registrar, consultar, actualizar y eliminar medicamentos.
- Administrar sucursales.
- Gestionar el stock de medicamentos por sucursal.
- Consultar el inventario disponible.
- Reservar unidades de medicamentos.
- Registrar descuentos de stock por dispensacion.
- Procesar dispensaciones por lote.
- Reponer existencias.
- Persistir la informacion mediante Spring Data JPA y H2.

## Tecnologias

- Java 17.
- Spring Boot.
- Spring Data JPA e Hibernate.
- Base de datos H2.
- Maven Wrapper.

## Requisitos

- JDK 17 o compatible.
- Git.
- Puerto 8083 disponible.
- Postman para probar los endpoints.

## Clonar el repositorio

git clone https://github.com/CatrinaMedina/recetayainventario.git

cd recetayainventario

## Compilar y generar el JAR

Desde Git Bash:

./mvnw clean package

Si la compilacion termina correctamente, se mostrara BUILD SUCCESS.

El archivo JAR se genera dentro de la carpeta target.

## Ejecutar localmente

./mvnw spring-boot:run

El microservicio se ejecuta en:

http://localhost:8083

## Base de datos

Se utiliza H2 con persistencia en archivo.

Configuracion de conexion:

spring.datasource.url=jdbc:h2:file:./data/inventariodb

Los datos se almacenan localmente en la carpeta data.

## Arquitectura

El proyecto se organiza en las siguientes capas:

- controller: recibe las peticiones HTTP y expone los endpoints REST.
- model: contiene las entidades y modelos del inventario.
- repository: acceso a la base de datos mediante Spring Data JPA.
- service: contiene la logica de negocio y sus implementaciones.

Principales modelos:

- Medicamento.
- Sucursal.
- StockSucursal.
- SolicitudStock.

## Endpoints
### Medicamentos

- POST /medicamentos
- GET /medicamentos
- GET /medicamentos/{id}
- PUT /medicamentos/{id}
- DELETE /medicamentos/{id}

### Sucursales

- POST /sucursales
- GET /sucursales
- GET /sucursales/{id}
- PUT /sucursales/{id}
- DELETE /sucursales/{id}

### Inventario

- POST /inventario/stock
- GET /inventario/stock
- GET /inventario/stock/{id}
- PUT /inventario/stock/{id}
- DELETE /inventario/stock/{id}
- GET /inventario/sucursal/{id}

## Operaciones de stock

- POST /inventario/reservas
- POST /inventario/dispensaciones
- POST /inventario/dispensaciones/lote
- POST /inventario/reposiciones

Estas operaciones permiten reservar, descontar y reponer
existencias de medicamentos en las sucursales.

## Ejemplo de solicitud de stock

{
  "medicamentoId": 1,
  "sucursalId": 1,
  "cantidad": 2
}

Los medicamentos y sucursales correspondientes deben existir.

## Pruebas con Postman

El microservicio se puede probar directamente en:

http://localhost:8083

Se recomienda comprobar:

- Creacion y consulta de medicamentos.
- Creacion y consulta de sucursales.
- Registro y consulta de stock.
- Reserva de medicamentos.
- Descuento y reposicion de existencias.
- Validaciones de datos incorrectos o stock insuficiente.
- Persistencia de datos despues de reiniciar el servicio.

## Integracion con API Gateway

El API Gateway funciona en el puerto 8080.

Mediante el Gateway se requiere autenticacion JWT.
Los roles MEDICO y FARMACEUTICO pueden consultar inventario.
Las modificaciones estan restringidas a FARMACEUTICO.

## Proyecto

RecetaYa - Java: Diseno y Construccion de Soluciones Nativas en Nube.
