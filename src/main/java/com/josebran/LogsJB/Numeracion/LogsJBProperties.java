package com.josebran.LogsJB.Numeracion;

public enum LogsJBProperties {
    /**
     * Bandera que indica si la libreria esta siendo utilizada en Android
     */
    LogsJBIsAndroid("LogsJBSupportisAndroid"),
    /**
     * Identifica la propiedad para validar el tamaño del log size
     */
    LogsJBValidarSize("LogsJBSupportValidarSize"),
    /**
     * Bandera que indica si la libreria imprimira en la terminal
     */
    LogsJBviewConsole("LogsJBSupportviewConsole"),
    /**
     * Bandera que índica a LogsJB si se escribirá el log en el archivo TXT
     */
    LogsJBWriteTxt("LogsJBSupportwriteTxt"),
    /**
     * Tamaño maximo del archivo sobre el cual se estara escribiendo el Log.
     */
    LogsJBSizeLog("LogsJBSupportSizeLog"),
    /**
     * Nivel Log desde el cual hacía arriba en la jerarquia de logs, deseamos se reporten
     */
    LogsJBNivelLog("LogsJBSupportNivelLog"),
    /**
     * Ruta del archivo .Txt donde se desea escribir el Log.
     */
    LogsJBRutaLog("LogsJBSupportRutaLog");
    /**
     * Indica la propieda que se estara setiando
     */
    private String property;

    LogsJBProperties(String property) {
        this.setProperty(property);
    }

    /**
     * Obtiene la propiedad que posee la numeración
     *
     * @return Propiedad que posee la numeración
     */
    public String getProperty() {
        return property;
    }

    /**
     * Setea la propiedad de la numeración
     *
     * @param property Propiedad que se setea a la numeración
     */
    private void setProperty(String property) {
        this.property = property;
    }
}
