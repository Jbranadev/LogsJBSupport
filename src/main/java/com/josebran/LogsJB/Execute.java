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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.josebran.LogsJB.MethodsTxt.convertir_fecha;

/****
 * Copyright (C) 2022 El proyecto de código abierto LogsJB de José Bran
 * Clase encargada de recuperar los mensajes de la lista compartida por el Proceso Principal
 * e iniciar un SubProceso encargado de leer los mensajes de la lista y escribirlos en el LogTxt.
 */
class Execute implements Cloneable {

    /***
     * Se utiliza el patron Singleton, para asegurarnos que sea una unica instancia la que se encargue de
     * Llevar el control de los Logs
     */
    private static final InheritableThreadLocal<Execute> instance = new InheritableThreadLocal<>() {
        @Override
        protected Execute initialValue() {
            System.out.println("Creando instancia de Execute para el hilo: " + Thread.currentThread().getName());
            return new Execute();
        }

        @Override
        protected Execute childValue(Execute parentValue) {
            System.out.println("Heredando instancia de Execute para el hilo hijo: " + Thread.currentThread().getName());
            // Retornamos una copia del valor del padre para evitar que se afecte la instancia original
            return parentValue.clone();
        }
    };
    /**
     * Separador que utiliza el sistema de archivos por default
     */
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private static final String separador = System.getProperty("file.separator");
//    private static final ThreadLocal<Execute> instance = ThreadLocal.withInitial(() -> {
//        System.out.println("Creando instancia de Execute para el hilo: " + Thread.currentThread().getName());
//        return new Execute();
//    });
    /**
     * Ejecutor de Tareas asincronas
     */
    private final ExecutorService executorPrincipal = Executors.newCachedThreadPool();
    //private static final Execute instance = new Execute();
    //private Boolean TaskisReady = true;
    // Cambia la declaración de TaskisReady a AtomicBoolean
    private final AtomicBoolean TaskisReady = new AtomicBoolean(true);
    @Getter(AccessLevel.PROTECTED)
    private final MethodsTxt runTXT = new MethodsTxt();
    /**
     * Bandera que indica si la aplicación esta corriendo en un sistema operativo Android
     */
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    protected Boolean isAndroid = false;
    /**
     * Indica si se imprimira en consola
     */
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    protected Boolean viewConsole = true;
    /***
     * Obtiene el usuario actual del sistema operativo
     */
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    protected String usuario = System.getProperty("user.name");
    /****
     * NivelLog desde el grado configurado hacía arriba se estara escribiendo el Log
     * El NivelLog por default es INFO.
     */
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    protected NivelLog gradeLog = NivelLog.INFO;
    /****
     * Tamaño maximo del archivo LogTxt diario que se estara escribiendo, si se supera el tamaño se modificara
     * el nombre del archivo a LOG_dd-MM-YYYY_HH-MM-SSS.txt, e iniciara la escritura del archivo Log.txt
     * con el nuevo registro.
     */
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    protected SizeLog sizeLog = SizeLog.Little_Little;
    /***
     * Ruta donde se estara escribiendo el log por default, la cual sería:
     *  ContexAplicación/Logs/fecha_hoy/Log.txt
     */
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    protected String ruta = (Paths.get("").toAbsolutePath().normalize() + separador + "Logs" + separador + convertir_fecha("dd-MM-YYYY") + separador + "Log.txt");
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected String LogsJBviewConsole = LogsJBProperties.LogsJBviewConsole.getProperty();
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected String LogsJBNivelLog = LogsJBProperties.LogsJBNivelLog.getProperty();
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected String LogsJBRutaLog = LogsJBProperties.LogsJBRutaLog.getProperty();
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected String LogsJBSizeLog = LogsJBProperties.LogsJBSizeLog.getProperty();
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected String LogsJBIsAndroid = LogsJBProperties.LogsJBIsAndroid.getProperty();
    /***
     * Lista que funciona como la cola de peticiones que llegan al Ejecutor
     */
    private ListaMensajes listado = new ListaMensajes();

    private Execute() {
        getLogsJBProperties();
    }

    /***
     * Proporciona la instancia de la clase encargada de ejecutar las acciónes en segundo Plano.
     * @return Retorna la instancia de la Clase Execute, que estara trabajando las peticiones de la aplicación
     * en segundo plano.
     */
    protected static Execute getInstance() {
        return instance.get();
        //return instance;
    }

    // Método para clonar la instancia, asegurando que cada hilo tiene una copia separada
    @Override
    protected Execute clone() {
        try {
            Execute cloned = (Execute) super.clone();
            // Clonamos la lista de mensajes también
            //cloned.listado = new ListaMensajes();;
            cloned.listado = new ListaMensajes();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error al clonar la instancia de Execute", e);
        }
    }

    /***
     * Setea la propiedad de si la libreria imprimira en consola la salida de los logs
     */
    protected void setearViewConsole() {
        String viewConsole = System.getProperty(this.LogsJBviewConsole);
        if (Objects.isNull(viewConsole)) {
            //Si la propiedad del sistema no esta definida, setea el nivel por default
            this.setViewConsole(true);
        } else {
            this.setViewConsole(Boolean.valueOf(viewConsole));
        }
        //System.out.println("SystemProperty Seteada soporte: "+System.getProperty("SizeLog"));
    }

