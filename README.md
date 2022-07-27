Developer Challenge - BackEnd Challenge
====================================

![Logo RFRANCO](https://rfrancodigital.com/wp-content/uploads/2018/01/R-Franco-Digital-logo-web.jpg) 

## Explicación
Esta aplicación es un reto propuesto por la empresa RFranco. Consiste en una API, en el que los vehiculos puedan conectarse y añadir pedidos a su reparto, para que los usuarios puedan obtener la posicion actual de su pedido.

Esta API permitirá insertar, actualizar y obtener la ubicación de los vehiculos. Tambien permitira añadir/borrar nuevos pedidos. Cada vez que la ubicación se actualice, se guardará en el historial de ubicacion.

###### Realización nivel 1
Para el funcionamiento de esta aplicación, se ha utilizado las siguientes tecnologías:
- Spring Boot
- PostgreSQL

Una vez arrancado la aplicación, se crearán los vehiculos necesarios para transportar los pedidos, en este caso camiones. Por defecto, no tienen ningún pedido ni ubicación asignados, por lo que por consiguiente se crearán los pedidos para despues añadirlos al camión. Por ultimo, a la hora de que se asigne una ubicación al vehiculo, se guardará en el historial automaticamente.

Se ha conseguido una cobertura del 73,3%

Se proporcionará un archivo para poder probar la API con la herramienta Postman.
