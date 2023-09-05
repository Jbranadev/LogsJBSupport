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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.josebran.LogsJB.MethodsTxt.*;

/****
 * Copyright (C) 2022 El proyecto de código abierto LogsJB de José Bran
 * Clase encargada de recuperar los mensajes de la lista compartida por el Proceso Principal
 * e iniciar un SubProceso encargado de leer los mensajes de la lista y escribirlos en el LogTxt.
 */
class Execute {

    private Boolean TaskisReady=true;

    /***
     * Lista que funciona como la cola de peticiones que llegan al Ejecutor
     */
    private static ListaMensajes listado=new ListaMensajes();



    /***
     * Se utiliza el patron Singleton, para asegurarnos que sea una unica instancia la que se encargue de
     * Llevar el control de los Logs
     */
    private static Execute instance = new Execute();

    private Execute() {
        //System.out.println("Ejecuta el constructor de Execute Soporte: ");
        setearRuta();
        setearNivelLog();
        setearSizelLog();
        setearIsAndroid();
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


    /***
     * Metodo por medio del cual se llama la escritura de los logs
     */
    protected void run(){
        try{
            writePrincipal();
        }catch (Exception e){
            System.err.println("Exepcion capturada en el metodo Metodo por medio del cual se llama la escritura de los logs");
            System.err.println("Tipo de Excepción : "+e.getClass());
            System.err.println("Causa de la Exepción : "+e.getCause());
            System.err.println("Mensaje de la Exepción : "+e.getMessage());
            System.err.println("Trace de la Exepción : "+ ExceptionUtils.getStackTrace(e));
        }
    }

    /***
     * Escritor principal, es el que maneja la logica de la aplicación la cual decide si el log se almacena en una BD's,
     * un Txt Ó si se envía a un RestAPI.
     */
    private void writePrincipal(){
        try{
            getInstance().setTaskisReady(false);
            Runnable EscritorPrincipal= ()->{
                String temporal="";
                boolean band=true;
                Integer i=0;
                while(band){
                    if(i>5000){
                        verificarSizeFichero();
                        i=0;
                    }
                    i++;
                    //String Mensaje=Execute.getInstance().getTexto();
                    //NivelLog logtemporal=Execute.getInstance().getNivelLog();
                    MensajeWrite mensajetemp=null;
                    mensajetemp=getListado().getDato();
                    //System.out.println("Mensaje en Execute: "+mensajetemp.getTexto()+" "+mensajetemp.getNivelLog());
                    String Mensaje=mensajetemp.getTexto();
                    NivelLog logtemporal=mensajetemp.getNivelLog();
                    String Clase= mensajetemp.getClase();
                    String Metodo= mensajetemp.getMetodo();
                    String fecha= mensajetemp.getFecha();
                    //System.out.println("NivelLog definido: "+nivelaplicación);
                    //System.out.println("NivelLog temporal: "+intniveltemporal);
                    //System.out.println("Cantidad de mensajes Por limpiar: "+getListaTxt().getSize());
                    //Verifica que el nivel de Log a escribir sea igual o mayor al nivel predefinido.
                    writeLog(logtemporal, Mensaje, Clase, Metodo, fecha);
                    if(getListado().getSize()==0){
                        band=false;
                        getInstance().setTaskisReady(true);
                        break;
                    }
                }
                return;
            };
            ExecutorService executorPrincipal = Executors.newFixedThreadPool(1);
            executorPrincipal.submit(EscritorPrincipal);
            executorPrincipal.shutdown();
        }catch (Exception e){
            System.err.println("Exepcion capturada en el metodo Escritor principal, es el que maneja la logica de la aplicación la cual decide si el log se almacena en una BD's,\n" +
                    "     * un Txt Ó si se envía a un RestAPI.");
            System.err.println("Tipo de Excepción : "+e.getClass());
            System.err.println("Causa de la Exepción : "+e.getCause());
            System.err.println("Mensaje de la Exepción : "+e.getMessage());
            System.err.println("Trace de la Exepción : "+ExceptionUtils.getStackTrace(e));
        }
    }




    /**
     * Obtiene la bandera que indica si actualmente esta trabajando la clase Execute o si ya no esta trabajando
     * @return True si esta libre, false si actualmente esta trabajando
     */
    protected synchronized Boolean getTaskisReady() {
        return TaskisReady;
    }

    /**
     * Setea la bandera que indica si actualmente esta trabajando la clase Execute o si ya no esta trabajando
     * @param taskisReady True si esta libre, false si actualmente esta trabajando
     */
    protected synchronized void setTaskisReady(Boolean taskisReady) {
        TaskisReady = taskisReady;
    }

}
