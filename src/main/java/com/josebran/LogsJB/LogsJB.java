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

import com.josebran.LogsJB.Numeracion.NivelLog;
import com.josebran.LogsJB.Numeracion.SizeLog;
import org.apache.commons.lang3.exception.ExceptionUtils;

import static com.josebran.LogsJB.Execute.getInstance;
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
public class LogsJB {
    /***
     * Obtiene la ruta donde se estara escribiendo el Log.
     * @return Retorna un String con la ruta del archivo .Txt donde se estara escribiendo el Log.
     */
    public static String getRuta() {
        return getInstance().ruta;
    }

    /**
     * Setea la ruta en la cual se desea que escriba el Log.
     *
     * @param Ruta Ruta del archivo .Txt donde se desea escribir el Log.
     */
    public static void setRuta(String Ruta) {
        try {
            getInstance().setRuta(Ruta);
            System.setProperty(getInstance().LogsJBRutaLog, Ruta);
        } catch (Exception e) {
            System.err.println("Excepcion capturada al tratar de setear la ruta del log " + Ruta);
        }
    }

    /**
     * Bandera que indica si la libreria esta siendo utilizada en Android
     *
     * @return True si la libreia esta siendo utilizada en Android, de lo contrario retorna False
     */
    public static Boolean getIsAndroid() {
        return getInstance().isAndroid;
    }

