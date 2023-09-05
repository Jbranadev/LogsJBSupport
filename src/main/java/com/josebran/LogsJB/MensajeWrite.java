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

/***
 * Copyright (C) 2022 El proyecto de código abierto LogsJB de José Bran
 * Clase que representa lo que es un mensaje en la escritura de los Logs, por el momento un objeto de esta clase
 * contiene:
 *  Un String Texto que es el mensaje que se desea escribir.
 *  Un NivelLog nivelLog que representa el grado de log del mensaje.
 *  Un String Clase que representa la clase en la cual se mando a llamar la escritura del Log.
 *  Un String Metodo que representa el metodo desde el cual se llama la escritura del Log.
 */
class MensajeWrite {
    private String Texto;
    private NivelLog nivelLog;

    private String Clase;

    private String Metodo;

    private String fecha;

    protected MensajeWrite(){

    }

    /***
     * Obtiene el texto del cuerpo del Mensaje
     * @return Retorna un String que representa el mensaje que deseamos sea escrito.
     */
    protected String getTexto() {
        return Texto;
    }

    /***
     * Setea el texto del Mensaje.
     * @param texto String que representa el mensaje que deseamos sea escrito.
     */
    protected void setTexto(String texto) {
        Texto = texto;
    }


    /***
     * Obtiene el NivelLog del Mensaje.
     * @return Retorna un NivelLog que representa el grado de log del mensaje.
     */
    protected NivelLog getNivelLog() {
        return nivelLog;
    }

    /****
     * Setea el NivelLog del Mensaje.
     * @param nivelLog NivelLog que representa el grado de log del mensaje.
     */
    protected void setNivelLog(NivelLog nivelLog) {
        this.nivelLog = nivelLog;
    }
    /***
     * Obtiene el nombre de la clase que actualmente esta llamando al Log
     * @return Retorna el nombre de la clase que esta invocando la escritura del Log
     */
    protected String getClase() {
        return Clase;
    }
    /***
     * Setea el nombre de la clase que esta haciendo el llamado al metodo que escribe el Log.
     * @param clase Nombre de la clase que llama al metodo que escribe el Log.
     */
    protected void setClase(String clase) {
        Clase = clase;
    }
    /**
     * Obtiene el nombre del metodo que actualmente esta llamando al Log
     * @return Retorna el nombre del metodo que esta invocando la escritura del Log
     */
    protected String getMetodo() {
        return Metodo;
    }
    /**
     * Setea el nombre del metodo que esta haciendo el llamado al metodo que escribe el Log.
     * @param metodo Nombre del metodo que llama al metodo que escribe el Log.
     */
    protected void setMetodo(String metodo) {
        Metodo = metodo;
    }

    /***
     * Obtiene la fecha de escritura del Log.
     * @return Retorna un string con la fecha de escritura del Log.
     */
    protected String getFecha() {
        return fecha;
    }

    /**
     * Setea la fecha de escritura del Log.
     * @param fecha String que representa el momento en el que fue mandado a llamar el Log.
     */
    protected void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