    /***
     * Setea el NivelLog configurado en las propiedades del sistema, de no estar
     * configurada la propiedad correspondiente a NivelLog, setea el nivel por default.
     */
    protected void setearNivelLog() {
        String nivelLog = System.getProperty(this.LogsJBNivelLog);
        if (Objects.isNull(nivelLog)) {
            //Si la propiedad del sistema no esta definida, setea el nivel por default
            this.setGradeLog(NivelLog.INFO);
        } else {
            if (nivelLog.equals("TRACE")) {
                this.setGradeLog(NivelLog.TRACE);
            }
            if (nivelLog.equals("DEBUG")) {
                this.setGradeLog(NivelLog.DEBUG);
            }
            if (nivelLog.equals("INFO")) {
                this.setGradeLog(NivelLog.INFO);
            }
            if (nivelLog.equals("WARNING")) {
                this.setGradeLog(NivelLog.WARNING);
            }
            if (nivelLog.equals("ERROR")) {
                this.setGradeLog(NivelLog.ERROR);
            }
            if (nivelLog.equals("FATAL")) {
                this.setGradeLog(NivelLog.FATAL);
            }
        }
        //System.out.println("SystemProperty Seteada soporte: "+System.getProperty("NivelLog"));
    }

    /***
     * Setea la RutaLog configurado en las propiedades del sistema, de no estar
     * configurada la propiedad correspondiente a RutaLog, setea la ruta por default.
     */
    protected void setearRuta() {
        String rutaLog = System.getProperty(this.LogsJBRutaLog);
        if (Objects.isNull(rutaLog)) {
            //Si la propiedad del sistema no esta definida, setea la ruta por default
            String ruta = (Paths.get("").toAbsolutePath().normalize() + separador + "Logs" + separador +
                    convertir_fecha("dd-MM-YYYY") + separador + "Log.txt");
            this.setRuta(ruta);
        } else {
            this.setRuta(rutaLog);
        }
        //System.out.println("SystemProperty Seteada soporte: "+System.getProperty("RutaLog"));
    }

    /***
     * Setea el SizeLog configurado en las propiedades del sistema, de no estar
     * configurada la propiedad correspondiente a SizeLog, setea el SizeLog por default.
     */
    protected void setearSizelLog() {
        String sizeLog = System.getProperty(this.LogsJBSizeLog);
        if (Objects.isNull(sizeLog)) {
            //Si la propiedad del sistema no esta definida, setea el nivel por default
            this.setSizeLog(SizeLog.Little_Little);
        } else {
            if (sizeLog.equals("Little_Little")) {
                this.setSizeLog(SizeLog.Little_Little);
            }
            if (sizeLog.equals("Little")) {
                this.setSizeLog(SizeLog.Little);
            }
            if (sizeLog.equals("Small_Medium")) {
                this.setSizeLog(SizeLog.Small_Medium);
            }
            if (sizeLog.equals("Medium")) {
                this.setSizeLog(SizeLog.Medium);
            }
            if (sizeLog.equals("Small_Large")) {
                this.setSizeLog(SizeLog.Small_Large);
            }
            if (sizeLog.equals("Large")) {
                this.setSizeLog(SizeLog.Large);
            }
        }
        //System.out.println("SystemProperty Seteada soporte: "+System.getProperty("SizeLog"));
    }

    /***
     * Setea la propiedad de si la libreria esta siendo utilizada en Android o no
     */
    protected void setearIsAndroid() {
        String Android = System.getProperty(this.LogsJBIsAndroid);
        if (Objects.isNull(Android)) {
            //Si la propiedad del sistema no esta definida, setea el nivel por default
            this.setIsAndroid(false);
        } else {
            this.setIsAndroid(Boolean.valueOf(Android));
        }
        //System.out.println("SystemProperty Seteada soporte: "+System.getProperty("SizeLog"));
    }

    /***
     * Proporciona el acceso a la lista que sirve como cola de las peticiones
     * @return Retorna una lista de MensajeWrite, la cual lleva la información que se desea registrar en los Logs
     */
    protected ListaMensajes getListado() {
        return listado;
    }

    /**
     * Recuperara las propiedades de LogsJB seteadas en las propiedades del sistema
     */
    public void getLogsJBProperties() {
        this.setearRuta();
        this.setearNivelLog();
        this.setearSizelLog();
        this.setearIsAndroid();
        this.setearViewConsole();
        this.getRunTXT().setInstance(this);
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
            this.setTaskisReady(false);
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
                    this.runTXT.setBw(new BufferedWriter(new FileWriter(fichero, true)));
                    String temporal = "";
                    boolean band = true;
                    Integer i = 0;
                    while (band) {
                        if (i > 5000) {
                            runTXT.verificarSizeFichero();
                            i = 0;
                        }
                        i++;
                        MensajeWrite mensajetemp = null;
                        mensajetemp = this.getListado().getDato();
                        String Mensaje = mensajetemp.getTexto();
                        NivelLog logtemporal = mensajetemp.getNivelLog();
                        String Clase = mensajetemp.getClase();
                        String Metodo = mensajetemp.getMetodo();
                        String fecha = mensajetemp.getFecha();
                        //Verifica que el nivel de Log a escribir sea igual o mayor al nivel predefinido.
                        this.runTXT.writeLog(logtemporal, Mensaje, Clase, Metodo, fecha);
                        if (getListado().getSize() == 0) {
                            band = false;
                            this.runTXT.getBw().close();
                            this.setTaskisReady(true);
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
