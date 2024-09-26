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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static com.josebran.LogsJB.LogsJB.*;

/****
 * Copyright (C) 2022 El proyecto de código abierto LogsJB de José Bran
 * Clase que almacena los metodos necesarios para poder escribir el LogTxt
 */
class MethodsTxt {

    // Definimos el formateador como una constante estática para evitar recrearlo en cada llamada
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss SSS");
    // Definir una constante para el patrón de fecha.
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss SSS");
    /**
     * Separador que utiliza el sistema de archivos por default
     */
    private static final String separador = System.getProperty("file.separator");
    /**
     * Bandera que indica si la aplicación esta corriendo en un sistema operativo Android
     */
    protected static Boolean isAndroid = false;

    /**
     * Indica si se imprimira en consola
     */
    protected static Boolean viewConsole = true;
    /***
     * Obtiene el usuario actual del sistema operativo
     */
    protected static String usuario = System.getProperty("user.name");
    /****
     * NivelLog desde el grado configurado hacía arriba se estara escribiendo el Log
     * El NivelLog por default es INFO.
     */
    protected static NivelLog gradeLog = NivelLog.INFO;
    /****
     * Tamaño maximo del archivo LogTxt diario que se estara escribiendo, si se supera el tamaño se modificara
     * el nombre del archivo a LOG_dd-MM-YYYY_HH-MM-SSS.txt, e iniciara la escritura del archivo Log.txt
     * con el nuevo registro.
     */
    protected static SizeLog sizeLog = SizeLog.Little_Little;
    /***
     * Ruta donde se estara escribiendo el log por default, la cual sería:
     *  ContexAplicación/Logs/fecha_hoy/Log.txt
     */
    protected static String ruta = (Paths.get("").toAbsolutePath().normalize() + separador + "Logs" + separador + convertir_fecha("dd-MM-YYYY") + separador + "Log.txt");

    private BufferedWriter bw;

    /***
     * Contador que expresa la cantidad de veces que se a escrito en la ejecución actual de la aplicación
     *
     */
    private long logtext = 0;

    /***
     * Setea el NivelLog configurado en las propiedades del sistema, de no estar
     * configurada la propiedad correspondiente a NivelLog, setea el nivel por default.
     */
    protected static void setearNivelLog() {
        String nivelLog = System.getProperty(LogsJBProperties.LogsJBNivelLog.getProperty());
        if (Objects.isNull(nivelLog)) {
            //Si la propiedad del sistema no esta definida, setea el nivel por default
            setGradeLog(NivelLog.INFO);
        } else {
            if (nivelLog.equals("TRACE")) {
                setGradeLog(NivelLog.TRACE);
            }
            if (nivelLog.equals("DEBUG")) {
                setGradeLog(NivelLog.DEBUG);
            }
            if (nivelLog.equals("INFO")) {
                setGradeLog(NivelLog.INFO);
            }
            if (nivelLog.equals("WARNING")) {
                setGradeLog(NivelLog.WARNING);
            }
            if (nivelLog.equals("ERROR")) {
                setGradeLog(NivelLog.ERROR);
            }
            if (nivelLog.equals("FATAL")) {
                setGradeLog(NivelLog.FATAL);
            }
        }
        //System.out.println("SystemProperty Seteada soporte: "+System.getProperty("NivelLog"));
    }

    /***
     * Setea la RutaLog configurado en las propiedades del sistema, de no estar
     * configurada la propiedad correspondiente a RutaLog, setea la ruta por default.
     */
    protected static void setearRuta() {
        String rutaLog = System.getProperty(LogsJBProperties.LogsJBRutaLog.getProperty());
        if (Objects.isNull(rutaLog)) {
            //Si la propiedad del sistema no esta definida, setea la ruta por default
            String ruta = (Paths.get("").toAbsolutePath().normalize() + separador + "Logs" + separador +
                    convertir_fecha("dd-MM-YYYY") + separador + "Log.txt");
            setRuta(ruta);
        } else {
            setRuta(rutaLog);
        }
        //System.out.println("SystemProperty Seteada soporte: "+System.getProperty("RutaLog"));
    }

