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
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.josebran.LogsJB.LogsJB.getRuta;
import static com.josebran.LogsJB.MethodsTxt.*;

/****
 * Copyright (C) 2022 El proyecto de código abierto LogsJB de José Bran
 * Clase encargada de recuperar los mensajes de la lista compartida por el Proceso Principal
 * e iniciar un SubProceso encargado de leer los mensajes de la lista y escribirlos en el LogTxt.
 */
class Execute {

    /***
     * Lista que funciona como la cola de peticiones que llegan al Ejecutor
     */
    private static final ListaMensajes listado = new ListaMensajes();
    /***
     * Se utiliza el patron Singleton, para asegurarnos que sea una unica instancia la que se encargue de
     * Llevar el control de los Logs
     */
    private static final Execute instance = new Execute();
    /**
     * Ejecutor de Tareas asincronas
     */
    private final ExecutorService executorPrincipal = Executors.newCachedThreadPool();
    //private Boolean TaskisReady = true;
    // Cambia la declaración de TaskisReady a AtomicBoolean
    private final AtomicBoolean TaskisReady = new AtomicBoolean(true);
    private BufferedWriter bw;
    /***
     * Contador que expresa la cantidad de veces que se a escrito en la ejecución actual de la aplicación
     *
     */
    private long logtext = 0;

    private Execute() {
        getLogsJBProperties();
    }

    /***
     * Proporciona la instancia de la clase encargada de ejecutar las acciónes en segundo Plano.
     * @return Retorna la instancia de la Clase Execute, que estara trabajando las peticiones de la aplicación
     * en segundo plano.
     */
    protected static Execute getInstance() {
        return instance;
    }

    /***
     * Proporciona el acceso a la lista que sirve como cola de las peticiones
     * @return Retorna una lista de MensajeWrite, la cual lleva la información que se desea registrar en los Logs
     */
    protected static ListaMensajes getListado() {
        return listado;
    }

    /**
     * Obtiene el buffer en el que se esta escribiendo actualmente el log
     *
     * @return Buffer en memoria que referencia el archivo en el que se esta escribiendo el log
     */
    protected BufferedWriter getBw() {
        return bw;
    }

    protected void setBw(BufferedWriter buffer) {
        this.bw = buffer;
    }

    /***
     * Obtiene la cantidad de veces que se a escrito en el Txt En la ejecución actual
     * @return Retorna la cantidad de veces que se a escrito en el Log.
     */
    protected long getLogtext() {
        return logtext;
    }
    /***
     * Setea la cantidad de veces que se a escrito en el Log actual.
     * @param Logtext Numero de veces que se a escrito en el Log.
     */

    /**
     * Setea la cantidad de veces que se a escrito en el Log actual.
     *
     * @param Logtext Numero de veces que se a escrito en el Log.
     * @throws NoSuchFieldException   Lanza esta excepción si no encuentra el field que se quiere modificar
     * @throws IllegalAccessException Lanza este error si no se puede setear el valor solicitado al campo
     */
    protected void setLogtext(long Logtext) throws NoSuchFieldException, IllegalAccessException {
        this.logtext = logtext;
    }

    /**
     * Recuperara las propiedades de LogsJB seteadas en las propiedades del sistema
     */
    protected void getLogsJBProperties() {
        setearRuta();
        setearNivelLog();
        setearSizelLog();
        setearIsAndroid();
        setearViewConsole();
    }

    /***
     * Metodo por medio del cual se llama la escritura de los logs
     */
    protected void run() {
        writePrincipal();
    }

    /***
     * Escritor principal, es el que maneja la logica de la aplicación la cual decide si el log se almacena en una BD's,
     * un Txt Ó si se envía a un RestAPI.
     */
    private void writePrincipal() {
        try {
            getInstance().setTaskisReady(false);
            Runnable EscritorPrincipal = () -> {
                try {
                    //Rutas de archivos
                    File fichero = new File(getRuta());
                    //Verifica si existe la carpeta Logs, si no existe, la Crea
                    File directorio = new File(fichero.getParent());
                    if (!directorio.exists()) {
                        if (directorio.mkdirs()) {
                            System.out.println("*");
                            System.out.println("Crea el directorio donde almacenara el Log de la prueba: " + fichero.getParent());
                            System.out.println("*");
                        }
                    }
                    bw = new BufferedWriter(new FileWriter(fichero, true));
                    String temporal = "";
                    boolean band = true;
                    Integer i = 0;
                    while (band) {
                        if (i > 5000) {
                            verificarSizeFichero();
                            i = 0;
                        }
                        i++;
                        MensajeWrite mensajetemp = null;
                        mensajetemp = getListado().getDato();
                        String Mensaje = mensajetemp.getTexto();
                        NivelLog logtemporal = mensajetemp.getNivelLog();
                        String Clase = mensajetemp.getClase();
                        String Metodo = mensajetemp.getMetodo();
                        String fecha = mensajetemp.getFecha();
                        //Verifica que el nivel de Log a escribir sea igual o mayor al nivel predefinido.
                        writeLog(logtemporal, Mensaje, Clase, Metodo, fecha);
                        if (getListado().getSize() == 0) {
                            band = false;
                            bw.close();
                            getInstance().setTaskisReady(true);
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Exepcion capturada al inicializar el buffer, " + "Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
                }
            };
            this.executorPrincipal.submit(EscritorPrincipal);
        } catch (Exception e) {
            System.err.println("Exepcion capturada en el metodo Escritor principal, " + "Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Obtiene la bandera que indica si actualmente esta trabajando la clase Execute o si ya no esta trabajando
     *
     * @return True si esta libre, false si actualmente esta trabajando
     */
    protected Boolean getTaskisReady() {
        return TaskisReady.get();
    }

    /**
     * Setea la bandera que indica si actualmente esta trabajando la clase Execute o si ya no esta trabajando
     *
     * @param taskisReady True si esta libre, false si actualmente esta trabajando
     */
    protected void setTaskisReady(Boolean taskisReady) {
        TaskisReady.set(taskisReady);
    }
}
