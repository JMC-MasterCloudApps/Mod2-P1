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

## Parte 2: Contract Testing (5 pts)

En la aplicación de la práctica 3 de la asignatura 2.1 Tecnologías de Servicios de Internet,
se desarrolló una aplicación con cuatro servicios. Dicha aplicación ha sido refactorizada y se
han dejado exclusivamente los servicios **planner** y **toposervice** (se proporciona un zip con
ambos servicios: **cdct_enunciado**).

El servicio planner utiliza el servicio **toposervice** para conocer la orografía de una
determinada ciudad. Ambos servicios van a ser desarrollados por equipos diferentes y
deben poder ser desplegados en cualquier momento sin depender el uno del otro y
asegurando la compatibilidad entre ellos. Para ello, ambos equipos han acordado utilizar
**Consumer Driven Contract Testing** usando **PACT**.

### Se pide
1. Configurar el servicio **planner** e implementar un test que verifique el funcionamiento
esperado del endpoint **getCity** del servicio **toposervice**.
2. Configurar el servicio **toposervice** de forma que se pueda validar el contrato contra la
implementación de la API proporcionada por **toposervice**.
3. Se valorará mockear el **TopoService** para evitar tener que cargar los datos al
arrancar la aplicación.

### Entregables
- El código de los dos servicios se entregará dentro de una carpeta **CDCT** en el zip
que se entregará con la práctica (ver instrucciones de entrega más abajo)