    /***
     * Setea el SizeLog configurado en las propiedades del sistema, de no estar
     * configurada la propiedad correspondiente a SizeLog, setea el SizeLog por default.
     */
    protected static void setearSizelLog() {
        String sizeLog = System.getProperty(LogsJBProperties.LogsJBSizeLog.getProperty());
        if (Objects.isNull(sizeLog)) {
            //Si la propiedad del sistema no esta definida, setea el nivel por default
            setSizeLog(SizeLog.Little_Little);
        } else {
            if (sizeLog.equals("Little_Little")) {
                setSizeLog(SizeLog.Little_Little);
            }
            if (sizeLog.equals("Little")) {
                setSizeLog(SizeLog.Little);
            }
            if (sizeLog.equals("Small_Medium")) {
                setSizeLog(SizeLog.Small_Medium);
            }
            if (sizeLog.equals("Medium")) {
                setSizeLog(SizeLog.Medium);
            }
            if (sizeLog.equals("Small_Large")) {
                setSizeLog(SizeLog.Small_Large);
            }
            if (sizeLog.equals("Large")) {
                setSizeLog(SizeLog.Large);
            }
        }
        //System.out.println("SystemProperty Seteada soporte: "+System.getProperty("SizeLog"));
    }

    /***
     * Setea la propiedad de si la libreria esta siendo utilizada en Android o no
     */
    protected static void setearIsAndroid() {
        String Android = System.getProperty(LogsJBProperties.LogsJBIsAndroid.getProperty());
        if (Objects.isNull(Android)) {
            //Si la propiedad del sistema no esta definida, setea el nivel por default
            setIsAndroid(false);
        } else {
            setIsAndroid(Boolean.valueOf(Android));
        }
        //System.out.println("SystemProperty Seteada soporte: "+System.getProperty("SizeLog"));
    }

    /***
     * Setea la propiedad de si la libreria imprimira en consola la salida de los logs
     */
    protected static void setearViewConsole() {
        String viewConsole = System.getProperty(LogsJBProperties.LogsJBviewConsole.getProperty());
        if (Objects.isNull(viewConsole)) {
            //Si la propiedad del sistema no esta definida, setea el nivel por default
            setviewConsole(true);
        } else {
            setviewConsole(Boolean.valueOf(viewConsole));
        }
        //System.out.println("SystemProperty Seteada soporte: "+System.getProperty("SizeLog"));
    }

    /***
     * Obtiene la fecha actual en formato dd/MM/YYYY HH:MM:SS
     * @return Retorna una cadena de texto con la fecha obtenida
     */
    protected static String convertir_fecha() {
        String temp = FORMATTER.format(LocalDateTime.now());
        return temp;
    }

    /***
     * Obtiene la fecha en el formato indicado
     * @param formato Formato que se desea obtener la fecha
     * @return Retorna una cadena de texto con la fecha obtenida en el formato especificado.
     */
    protected static String convertir_fecha(String formato) {
        DateTimeFormatter formater = DateTimeFormatter.ofPattern(formato);
        String temp = formater.format(LocalDateTime.now());
        return temp;
    }

    /**
     * metodo que retorna la cantidad de tabulaciones para el siguiente texto en la misma linea conforme
     * al la longitud de la cadena actual:
     * si la cadena es  igual o menor a 7 da 4 tabulaciones
     * si la cadena es igual o menor a 16 da 3 tabulaciones
     * si la cadena es  mayor a 16 da 2 tabulaciones.
     *
     * @param cadena Texto a evaluar para obtener la separcion de tabulaciones de acuerdo al algoritmo definido.
     * @return Retorna un string con la cantidad de tabulaciones respecto al siguiente texto en la misma linea.
     */
    protected static String getTabs(String cadena) {
        //Reglas del negocio, maximas tabulaciones son 4
        //Minima tabulacion es una
        StringBuilder result = new StringBuilder();
        String tab = "\u0009";
        int tamaño = cadena.length();
        int sobrantes = tamaño % 4;
        if (sobrantes != 0) {
            int restantes = 4 - sobrantes;
            result.append(" ".repeat(restantes));
        }
        //Si la cadena es menor a 13, retornara 7 tabs
        int numTabs = 0;
        if (tamaño < 13) {
            numTabs = 7;
            //Si la cadena es menor a 17, retornara 6 tabs
        } else if (tamaño < 17) {
            numTabs = 6;
            //Si la cadena es menor a 25, retornara 5 tabs
        } else if (tamaño < 25) {
            numTabs = 5;
            //Si la cadena es menor a 29, retornara 4 tabs
        } else if (tamaño < 29) {
            numTabs = 4;
            //Si la cadena es menor a 33, retornara 3 tabs
        } else if (tamaño < 33) {
            numTabs = 3;
            //Si la cadena es menor a 37, retornara 2 tabs
        } else if (tamaño < 37) {
            numTabs = 2;
            //Si la cadena es mayor a 36, retornara 2 tabs
        } else if (tamaño > 36) {
            numTabs = 2;
        }
        // Añadir las tabs correspondientes
        result.append(tab.repeat(numTabs));
        return result.toString();
    }

