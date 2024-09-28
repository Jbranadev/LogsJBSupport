package com.josebran.LogsJB;

import com.josebran.LogsJB.Numeracion.NivelLog;
import com.josebran.LogsJB.Numeracion.SizeLog;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

import static com.josebran.LogsJB.LogsJB.*;

@Listeners({org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class})
public class LogsJBTest {

    @Test(testName = "Write Log sin Setear propiedades")
    public void writeLogSinSetearPropiedades() {
        try {
            File fichero = new File(LogsJB.getRuta());
            //System.out.println("Ruta del log: " + fichero.getAbsolutePath());
            //Verifica si existe la carpeta Logs, si no existe, la Crea
            File directorio = new File(fichero.getParent());
            FileUtils.deleteDirectory(directorio);
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {
                    System.out.println("*");
                    System.out.println("Crea el directorio donde almacenara el Log de la prueba: " + fichero.getParent());
                    System.out.println("*");
                }
            }
            FileUtils.writeStringToFile(fichero, "Creación archivo Primera Vez", Charset.defaultCharset());
            trace(" comentario grado" + " Trace".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
            debug(" comentario grado " + "Debug".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
            info(" comentario grado " + "Info".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
            warning(" comentario grado " + "Warning".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
            error(" comentario grado " + "Error".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
            fatal(" comentario grado " + " Fatal".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
            LogsJB.waitForOperationComplete();
        } catch (Exception e) {
            System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
            System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    @Test(testName = "Write Log Set Is Android", dependsOnMethods = "writeLogSinSetearPropiedades")
    public void writeLogSinSetIsAndroid() {
        LogsJB.setIsAndroid(true);
        LogsJB.getLogsJBProperties();
        Assert.assertTrue(LogsJB.getIsAndroid(), "No se a seteado correctamente el valor de IsAndroid");
        try {
            trace(" comentario grado" + " Trace".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
            debug(" comentario grado " + "Debug".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
            info(" comentario grado " + "Info".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
            warning(" comentario grado " + "Warning".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
            error(" comentario grado " + "Error".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
            fatal(" comentario grado " + " Fatal".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
            LogsJB.waitForOperationComplete();
            LogsJB.setIsAndroid(false);
            LogsJB.getLogsJBProperties();
        } catch (Exception e) {
            System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
            System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    @Test(testName = "Setear Usuario txt", dependsOnMethods = "writeLogSinSetIsAndroid")
    public void setearUsuario() {
        try {
            LogsJB.setUsuario("Carlos Bran");
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getUsuario().equalsIgnoreCase("Carlos Bran"), "El valor de Usuario obtenido no corresponde al seteado");
        } catch (Exception e) {
            System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
            System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    @Test(testName = "Setear Nivel Log txt", dependsOnMethods = "setearUsuario")
    public void setearNivelLog() {
        try {
            LogsJB.setGradeLog(NivelLog.TRACE);
            LogsJB.getLogsJBProperties();
            Assert.assertSame(getGradeLog(), NivelLog.TRACE, "El valor de Log obtenido no corresponde al seteado");
            LogsJB.setGradeLog(NivelLog.DEBUG);
            LogsJB.getLogsJBProperties();
            Assert.assertSame(getGradeLog(), NivelLog.DEBUG, "El valor de Log obtenido no corresponde al seteado");
            LogsJB.setGradeLog(NivelLog.INFO);
            LogsJB.getLogsJBProperties();
            Assert.assertSame(getGradeLog(), NivelLog.INFO, "El valor de Log obtenido no corresponde al seteado");
            LogsJB.setGradeLog(NivelLog.WARNING);
            LogsJB.getLogsJBProperties();
            Assert.assertSame(getGradeLog(), NivelLog.WARNING, "El valor de Log obtenido no corresponde al seteado");
            LogsJB.setGradeLog(NivelLog.ERROR);
            LogsJB.getLogsJBProperties();
            Assert.assertSame(getGradeLog(), NivelLog.ERROR, "El valor de Log obtenido no corresponde al seteado");
            LogsJB.setGradeLog(NivelLog.FATAL);
            LogsJB.getLogsJBProperties();
            Assert.assertSame(getGradeLog(), NivelLog.FATAL, "El valor de Log obtenido no corresponde al seteado");
        } catch (Exception e) {
            System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
            System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    @Test(testName = "Setear Size Log txt", dependsOnMethods = "setearNivelLog")
    public void setearSizeLog() {
        try {
            LogsJB.setSizeLog(SizeLog.Little_Little);
            LogsJB.getLogsJBProperties();
            Assert.assertEquals(SizeLog.Little_Little.getSizeLog(), getSizeLog().getSizeLog(), "El Size de Log obtenido no corresponde al seteado");
            LogsJB.setSizeLog(SizeLog.Little);
            LogsJB.getLogsJBProperties();
            Assert.assertEquals(SizeLog.Little.getSizeLog(), getSizeLog().getSizeLog(), "El Size de Log obtenido no corresponde al seteado");
            LogsJB.setSizeLog(SizeLog.Small_Medium);
            LogsJB.getLogsJBProperties();
            Assert.assertEquals(SizeLog.Small_Medium.getSizeLog(), getSizeLog().getSizeLog(), "El Size de Log obtenido no corresponde al seteado");
            LogsJB.setSizeLog(SizeLog.Medium);
            LogsJB.getLogsJBProperties();
            Assert.assertEquals(SizeLog.Medium.getSizeLog(), getSizeLog().getSizeLog(), "El Size de Log obtenido no corresponde al seteado");
            LogsJB.setSizeLog(SizeLog.Small_Large);
            LogsJB.getLogsJBProperties();
            Assert.assertEquals(SizeLog.Small_Large.getSizeLog(), getSizeLog().getSizeLog(), "El Size de Log obtenido no corresponde al seteado");
            LogsJB.setSizeLog(SizeLog.Large);
            LogsJB.getLogsJBProperties();
            Assert.assertEquals(SizeLog.Large.getSizeLog(), getSizeLog().getSizeLog(), "El Size de Log obtenido no corresponde al seteado");
        } catch (Exception e) {
            System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
            System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    @Test(testName = "Write Log txt Llegar a 8MB", dependsOnMethods = "setearSizeLog")
    public void writeLog() {
        try {
            LogsJB.setGradeLog(NivelLog.TRACE);
            LogsJB.setSizeLog(SizeLog.Little_Little);
            LogsJB.getLogsJBProperties();
            Assert.assertEquals(SizeLog.Little_Little.getSizeLog(), getSizeLog().getSizeLog(), "El Size de Log obtenido no corresponde al seteado");
            ThreadLocalRandom.current().nextInt(5, 14);
            Integer i = 0;
            Random random = new Random();
            while (i < 55000) {
                trace(i + " comentario grado" + " Trace".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
                debug(i + " comentario grado " + "Debug".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
                info(i + " comentario grado " + "Info".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
                warning(i + " comentario grado " + "Warning".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
                error(i + " comentario grado " + "Error".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
                fatal(i + " comentario grado " + " Fatal".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
                i = i + 6;
            }
            LogsJB.waitForOperationComplete();
        } catch (Exception e) {
            System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
            System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    @Test(testName = "Write Log txt Llegar a 15MB", dependsOnMethods = "writeLog")
    public void writeLogVeinNueveTxt() {
        try {
            LogsJB.setGradeLog(NivelLog.TRACE);
            LogsJB.setSizeLog(SizeLog.Little_Little);
            ThreadLocalRandom.current().nextInt(5, 14);
            Integer i = 0;
            Random random = new Random();
            while (i < 55000) {
                trace(i + " comentario grado" + " Trace".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
                debug(i + " comentario grado " + "Debug".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
                info(i + " comentario grado " + "Info".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
                warning(i + " comentario grado " + "Warning".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
                error(i + " comentario grado " + "Error".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
                fatal(i + " comentario grado " + " Fatal".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
                i = i + 6;
            }
            LogsJB.waitForOperationComplete();
        } catch (Exception e) {
            System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
            System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    @Test(testName = "Write Log txt Llegar a 25MB", dependsOnMethods = "writeLogVeinNueveTxt")
    public void writeLogTreintaYSeisTexto333() {
        try {
            LogsJB.setGradeLog(NivelLog.TRACE);
            LogsJB.setSizeLog(SizeLog.Little_Little);
            ThreadLocalRandom.current().nextInt(5, 14);
            Integer i = 0;
            while (i < 55000) {
                trace(i + " comentario grado" + " Trace".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
                debug(i + " comentario grado " + "Debug".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
                info(i + " comentario grado " + "Info".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
                warning(i + " comentario grado " + "Warning".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
                error(i + " comentario grado " + "Error".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
                fatal(i + " comentario grado " + " Fatal".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
                i = i + 6;
            }
            Integer k = 0;
            // Ejecutar el bloque de código de forma asíncrona en un CompletableFuture
            CompletableFuture<Void> futureTask = CompletableFuture.runAsync(() -> {
                try {
                    File fichero = new File(LogsJB.getRuta());
                    //System.out.println("Ruta del log: " + fichero.getAbsolutePath());
                    //Verifica si existe la carpeta Logs, si no existe, la Crea
                    File directorio = new File(fichero.getParent());
                    if (!directorio.exists()) {
                        if (directorio.mkdirs()) {
                            System.out.println("*");
                            System.out.println("Crea el directorio donde almacenara el Log de la prueba: " + fichero.getParent());
                            System.out.println("*");
                        }
                    }
                    // Crear un nuevo archivo llamado "jbran.txt" dentro del directorio
                    File nuevoArchivo = new File(directorio, "jbran.txt");
                    String rutanueva = nuevoArchivo.toPath().toAbsolutePath().normalize().toString();
                    System.out.println("Ruta donde se almacenara el archivo jbran: "+rutanueva);
                    //instance.get().getLogsJBviewConsole();
                    LogsJB.setviewConsole(true);
                    String propiedad = LogsJB.getInstanceLogsJB().getLogsJBviewConsole();
                    String viewConsole = System.getProperty(propiedad);
                    LogsJB.getInstanceLogsJB().setLogsJBviewConsole("logsViewJB");
                    LogsJB.setviewConsole(!Boolean.valueOf(viewConsole));
                    String viewConsole2 = System.getProperty(LogsJB.getInstanceLogsJB().getLogsJBviewConsole());
                    LogsJB.getInstanceLogsJB().getLogsJBProperties();
                    LogsJB.getInstanceLogsJB().setLogsJBRutaLog("logsjbruta");
                    LogsJB.setRuta(rutanueva);
                    LogsJB.setGradeLog(NivelLog.TRACE);
                    LogsJB.setSizeLog(SizeLog.Little_Little);
                    ThreadLocalRandom.current().nextInt(5, 14);  // Inicializa el generador de números aleatorios
                    Integer j = 0;
                    while (j < 6000) {
                        // Ejecución de los logs con diferentes niveles
                        trace(j + " comentario grado" + " Trace".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
                        debug(j + " comentario grado " + "Debug".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
                        info(j + " comentario grado " + "Info".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
                        warning(j + " comentario grado " + "Warning".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
                        error(j + " comentario grado " + "Error".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
                        fatal(j + " comentario grado " + " Fatal".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
                        j += 6;  // Incrementar el contador
                    }
                    LogsJB.waitForOperationComplete();
                } catch (Exception e) {
                    System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
                    System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
                }
            });
            LogsJB.waitForOperationComplete();
            futureTask.join();
            File fichero = new File(LogsJB.getRuta());
            //Verifica si existe la carpeta Logs, si no existe, la Crea
            File directorio = new File(fichero.getParent());
            System.out.println("Directorio donde se almaceno archivo jbran: "+directorio.toPath().toAbsolutePath().normalize().toString());
            // Obtiene los archivos en el directorio
            File[] archivos = directorio.listFiles();

            // Verifica si hay archivos y los imprime
            if (archivos != null && archivos.length > 0) {
                System.out.println("Archivos en el directorio 'Logs':");
                for (File archivo : archivos) {
                    System.out.println(archivo.getName());
                }
            } else {
                System.out.println("No hay archivos en el directorio 'Logs'.");
            }
            Assert.assertEquals(FileUtils.listFiles(directorio, null, false).size(), 3, "El Directorio no contiene más de un archivo");
        } catch (Exception e) {
            System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
            System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }

    @Test(testName = "Write Log Segunda Vez txt", dependsOnMethods = "writeLogTreintaYSeisTexto333")
    public void writeLogSegundaOcasionMayorTreintaYSeis() {
        try {
            LogsJB.setGradeLog(NivelLog.TRACE);
            LogsJB.setSizeLog(SizeLog.Little_Little);
            LogsJB.getLogsJBProperties();
            //LogsJB.debug( "Primer comentario grado Debug");
            //System.out.println("clase: " + Clase + " metodo: " + Metodo);
            //Rutas de archivos
            File fichero = new File(getRuta());
            //System.out.println("Ruta del log: " + fichero.getAbsolutePath());
            //Verifica si existe la carpeta Logs, si no existe, la Crea
            File directorio = new File(fichero.getParent());
            //FileUtils.deleteDirectory(directorio);
            Integer i = 0;
            Random random = new Random();
            while (i < 1200) {
                trace(i + " comentario grado" + " Trace".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
                debug(i + " comentario grado" + " Debug".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
                info(i + " comentario grado" + " Info".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
                warning(i + " comentario grado" + " Warning".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
                error(i + " comentario grado" + " Error".repeat(ThreadLocalRandom.current().nextInt(5, 14)));
                fatal(i + " comentario grado" + " Fatal".repeat(ThreadLocalRandom.current().nextInt(0, 10)));
                i = i + 6;
            }
            LogsJB.waitForOperationComplete();
            while (i < 1200) {
                trace(i + "cadena contar caracteres26");
                debug(i + "cadena contar caracteres treinta3");
                info(i + "cadena contar caracteres treinta 6");
                warning(i + "cadena contar caracteres treinta Siete");
                error(i + " comentario grado");
                fatal(i + " comentario grado");
                i = i + 6;
            }
            LogsJB.waitForOperationComplete();
        } catch (Exception e) {
            System.err.println("Excepcion capturada en el metodo main: " + e.getMessage());
            System.err.println("Trace de la Exepción : " + ExceptionUtils.getStackTrace(e));
        }
    }
}