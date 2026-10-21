# rest-client-quickstart

Servicio Quarkus que consume la API de extensiones de Quarkus mediante MicroProfile REST Client y expone varias formas de devolver la respuesta.

## Guías adaptadas

- [Using the REST Client](https://quarkus.io/guides/rest-client): interfaz declarativa con `@RegisterRestClient`, configuración mediante `configKey`, inyección del cliente, cabeceras, filtros, timeouts y manejo de errores.
- [Writing JSON REST Services](https://quarkus.io/guides/rest-json): `quarkus-rest-jackson` y `quarkus-rest-client-jackson` conectan los recursos HTTP y la serialización JSON del servicio remoto.
- [Conocimientos JSON](https://www.nylas.com/blog/the-complete-guide-to-working-with-json/): ayuda a razonar sobre la conversión de la respuesta JSON remota en objetos `Extension`.

El cliente apunta a `https://stage.code.quarkus.io/api`. Las llamadas reales dependen de que ese servicio esté disponible y de tener acceso a la red.

## Cómo se aplica

- `ExtensionsService` declara la API externa y ofrece retornos síncronos, `CompletionStage`, `Uni` y `Response`.
- `ExtensionsResource` expone esas variantes localmente bajo `/extension`.
- `InkluSportHeadersFactory` y `InkluSportRequestFilter` muestran cómo añadir información a las solicitudes.
- `@ClientExceptionMapper` traduce algunas respuestas de error; `@ClientObjectMapper` personaliza la deserialización.
- `application.properties` define la URL, los timeouts, el registro de peticiones y las cabeceras enmascaradas.
- Las pruebas incluyen WireMock para simular respuestas sin depender del servicio remoto.

## Ejecutar y probar

Requiere Java 17 según `maven.compiler.source` y `maven.compiler.target` en `pom.xml`.

```powershell
.\mvnw.cmd quarkus:dev
```

Ejemplos locales:

```powershell
curl.exe http://localhost:8080/extension/id/hibernate-orm
curl.exe http://localhost:8080/extension/id-async/hibernate-orm
curl.exe http://localhost:8080/extension/id-uni/hibernate-orm
curl.exe -i http://localhost:8080/extension/full-response/hibernate-orm
```

La aplicación consulta la URL remota configurada. Para pruebas repetibles y sin red, ejecuta:

```powershell
.\mvnw.cmd test
```

## Ejercicio para explicar el cliente

1. Cambia temporalmente la ruta local para recibir un id y observa cómo se convierte en `?id=...` mediante `@RestQuery`.
2. Compara los métodos síncrono, `CompletionStage` y `Uni`.
3. Simula un `404` con WireMock y sigue el flujo hasta `@ClientExceptionMapper`.
4. Inspecciona cómo se enmascaran `Authorization`, `Cookie` y `X-API-Key` en los logs.

**Advertencia de seguridad:** `quarkus.tls.trust-all=true` desactiva la validación normal de certificados TLS. Es un ajuste inseguro para producción; mantenlo solo en un entorno de prueba controlado y elimínalo antes de desplegar.
