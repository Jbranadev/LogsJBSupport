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

package com.josebran.LogsJB.Numeracion;

/****
 * Copyright (C) 2022 El proyecto de código abierto LogsJB de José Bran
 * Numeración que sirve para indicar el NivelLog del Mensaje.
 */
public enum NivelLog {
    /**
     * TRACE, NOTIFICA O MUESTRA UN DETALLE MAYOR QUE DEBUG.
     * El grado de este nivel de log es 200.
     */
    TRACE(200),

    /**
     * DEBUG , REALIZA LA DEPURACION DE LA APLICACION.
     * El grado de este nivel de log es 400.
     */
    DEBUG(400),
    /**
     * INFO, BRINDA INFORMACION DEL PROGESO Y ESTADO DE LA APLICACION.
     * El grado de este nivel de log es 500.
     */
    INFO(500),
    /**
     * WARNING. BRINDA  UNA ADVERTENCIA DE UN EVENTO INESPERADO DE LA APLICACION
     * El grado de este nivel de log es 600.
     */
    WARNING(600),
    /**
     * ERROR, NOTIFICA UN  ERROR GRAVE QUE DEBE VERIFICARSE.
     * El grado de este nivel de log es 800.
     */
    ERROR(800),
    /**
     * FATAL, NOTIFICA UN FUNCIONAMIENTO ERRONEO EN LA APLICACION.
     * El grado de este nivel de log es 1000.
     */
    FATAL(1000);

    private int gradeLog;

    private NivelLog(int GradeLog){
        this.gradeLog=GradeLog;
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
     * @return retorna un entero que representa el gradeLog correspondiente a la numeración.
     */
    public int getGradeLog(){
        return gradeLog;
    }

}
