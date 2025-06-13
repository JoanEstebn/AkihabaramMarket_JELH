// INTRODUCCIÓN //
El contenido de este archivo está presente en la página de github de https://github.com/JoanEstebn/AkihabaramMarket_JELH.git
El siguiente contenido trata de la modernización de la tienda Akihabara Market, una tienda de productos otakus. este proyecto tratará sobre la gestión de inventario y clientes para la tienda, haciendo uso de java y mysql y las distintas opciones para la creación de programas, como es la interfaz de consolas, GUI con Swing y distintas funciones por IA.
-------------------------------------------------------
// TECNOLOGÍAS //
Las tecnologías que vamos a utilizar son:
- *Java 17*
- *MySQL*
- *WindowBuilder* Para la interfaz gráfica
- *OpenRouter API* para el LLM
- *Gson* Para el manejo del json 
- *JUnit 5* Para las pruebas unitarias
- *Maven* Para la gestión de dependencias
-------------------------------------------------------
// ARBOL DE ARCHIVOS //

akihabara_tienda_JELH/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── conexion/
│   │   │   │   └── ConexionBBDD.java
│   │   │   ├── controlador/
│   │   │   │   ├── MainApp.java
│   │   │   │   └── PRUEBAS.java
│   │   │   ├── dao/
│   │   │   │   ├── ClienteDAO.java
│   │   │   │   ├── ConexionBBDD.java
│   │   │   │   └── ProductoDAO.java
│   │   │   ├── gui/
│   │   │   │   ├── AkihabaraGUIjelh.java
│   │   │   │   ├── ClientesPanel.java
│   │   │   │   └── ProductosPanel.java
│   │   │   ├── model/
│   │   │   │   ├── Cliente.java
│   │   │   │   └── ProductoOtaku.java
│   │   │   ├── prueba.datos/
│   │   │   │   └── SetupDatos.java
│   │   │   ├── service/
│   │   │   │   └── LlmService.java
│   │   │   ├── sql/
│   │   │   │   └── practicas_db.sql
│   │   │   ├── util/
│   │   │   │   └── Config.java
│   │   │   └── vista.consola/
│   │   │       ├── InterfazConsola.java
│   │   │       └── InterfazUsuario.java
│   │   └── resources/
│   │       └── config.properties
│   ├── test/
│   │   ├── java/
│   │   │   └── pruebas/
│   │   │       ├── ClienteDAOtest.java
│   │   │       ├── ClienteTest.java
│   │   │       ├── LlmServiceTest.java
│   │   │       ├── ProductoDAOtest.java
│   │   │       └── ProductoOtakuTest.java
│   └── test/resources/
-------------------------------------------------------
// REQUISITOS PARA LA EJECUCIÓN //
Para ejecutarlo vamos a necesitar lo siguiente:
- Java 17 o superior
- MySQL Server en funcionamiento
- IDE (recomendado IntelliJ IDEA o Eclipse)
- Maven instalado

Ejecuta el script `practicas_db.sql` en el SQL

 sql source practicas_db.sql;
-------------------------------------------------------
// INTELIGENCIA ARTIFICIAL //
Este proyecto se conecta con OpenRouter.ai para usar un LLM gratuito como Mistral 7B. Se emplea HttpClient y Gson para comunicarse con la API de forma segura y eficiente.

Para poder usar de forma correcta la IA tendremos que editar los datos del archivo "config.properties":
openrouter.api.key=TU_API_KEY
openrouter.model=mistralai/mistral-7b-instruct:free
Para poder conseguir la api key tendremos que buscar el modelo que aparece y tendremos que generarla en la página de openRouter.
-------------------------------------------------------
// EJECUCIÓN //
Para ejecutar el programa tendremos que realizar los siguientes pasos desde un IDE, en mi caso desde Eclipse IDE:
Carga el proyecto como proyecto Maven.
Ejecuta la clase MainApp.java para usar la versión consola.
Ejecuta AkihabaraGUIjelh.java para usar la interfaz gráfica.
-------------------------------------------------------
// FUNCIONES //

- Funciones Asistidas por IA -
| Mediante OpenRouter, el sistema puede:
|
| Generar descripciones de productos
| 
| Sugerir categorías otaku para nuevos productos
| 
| Estas opciones están integradas en el menú de consola y en la interfaz Swing.

- Interfaz gráfica -
| Se ha implementado una interfaz gráfica usando Java Swing:
| 
| Gestión de productos: añadir, editar, eliminar.
| 
| Gestión de clientes: registro, modificación, visualización.

- Funcionalidades clave -
| Productos
| Añadir, editar, eliminar productos
| 
| Buscar por ID
| 
| Listar todos
| 
| Asistencias con IA
| 
| Clientes
| CRUD completo desde consola y GUI
| 
| Validación de email y teléfono
| 
| Registro automático de fecha
-------------------------------------------------------
// ARCHIVOS JAR //
Además del código, existen dos archivos para poder ejecutar tanto la interfáz gráfica como la aplicación principal:
- MainAppJELH.jar
- AkihabaraGUIjelh.jar

Estos dos archivos los podemos ejecutar desde el cmd de la siguiente forma:
java -version <----------- Para confirmar que tenemos java instalado y podamos ejecutarlo
cd RUTA_DEL_ARCHIVO <----------- Tenemos que establecernos en la ruta donde está el archivo .jar
java -jar MainAppJELH.jar <----------- Para ejecutar el programa principal
java -jar AkihabaraGUIjelh.jar <----------- Para ejecutar el programa de interfaz gráfica
-------------------------------------------------------
// ERRORES //
Si te da error de conexión a la base de datos, asegúrate de que:
MySQL esté corriendo.
La base de datos AkihabaraDB_db_JELH exista.
El archivo config.properties esté en resources o en el mismo directorio del .jar.
Si usas funciones de IA:
Revisa que config.properties tenga la openrouter.api.key.
Que haya conexión a internet.
-------------------------------------------------------
// AUTOR //
Joan Esteban Londoño Hernández
