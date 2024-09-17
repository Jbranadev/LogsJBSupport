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
 * Numeración que sirve para indicar el tamaño maximo del LogTxt que se escribira por día.
 */
public enum SizeLog {

    /***
     * El tamaño mas pequeño, que el pequeño,
     * Se definiria el tamaño de log en 25Mb.
     */
    Little_Little(25),
    /***
     * El tamaño pequeño del Log de una aplicación,
     * Se definiria el tamaño de log en 50Mb.
     */
    Little(50),
    /**
     * El tamaño mas pequeño que el mediano y mas grande que el pequeño del Log de una aplicación,
     * Se definiria el tamaño de log en 100Mb.
     */
    Small_Medium(100),
    /**
     * El tamaño mediano del Log de una aplicación,
     * Se definiría el tamaño de log en 150Mb.
     */
    Medium(150),
    /**
     * El tamaño mas pequeño que el grande y mas grande que el mediano del Log de una aplicación,
     * Se definiria el tamaño de log en 250Mb
     */
    Small_Large(250),
    /**
     * El tamaño grande del Log de una aplicación,
     * Se definiría el tamaño de log en 500Mb.
     */
    Large(500);

    private final int size;

    SizeLog(int SizeLog) {
        this.size = SizeLog;
    }

    /***
     * Retorna el tamaño expresado en MB definido para el fichero Log de la aplicación actual
     * @return Retorna un entero con el tamaño maximo definido para el fichero donde se escribe el Log.
     * Little_Little = 25Mb,
     * Little = 50Mb,
     * Small_Medium = 100Mb,
     * Medium = 150Mb,
     * Small_Large = 250Mb,
     * Large = 500Mb.
     */
    public int getSizeLog() {
        return this.size;
    }
}
