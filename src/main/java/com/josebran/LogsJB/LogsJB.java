/***
 * Copyright (C) 2022 El proyecto de código abierto LogsJB de José Bran
 *
 * Con licencia de Apache License, Versión 2.0 (la "Licencia");
 * no puede usar este archivo excepto de conformidad con la Licencia.
 * Puede obtener una copia de la Licencia en
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * A menos que lo exija la ley aplicable o se acuerde por escrito, el software
 * distribuido bajo la Licencia se distribuye "TAL CUAL",
 * SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ya sean expresas o implícitas.
 * Consulte la Licencia para conocer el idioma específico que rige los permisos y
 * limitaciones bajo la Licencia.
 */

package com.josebran.LogsJB;


import com.josebran.LogsJB.Numeracion.LogsJBProperties;
import com.josebran.LogsJB.Numeracion.NivelLog;
import com.josebran.LogsJB.Numeracion.SizeLog;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.Field;

import static com.josebran.LogsJB.Execute.getInstance;
import static com.josebran.LogsJB.Execute.getListado;
import static com.josebran.LogsJB.MethodsTxt.convertir_fecha;

/****
 * Copyright (C) 2022 El proyecto de código abierto LogsJB de José Bran
 * Clase que proporciona los metodos de configuración y entrada para la escritura de los Logs en segundo plano.
 * Los metodos de configuración son:
 *      setRuta(Ruta);
 *      setGradeLog(NivelLog);
 *      setSizeLog(SizeLog);
 *
 * Los metodos de entrada son:
 *      trace(Texto);
 *      debug(Texto);
 *      info(Texto);
 *      warning(Texto);
 *      fatal(Texto);
 *      error(Texto);
 */
public  class LogsJB {


    /***
     * Obtiene la ruta donde se estara escribiendo el Log.
     * @return Retorna un String con la ruta del archivo .Txt donde se estara escribiendo el Log.
     */
    public static String getRuta() {
        return MethodsTxt.ruta;
    }

    /**
     * Setea la ruta en la cual se desea que escriba el Log.
     * @param Ruta Ruta del archivo .Txt donde se desea escribir el Log.
     */
    public static void setRuta(String Ruta) {
        try{
            Field field = MethodsTxt.class.getDeclaredField("ruta");
            field.setAccessible(true);
            field.set(null, Ruta);
            System.setProperty(LogsJBProperties.LogsJBRutaLog.getProperty(), Ruta);
        }catch (Exception e){
            System.err.println("Excepcion capturada al tratar de setear la ruta del log " +Ruta);
        }
    }

    /**
     * Bandera que indica si la libreria esta siendo utilizada en Android
     * @return True si la libreia esta siendo utilizada en Android, de lo contrario retorna False
     */
    public static Boolean getIsAndroid() {
        return MethodsTxt.isAndroid;
    }

    /**
     * Setea la bandera que indica si la libreria esta siendo utilizada en Android
     * @param isAndroid True si la libreria esta siendo utilizada en Android, False si no esta siendo utilizada en Android
     */
    public static void setIsAndroid(Boolean isAndroid) {
        try{
            Field field = MethodsTxt.class.getDeclaredField("isAndroid");
            field.setAccessible(true);
            field.set(null, isAndroid);
            System.setProperty(LogsJBProperties.LogsJBIsAndroid.getProperty(), String.valueOf(isAndroid));
        }catch (Exception e){
            System.err.println("Excepcion capturada al tratar de setear el contador de las veces que se a escrito en " +
                    "el log " +isAndroid);
            System.err.println("Tipo de Excepción : "+e.getClass());
            System.err.println("Causa de la Exepción : "+e.getCause());
            System.err.println("Mensaje de la Exepción : "+e.getMessage());
            System.err.println("Trace de la Exepción : "+ ExceptionUtils.getStackTrace(e));
        }
    }

    /***
     * Obtiene el grado del log, sobre el cual se estara realizando el seguimiento de los mensajes que se
     * escriben en las bitacoras de Log de la aplicación actual.
     * Trace = 200,
     * Debug = 400,
     * Info = 500,
     * Warning = 600,
     * Error = 800,
     * Fatal = 1000.
     * @return Retorna un NivelLog el cual expresa el Nivel Log desde el cual se estara reportando al Log y sus
     * superiores, El valor por defaul es Info.
     */
    public static NivelLog getGradeLog() {
        return MethodsTxt.gradeLog;
    }



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
    public static void setGradeLog(NivelLog GradeLog) {
        try{
            Field field = MethodsTxt.class.getDeclaredField("gradeLog");
            field.setAccessible(true);
            field.set(null, GradeLog);
            System.setProperty(LogsJBProperties.LogsJBNivelLog.getProperty(), GradeLog.name());
            //Methods.metodo = metodo;
        }catch (Exception e){
            System.err.println("Excepcion capturada al tratar de setear el GradeLog de la aplicación " +GradeLog);
        }
    }

