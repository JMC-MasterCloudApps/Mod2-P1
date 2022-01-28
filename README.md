# Práctica 1. Testing con Spring
## Parte 1: Testing en Java con Spring (3 pts)

Se desea implementar las pruebas necesarias para comprobar la correcta funcionalidad de
una aplicación que gestiona una librería online. Se proporciona el código de dicha aplicación
(publicado en el Aula Virtual: **EnunciadoJava**).

Se quieren realizar las siguientes pruebas de la aplicación:
### Tests unitarios con MockMVC
- Comprobar que se pueden recuperar todos los libros (como usuario sin
logear)
- Añadir un nuevo libro (como usuario logueado)
- Borrar un libro (como administrador)

> En los test unitarios, es obligatorio el uso de Mocks, dado que la persistencia se realiza
utilizando una base de datos H2 (y queremos evitarlo en este tipo de test).

### Material de ayuda
Dado que no se ha explicado en clase cómo manejar los test unitarios cuando utilizamos
autenticación, se proporciona a continuación código de ayuda para este caso:

**Autenticación en test unitarios**

Es necesario añadir la anotación `@WithMockUser` para generar un mock de un usuario con
un rol que necesitemos para la prueba. Los valores (a excepción de roles) no es relevante.

```java
@Test
@WithMockUser(username = "user", password = "pass", roles = "USER")
public void myTest() throws Exception {
```

Es necesario importar la siguiente dependencia para importar la anotación:

```xml
<dependency>
  <groupId>org.springframework.security</groupId>
  <artifactId>spring-security-test</artifactId>
  <scope>test</scope>
</dependency>
```