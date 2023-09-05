# LogsJBSupport :computer: 
LogsJBSupport es una librería java que permite la gestión de 
registros de un programa en paralelo a la ejecución 
del programa, lo cual la hace una potente herramienta para programas empresariales y personales 
que exigen grandes niveles de rendimiento. 

![Maven Central](https://img.shields.io/maven-central/v/io.github.josecarlosbran/LogsJBSupport?logo=apachemaven&logoColor=%23C71A36&color=blue&link=https%3A%2F%2Fcentral.sonatype.com%2Fsearch%3Fq%3D%26namespace%3Dio.github.josecarlosbran%26name%3DLogsJBSupport)
[![License](https://img.shields.io/badge/Licence-Apache%202.0-blue.svg)](https://github.com/Jbranadev/LogsJB/tree/support_version/LICENSE.txt)
* * *
## Estado del Proyecto :atom:  
LogsJBSupport actualmente está en una etapa de desarrollo continuo, por lo cual sus observaciones y recomendaciones, 
son bienvenidas para mejorar el proyecto.
***

## Configuración :gear: 
Utilizar LogsJBSupport es muy fácil. LogsJBSupport viene con una configuración por default.

### Configuración por Default de LogsJBSupport
- Los registros se almacenan en ContextRoute/Logs/fecha_actual/Log.txt

Donde:

ContextRoute es la ruta del programa actual en ejecución. 

Logs es una carpeta creada en el directorio de la aplicación
En el cual se crea automáticamente una carpeta por cada día en el que se crea un Log.txt.

- El tamaño máximo de un log es de 125Mb 

Al superarse el tamaño máximo por default de Log.txt, se modificará el nombre de este a la siguiente notación
Log_dd-MM-YYYY_HH-MM-SSS.txt y se seguirán escribiendo los registros en Log.txt

![](Imagenes/notacion_logs.png)

- Los registros en Log.txt tienen un grado igual o superior a Info.

Por default se registran únicamente los registros con un grado igual o mayor al grado que posee el NivelLog.INFO.

Así se ven los registros generados en Log.txt

| Fecha y Hora | Usuario | Clase | Método | Nivel Log | Mensaje |
|--------------|---------|-------|--------|-----------|---------|

![](Imagenes/Registros_Log_txt.png)

Las primeras cuatro columnas son generadas automáticamente por LogsJBSupport
estas cuatro columnas son:

| Fecha y Hora | Usuario | Clase | Método |
|--------------|---------|-------|--------|

Donde: 

Fecha y Hora, es el momento exacto en el que se llamó la escritura del registro.

Usuario, es el usuario actual del programa, la librería por default coloca el usuario del sistema operativo, pero este puede
ser modificado de acuerdo a la necesidad del usuario de la librería.

Clase, es la dirección completa de la clase a la cual pertenece el método que llamo a la escritura del registro.

Método, es el método que hizo el llamado a la escritura del registro.

Mientras que las últimas dos columnas indican lo siguiente:

| Nivel Log | Mensaje |
|-----------|---------|

Donde:

Nivel Log, índica el tipo de registro que es. Los tipos de registro disponibles son: **Trace, Debug, Info, Warning, Error y Fatal**.

Mensaje, es el Mensaje que el usuario índico que quería registrar.
***

### ¿Configuración de LogsJBSupport de acuerdo a las necesidades de mi implementación?

LogsJBSupport puede ser configurada de acuerdo a las necesidades de la implementación que usted esté realizando.

- Modificar la ruta de almacenamiento de los registros.

Usted puede modificar la ruta de almacenamiento de los registros de su implementación de la siguiente manera.
~~~
/**
 * Setea la ruta en la cual se desea que escriba el Log.
 * @param Ruta Ruta del archivo .Txt donde se desea escribir el Log.
 */
LogsJB.setRuta(Ruta);
~~~


- Modificar el tamaño máximo que puede tener su archivo de registros.

Usted puede modificar el tamaño que desea que tenga cada archivo de registros de su implementación.
~~~
/***
 * Setea el tamaño maximo para el archivo Log de la aplicación actual.
 * @param SizeLog Tamaño maximo del archivo sobre el cual se estara escribiendo el Log.
 *      * Little_Little = 125Mb,
 *      * Little = 250Mb,
 *      * Small_Medium = 500Mb,
 *      * Medium = 1,000Mb,
 *      * Small_Large = 2,000Mb,
 *      * Large = 4,000Mb.
 * El valor por defaul es Little_Little.
 */
LogsJB.setSizeLog(SizeLog.Little_Little);
~~~


- Modificar el grado de registros que se estarán reportando.
~~~
/***
 * Setea el NivelLog desde el cual deseamos se escriba en el Log de la aplicación actual.
 * @param GradeLog Nivel Log desde el cual hacía arriba en la jerarquia de logs, deseamos se reporten
 *      * Trace = 200,
 *      * Debug = 400,
 *      * Info = 500,
 *      * Warning = 600,
 *      * Error = 800,
 *      * Fatal = 1000.
 * El valor por defaul es Info. Lo cual hace que se reporten los Logs de grado Info, Warning, Error y Fatal.
 */
LogsJB.setGradeLog(NivelLog.INFO);
~~~

- Modificar el usuario que se graba en el registro.
~~~
/***
 * Setea el nombre del usuario del sistema sobre el cual corre la aplicación
 * @param Usuario Usuario actual del sistema que se desea indicar al Log.
 */
LogsJB.setUsuario(Usuario);
~~~
* * *

## ¿Cómo usar LogsJBSupport?
Usar LogsJBSupport es más fácil que hacer un llamado a System.out.println(mensaje), ya que al llamar a los métodos de registro
de LogsJBSupport se escribe el mensaje en la salida de la terminal del programa y en el archivo Log.txt, con menos esfuerzo del necesario
para hacer un System.out.println(mensaje).

~~~
/**
* Una vez se a importado los métodos estáticos de LogsJB
* Se puede hacer el llamado invocando al método estático de las siguientes dos maneras:
* LogsJB.debug(Mensaje);
* debug(Mensaje);
* @param Mensaje es un String que indica el mensaje que queremos registrar en la salida de la terminal,
* como en el archivo Logs.txt
*/
 
//Comentario grado Trace
trace( "Primer comentario grado Trace");
//Comentario grado Debug
debug( "Primer comentario grado Debug");
//Comentario grado Info
info( "Primer comentario grado Info");
//Comentario grado Warning
warning( "Primer comentario grado Warning");
//Comentario grado Error
error( "Primer comentario grado Error");
//Comentario grado Fatal
fatal( "Primer comentario grado Fatal"); 
~~~

Salida en la terminal
![](Imagenes/Terminal_output.png)

Salida en Log.txt
![](Imagenes/Txt_output.png)


* * *
## ¿Cómo Obtener LogsJBSupport para usarlo en mi proyecto?
Puedes obtener la librería LogsJBSupport de la siguiente manera

Maven 
~~~
<dependency>
    <groupId>io.github.josecarlosbran</groupId>
    <artifactId>LogsJBSupport</artifactId>
    <version>0.5.6</version>
</dependency>
~~~


Gradle
~~~
implementation 'io.github.josecarlosbran:LogsJBSupport:0.5.6'
~~~

Para mayor información sobre como descargar LogsJBSupport desde otros 
administradores de paquetes, puedes ir al siguiente Link

<https://search.maven.org/artifact/io.github.josecarlosbran/LogsJBSupport>

***

## Licencia :balance_scale: 
LogsJBSupport es una librería open source desarrollada por José Bran, para la administración
de los registros de un programa, con licencia de Apache License, Versión 2.0;

No puede usar esta librería excepto de conformidad con la Licencia.
Puede obtener una copia de la Licencia en http://www.apache.org/licenses/LICENSE-2.0 

A menos que lo exija la ley aplicable o se acuerde por escrito, el software
distribuido bajo la Licencia se distribuye "TAL CUAL",
SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ya sean expresas o implícitas.
Consulte la Licencia para conocer el idioma específico que rige los permisos y
limitaciones bajo la Licencia.

***