    /**
     * Setea la bandera que indica si la libreria esta siendo utilizada en Android
     *
     * @param isAndroid True si la libreria esta siendo utilizada en Android, False si no esta siendo utilizada en Android
     */
    public static void setIsAndroid(Boolean isAndroid) {
        try {
            getInstance().setIsAndroid(isAndroid);
            System.setProperty(getInstance().LogsJBIsAndroid, String.valueOf(isAndroid));
        } catch (Exception e) {
            System.err.println("Excepcion capturada al tratar de setear el contador de las veces que se a escrito en " +
                    "el log " + isAndroid + " Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Setea la bandera que indica si la librería imprimira los logs en consola
     *
     * @return True si imprimira en consola la salida del log, false en caso contrario
     */
    public static Boolean getviewConsole() {
        return getInstance().viewConsole;
    }

    /**
     * Setea la bandera que indica si la libreria imprimira los logs en consola
     *
     * @param viewConsole True si la libreria imprimira en la salida de la terminal el log, False si no imprimira en la terminal el log
     */
    public static void setviewConsole(Boolean viewConsole) {
        try {
            getInstance().setViewConsole(viewConsole);
            System.setProperty(getInstance().LogsJBviewConsole, String.valueOf(viewConsole));
        } catch (Exception e) {
            System.err.println("Excepcion capturada al tratar de setear el contador de las veces que se a escrito en " +
                    "el log " + viewConsole + " Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
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
        return getInstance().gradeLog;
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
        try {
            getInstance().setGradeLog(GradeLog);
            System.setProperty(getInstance().LogsJBNivelLog, GradeLog.name());
        } catch (Exception e) {
            System.err.println("Excepcion capturada al tratar de setear el GradeLog de la aplicación " + GradeLog + " Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    /****
     * Obtiene el tamaño maximo actual definido para el fichero de Log sobre el cual se estara escribiendo.
     * @return Retorna un SizeLog que representa el tamaño configurado para el archivo log de la aplicación,
     * El valor por defaul es Little_Little.
     */
    public static SizeLog getSizeLog() {
        return getInstance().sizeLog;
    }

    /***
     * Setea el tamaño maximo para el archivo Log de la aplicación actual.
     * @param SizeLog Tamaño maximo del archivo sobre el cual se estara escribiendo el Log.
     *      * Little_Little = 25Mb,
     *      * Little = 50Mb,
     *      * Small_Medium = 100Mb,
     *      * Medium = 150Mb,
     *      * Small_Large = 250Mb,
     *      * Large = 500Mb.
     * El valor por defaul es Little_Little.
     */
    public static void setSizeLog(SizeLog SizeLog) {
        try {
            getInstance().setSizeLog(SizeLog);
            System.setProperty(getInstance().LogsJBSizeLog, SizeLog.name());
            //Methods.metodo = metodo;
        } catch (Exception e) {
            System.err.println("Excepcion capturada al tratar de setear el Tamaño del archivo Log " + SizeLog + " Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Setea la Cantidad de registros que deben escribirse para validar el tamaño del archivo log
     *
     * @param validarSize Cantidad de registros que deben escribirse para validar el tamaño del archivo log
     */
    public static void setSizeLog(Integer validarSize) {
        try {
            getInstance().setValidarSize(validarSize);
            System.setProperty(getInstance().LogsJBValidarSize, String.valueOf(validarSize));
        } catch (Exception e) {
            System.err.println("Excepcion capturada al tratar de setear la validación de tamaño del archivo " + validarSize + " Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Retorna la propiedad que indica cada cuanto ciertos logs validar el tamaño del archivo log
     *
     * @return Cantidad de registros que deben escribirse para validar el tamaño del archivo log
     */
    public static Integer getValidarSize() {
        return getInstance().validarSize;
    }

    /***
     * Obtiene el usuario del sistema sobre el cual corre la aplicación
     * @return Retorna un String con el nombre del usuario actual.
     */
    public static String getUsuario() {
        return getInstance().usuario;
    }

    /***
     * Setea el nombre del usuario del sistema sobre el cual corre la aplicación
     * @param Usuario Usuario actual del sistema que se desea indicar al Log.
     */
    public static void setUsuario(String Usuario) {
        try {
            getInstance().setUsuario(Usuario);
        } catch (Exception e) {
            System.err.println("Excepcion capturada al tratar de setear el usuario del entorno actual " + Usuario + " Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Obtiene la instancia del escritor de LogsJB para obtener las propiedades de configuración y escritura de logs
     */
    public static Execute getInstanceLogsJB() {
        return getInstance();
    }

    /***
     * Metodo encargado de hacer la llamada al ejecutor en un hilo de ejecución aparte, para que este se encargue
     * de ejecutar los ejecutores de log's en subprocesos, diferentes al programa principal
     * @param nivelLog NivelLog del mensaje que queremos almacenar en el Log.
     * @param Texto Texto que se desea escribir en el Log.
     */
    private static void executor(NivelLog nivelLog, String Texto) {
        try {
            //Permitira obtener la pila de procesos asociados a la ejecuciòn actual
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String clase = null;
            String metodo = null;
            try {
                clase = elements[3].getClassName();
                metodo = elements[3].getMethodName() + " => " + elements[3].getLineNumber();
                if (getIsAndroid()) {
                    clase = elements[4].getClassName();
                    metodo = elements[4].getMethodName() + " => " + elements[4].getLineNumber();
                }
            } catch (Exception ex) {

            }
            if (nivelLog.getGradeLog() >= getGradeLog().getGradeLog()) {
                MensajeWrite mensaje = new MensajeWrite();
                mensaje.setTexto(Texto);
                mensaje.setNivelLog(nivelLog);
                mensaje.setClase(clase);
                mensaje.setMetodo(metodo);
                mensaje.setFecha(convertir_fecha());
                getInstance().getListado().addDato(mensaje);
                if (getInstance().getTaskisReady()) {
                    getInstance().run();
                }
            }
        } catch (Exception e) {
            System.err.println("Exepcion capturada en el metodo Metodo por medio del cual se llama la escritura de los logs" + " Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Obtiene la bandera que indica si no existe alguna tarea pendiente de realizar por parte LogsJB
     *
     * @return True si la LogsJB se encuentra libre, si esta ocupado retorna False
     */
    public static Boolean getTaskIsReady() {
        return getInstance().getTaskisReady();
    }

    /**
     * Espera que se termine de ejecutar los trabajos que esta realizando el Log
     */
    public static void waitForOperationComplete() {
        while (!getTaskIsReady()) {
        }
        System.out.println("Completo de escribir los Logs");
    }

    /**
     * Recuperara las propiedades de LogsJB seteadas en las propiedades del sistema
     */
    public static void getLogsJBProperties() {
        getInstance().getLogsJBProperties();
    }

    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria de Informacion.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void info(String Texto) {
        executor(NivelLog.INFO, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria de Debug.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void debug(String Texto) {
        executor(NivelLog.DEBUG, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria de Trace, la cual es un seguimiento mayor a Debug.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void trace(String Texto) {
        executor(NivelLog.TRACE, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria de Advertencia.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void warning(String Texto) {
        executor(NivelLog.WARNING, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria Fatal lo cual indica un error del cual no es posible recuperarse.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void fatal(String Texto) {
        executor(NivelLog.FATAL, Texto);
    }

    /***
     * Escribe en el Log el mensaje especificado indicando que pertenece a la categoria de Error, lo cual indica que capturo un error.
     * @param Texto Texto que se desea escribir en el Log.
     */
    public static void error(String Texto) {
        executor(NivelLog.ERROR, Texto);
    }
}