    /****
     * Obtiene el tamaño maximo actual definido para el fichero de Log sobre el cual se estara escribiendo.
     * @return Retorna un SizeLog que representa el tamaño configurado para el archivo log de la aplicación,
     * El valor por defaul es Little_Little.
     */
    public static SizeLog getSizeLog() {
        return MethodsTxt.sizeLog;
    }

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
    public static void setSizeLog(SizeLog SizeLog) {
        try{
            Field field = MethodsTxt.class.getDeclaredField("sizeLog");
            field.setAccessible(true);
            field.set(null, SizeLog);
            System.setProperty(LogsJBProperties.LogsJBSizeLog.getProperty(), SizeLog.name());
            //Methods.metodo = metodo;
        }catch (Exception e){
            System.err.println("Excepcion capturada al tratar de setear el Tamaño del archivo Log " +SizeLog);
        }
    }


    /***
     * Obtiene el usuario del sistema sobre el cual corre la aplicación
     * @return Retorna un String con el nombre del usuario actual.
     */
    public static String getUsuario() {
        return MethodsTxt.usuario;
    }

    /***
     * Setea el nombre del usuario del sistema sobre el cual corre la aplicación
     * @param Usuario Usuario actual del sistema que se desea indicar al Log.
     */
    public static void setUsuario(String Usuario){
        try{
            Field field = MethodsTxt.class.getDeclaredField("usuario");
            field.setAccessible(true);
            field.set(null, Usuario);
        }catch (Exception e){
            System.err.println("Excepcion capturada al tratar de setear el usuario del entorno actual "+Usuario);
            System.err.println("Tipo de Excepción : "+e.getClass());
            System.err.println("Causa de la Exepción : "+e.getCause());
            System.err.println("Mensaje de la Exepción : "+e.getMessage());
            System.err.println("Trace de la Exepción : "+ExceptionUtils.getStackTrace(e));
        }

    }




    /***
     * Metodo encargado de hacer la llamada al ejecutor en un hilo de ejecución aparte, para que este se encargue
     * de ejecutar los ejecutores de log's en subprocesos, diferentes al programa principal
     * @param nivelLog NivelLog del mensaje que queremos almacenar en el Log.
     * @param Texto Texto que se desea escribir en el Log.
     */
    private static void executor(NivelLog nivelLog, String Texto){
        try{
            //Permitira obtener la pila de procesos asociados a la ejecuciòn actual
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String clase = null;
            String metodo = null;
            try{
                clase = elements[3].getClassName();
                metodo = elements[3].getMethodName()+" => "+elements[3].getLineNumber();
                if(getIsAndroid()){
                    clase = elements[4].getClassName();
                    metodo = elements[4].getMethodName()+" => "+elements[4].getLineNumber();
                }
            }catch (Exception ex){
                clase = elements[2].getClassName();
                metodo = elements[2].getMethodName()+" => "+elements[2].getLineNumber();
                if(getIsAndroid()){
                    clase = elements[3].getClassName();
                    metodo = elements[3].getMethodName()+" => "+elements[3].getLineNumber();
                }
            }

            if(nivelLog.getGradeLog()>= getGradeLog().getGradeLog()){
                MensajeWrite mensaje=new MensajeWrite();
                mensaje.setTexto(Texto);
                mensaje.setNivelLog(nivelLog);
                mensaje.setClase(clase);
                mensaje.setMetodo(metodo);
                mensaje.setFecha(convertir_fecha());
                //System.out.println("Agregara el dato: "+Thread.currentThread().getName());
                getListado().addDato(mensaje);
                //System.out.println("Correra el metodo run: "+Thread.currentThread().getName());
                if(getInstance().getTaskisReady()){
                    getInstance().run();
                }
                //System.out.println("Nombre hilo Execute: "+Thread.currentThread().getName());
                //Thread.sleep(2);
            }
        }catch (Exception e){
            System.err.println("Excepcion capturada al Executor encargado de hacer la llamada al ejecutor en un hilo de ejecución aparte, para que este se encargue\n" +
                    "     * de ejecutar los ejecutores de log's en subprocesos, diferentes al programa principal");
            System.err.println("Exepcion capturada en el metodo Metodo por medio del cual se llama la escritura de los logs");
            System.err.println("Tipo de Excepción : "+e.getClass());
            System.err.println("Causa de la Exepción : "+e.getCause());
            System.err.println("Mensaje de la Exepción : "+e.getMessage());
            System.err.println("Trace de la Exepción : "+ExceptionUtils.getStackTrace(e));
        }


    }

    /**
     * Obtiene la bandera que indica si no existe alguna tarea pendiente de realizar por parte LogsJB
     * @return True si la LogsJB se encuentra libre, si esta ocupado retorna False
     */
    public static Boolean getTaskIsReady(){
        return getInstance().getTaskisReady();
    }

    /**
     * Espera que se termine de ejecutar los trabajos que esta realizando el Log
     */
    public static void waitForOperationComplete(){
        while(!getInstance().getTaskisReady()){

        }
        System.out.println("Completo de escribir los Logs");
    }


    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria de Informacion.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void info(String Texto){
        executor(NivelLog.INFO, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria de Debug.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void debug(String Texto){
        executor(NivelLog.DEBUG, Texto);
    }
    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria de Trace, la cual es un seguimiento mayor a Debug.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void trace(String Texto){
        executor(NivelLog.TRACE, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria de Advertencia.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void warning(String Texto){
        executor(NivelLog.WARNING, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria Fatal lo cual indica un error del cual no es posible recuperarse.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void fatal(String Texto){
        executor(NivelLog.FATAL, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria de Error, lo cual indica que capturo un error.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void error(String Texto){
        executor(NivelLog.ERROR, Texto);
    }



}