    /**
     * Construye un mensaje de log basado en los parámetros proporcionados.
     * <p>
     * Este método genera una cadena que representa el formato del mensaje de log,
     * incluyendo detalles como la fecha, el usuario, la clase, el método y el nivel
     * de log, separados por tabulaciones para mantener un formato estructurado.
     *
     * @param fecha    La fecha y hora en que se está generando el log.
     * @param clase    El nombre de la clase desde donde se está escribiendo el log.
     * @param metodo   El nombre del método desde donde se está escribiendo el log.
     * @param nivelLog El nivel de log (INFO, ERROR, etc.) que define la gravedad del log.
     * @param texto    El mensaje de texto que describe el evento o la información a registrar.
     * @return Una cadena formateada que contiene la información completa del log.
     */
    private static String buildLogMessage(String fecha, String clase, String metodo, NivelLog nivelLog, String texto) {
        String logBuilder = fecha + getTabs(fecha) +
                getUsuario() + getTabs(getUsuario()) +
                clase + getTabs(clase) +
                metodo + getTabs(metodo) +
                nivelLog + getTabs(nivelLog.toString()) +
                texto;
        return logBuilder;
    }

    /**
     * Escribe la cabecera de un archivo de log.
     * <p>
     * Este método escribe una cabecera predefinida en el archivo de log, que incluye
     * varias líneas de asteriscos para dividir secciones de logs. Se debe invocar
     * cuando se crea un nuevo archivo o cuando se comienza un nuevo bloque de logs.
     *
     * @param bw El objeto {@link BufferedWriter} que se utiliza para escribir en el archivo de log.
     * @throws IOException Si ocurre un error durante la escritura en el archivo de log.
     */
    private static void escribirCabeceraLog(BufferedWriter bw) throws IOException {
        bw.write("\n*\n*\n*\n*\n");
    }

    /***
     * Obtiene la cantidad de veces que se a escrito en el Txt En la ejecución actual
     * @return Retorna la cantidad de veces que se a escrito en el Log.
     */
    protected long getLogtext() {
        return logtext;
    }

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
     * Obtiene el buffer en el que se esta escribiendo actualmente el log
     *
     * @return Buffer en memoria que referencia el archivo en el que se esta escribiendo el log
     */
    protected BufferedWriter getBw() {
        return bw;
    }

    /**
     * Setea el buffer en el que se esta escribiendo actualmente el log
     *
     * @param buffer Buffer en memoria que referencia el archivo en el que se esta escribiendo el log
     */
    protected void setBw(BufferedWriter buffer) {
        this.bw = buffer;
    }

