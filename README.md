# ms-usuarios

Microservicio REST de la tienda para gestionar **usuarios** y sus **direcciones**. Forma parte de la Evaluación Parcial N°2 de JVY0101 (Java: Diseño y Construcción de Soluciones nativas en Nube). Cada usuario puede tener muchas direcciones (`@OneToMany`) y cada dirección pertenece a un solo usuario (`@ManyToOne`).

## Tecnologías

- Java 17
- Spring Boot 4.1.1 (Spring Web MVC, Spring Data JPA, Validation)
- PostgreSQL 16 (Docker)
- Maven (Maven Wrapper `mvnw`)

## Requisitos

- JDK 17
- Docker Desktop
- Git
- Postman (para probar los endpoints)

## Clonar el repositorio

```bash
git clone https://github.com/kodevlyu/jvy0101-tienda-ms-usuarios.git
cd jvy0101-tienda-ms-usuarios
```

## Configurar la base de datos

Crear y levantar el contenedor de PostgreSQL con la base `usuarios_db`:

```bash
docker run --name pg-ms -e POSTGRES_USER=ms_user -e POSTGRES_PASSWORD=ms_pass123 -e POSTGRES_DB=usuarios_db -p 5432:5432 -d postgres:16
```

Si el contenedor `pg-ms` ya existe, basta con iniciarlo:

```bash
docker start pg-ms
```

Las tablas `usuarios` y `direcciones` las crea Hibernate al iniciar la aplicación.

## Configuración (`src/main/resources/application.properties`)

```properties
spring.application.name=ms-usuarios
server.port=8086
spring.datasource.url=jdbc:postgresql://localhost:5432/usuarios_db
spring.datasource.username=ms_user
spring.datasource.password=ms_pass123
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
```

## Compilar y ejecutar

En Windows (PowerShell):

```powershell
.\mvnw clean
.\mvnw install
.\mvnw package
java -jar target\ms-usuarios-0.0.1-SNAPSHOT.jar
```

En Linux o macOS:

```bash
./mvnw clean install package
java -jar target/ms-usuarios-0.0.1-SNAPSHOT.jar
```

El servicio queda disponible en `http://localhost:8086`. La base de datos debe estar corriendo antes de compilar, porque `install` ejecuta la prueba de arranque de Spring.

## Endpoints

### Usuarios (`/api/usuarios`)

| Método | Ruta | Descripción | Respuesta |
|--------|------|-------------|-----------|
| POST | `/api/usuarios` | Crear usuario | 201 Created, 400 |
| GET | `/api/usuarios` | Listar usuarios | 200 OK |
| GET | `/api/usuarios/{id}` | Buscar usuario por id | 200 OK, 404 |
| PUT | `/api/usuarios/{id}` | Actualizar usuario | 200 OK, 400, 404 |
| DELETE | `/api/usuarios/{id}` | Eliminar usuario (y sus direcciones) | 204 No Content, 404 |

### Direcciones (`/api/direcciones`)

| Método | Ruta | Descripción | Respuesta |
|--------|------|-------------|-----------|
| POST | `/api/direcciones` | Crear dirección para un usuario | 201 Created, 400, 404 |
| GET | `/api/direcciones` | Listar direcciones | 200 OK |
| GET | `/api/direcciones/usuario/{usuarioId}` | Listar direcciones de un usuario | 200 OK, 404 |
| GET | `/api/direcciones/{id}` | Buscar dirección por id | 200 OK, 404 |
| PUT | `/api/direcciones/{id}` | Actualizar dirección | 200 OK, 400, 404 |
| DELETE | `/api/direcciones/{id}` | Eliminar dirección | 204 No Content, 404 |

### Ejemplo: crear usuario

`POST /api/usuarios`

```json
{
  "nombre": "Ana Pérez",
  "email": "ana@correo.cl",
  "telefono": "+56911112222"
}
```

### Ejemplo: crear dirección

`POST /api/direcciones`

```json
{
  "calle": "Av. Libertador Bernardo O'Higgins",
  "numero": "1234",
  "comuna": "Santiago",
  "ciudad": "Santiago",
  "usuario": { "id": 1 }
}
```

### Ejemplo de error de validación (400)

```json
{
  "mensaje": "Error de validación",
  "errores": [
    "nombre: El nombre es obligatorio",
    "email: El email no tiene un formato valido"
  ],
  "status": 400
}
```

## Estructura del proyecto

```
cl.duoc.msusuarios
├── controller   (UsuarioController, DireccionController)
├── service      (UsuarioService, DireccionService)
├── repository   (UsuarioRepository, DireccionRepository)
├── model        (Usuario, Direccion)
└── exception    (RecursoNoEncontradoException, GlobalExceptionHandler)
```

## Autora

kodevlyu