    /***
     * Verifica el tamaño del fichero de log actual, cuando este alcance los 5MB le asignara el nombre
     * LOG_dd-MM-YYYY_HH-MM-SSS.txt donde la fecha y hora que se le coloca, corresponde a la fecha y hora de creación del archivo
     */
    protected synchronized void verificarSizeFichero() {
        try {
            //System.out.println("Nombre hilo Execute: "+Thread.currentThread().getName());
            File logactual = new File(getRuta());
            //Devuelve el tamaño del fichero en Mb
            long sizeFichero = ((logactual.length()) / 1024) / 1024;
            //long sizeFichero=((logactual.length())/1024);
            //System.out.println("Tamaño del archivo en Kb: " +sizeFichero);
            if (sizeFichero > getSizeLog().getSizeLog()) {
                BasicFileAttributes attributes = Files.readAttributes(logactual.toPath(), BasicFileAttributes.class);
                //FileTime time = attributes.creationTime();
                FileTime time = attributes.lastModifiedTime();
                // Genera una fecha formateada para renombrar el archivo.
                String fechaformateada = DATE_FORMATTER.format(Instant.ofEpochMilli(time.toMillis())
                        .atZone(ZoneId.systemDefault()).toLocalDateTime());
                // Genera un número aleatorio entre 0 y 9 para evitar colisiones de nombres.
                int numeroAleatorio = ThreadLocalRandom.current().nextInt(0, 10);
                //System.out.println( "La fecha y hora de creación del archivo es: " + fechaformateada );
                //SimpleDateFormat  formatofecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                //String fechalog=(formatofecha.format(logactual.lastModified())).replace(":","-").replace(" ", "_");
                this.getBw().close();
                String fechalog = fechaformateada.replace(":", "-").replace(" ", "_") + numeroAleatorio;
                String newrute = getRuta().replace(".txt", "") + "_" + fechalog + ".txt";
                File newfile = new File(newrute);
                logactual.renameTo(newfile);
                System.out.println("Archivo renombrado: " + newrute);
                logactual.delete();
                logactual.createNewFile();
                this.setBw(new BufferedWriter(new FileWriter(logactual, true)));
            }
        } catch (Exception e) {
            System.err.println("Exepcion capturada en el metodo Metodo por medio del cual se verifica el tamaño del archivo: " + getRuta() + " Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    /***
     * Metodo para escribir en el Log, lo que esta sucediendo dentro de la prueba,
     * imprime en consola el texto que sera agregado al Log
     * @param nivelLog nivelLog que representa el grado de log del mensaje.
     * @param Texto Texto que es el mensaje que se desea escribir.
     * @param Clase Clase que representa la clase en la cual se mando a llamar la escritura del Log.
     * @param Metodo Metodo que representa el metodo desde el cual se llama la escritura del Log.
     * @param fecha fecha y hora de la escritura del Log.
     */
    protected synchronized void writeLog(NivelLog nivelLog, String Texto, String Clase, String Metodo, String fecha) {
        try {
            //System.out.println("Nombre hilo Execute: "+Thread.currentThread().getName());
            //Aumenta la Cantidad de Veces que se a escrito el Log
            this.setLogtext(this.getLogtext() + 1);
            String logMessage = buildLogMessage(fecha, Clase, Metodo, nivelLog, Texto);
            if (getIsAndroid()) {
                if (getviewConsole()) {
                    System.out.println("\n");
                }
                if (nivelLog.getGradeLog() >= NivelLog.ERROR.getGradeLog()) {
                    if (getviewConsole()) {
                        System.err.println(logMessage);
                    }
                } else {
                    if (getviewConsole()) {
                        System.out.println(logMessage);
                    }
                }
            } else {
                //System.out.println("clase: " + Clase + " metodo: " + Metodo);
                //Rutas de archivos
                File fichero = new File(getRuta());
                //System.out.println("Ruta del log: " + fichero.getAbsolutePath());
                //Verifica si existe la carpeta Logs, si no existe, la Crea
                /////Esta seccion se encarga de Crear y escribir en el Log/////
                //verificarSizeFichero();
                /*Si es un nuevo Test se ejecuta el siguiente codigo, tomando en cuenta que sea el primer
                 * TestCase del Test actual*/
                //Si el fichero no Existe, lo creara y agregara el siguiente texto
                if (!fichero.exists()) {
                    BufferedWriter bw = this.getBw();
                    escribirCabeceraLog(bw);
                    bw.write(logMessage);
                    bw.newLine();
                    if (getviewConsole()) {
                        System.out.println("*" + "\n");
                        System.out.println("*" + "\n");
                        System.out.println("*" + "\n");
                        System.out.println("*" + "\n");
                        System.out.println("*" + "\n");
                        System.out.println(logMessage);
                    }
                } else {
                    if (this.getLogtext() == 1) {
                        BufferedWriter bw = this.getBw();
                        escribirCabeceraLog(bw);
                        bw.write(logMessage);
                        bw.newLine();
                        if (getviewConsole()) {
                            System.out.println("\n");
                            System.out.println(logMessage);
                        }
                    } else {
                        //Agrega en el fichero el Log
                        BufferedWriter bw = this.getBw();
                        bw.newLine();
                        bw.write(logMessage);
                        bw.newLine();
                        if (getviewConsole()) {
                            System.out.println("\n");
                        }
                        if (nivelLog.getGradeLog() >= NivelLog.ERROR.getGradeLog()) {
                            if (getviewConsole()) {
                                System.err.println(logMessage);
                            }
                        } else {
                            if (getviewConsole()) {
                                System.out.println(logMessage);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Exepcion capturada en el metodo Metodo por medio del cual se escribir el log del Text" + " Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }
}
