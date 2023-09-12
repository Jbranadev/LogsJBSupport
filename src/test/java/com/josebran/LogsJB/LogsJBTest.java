package com.josebran.LogsJB;

import com.josebran.LogsJB.Numeracion.NivelLog;
import com.josebran.LogsJB.Numeracion.SizeLog;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import javax.swing.text.Utilities;
import java.io.File;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.josebran.LogsJB.LogsJB.*;


@Listeners({org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class})
public class LogsJBTest {
    @Test(testName = "Setear Usuario txt")
    public void setearUsuario() {
        try{
            LogsJB.setUsuario("Carlos Bran");
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getUsuario().equalsIgnoreCase("Carlos Bran"), "El valor de Usuario obtenido no corresponde al seteado");
        }catch (Exception e){
            System.err.println("Excepcion capturada en el metodo main: "+e.getMessage());
            System.err.println("Trace de la Exepción : "+ ExceptionUtils.getStackTrace(e));
        }
    }

    @Test(testName = "Setear Nivel Log txt")
    public void setearNivelLog() {
        try{
            LogsJB.setGradeLog(NivelLog.TRACE);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getGradeLog()==NivelLog.TRACE, "El valor de Log obtenido no corresponde al seteado");

            LogsJB.setGradeLog(NivelLog.DEBUG);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getGradeLog()==NivelLog.DEBUG, "El valor de Log obtenido no corresponde al seteado");

            LogsJB.setGradeLog(NivelLog.INFO);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getGradeLog()==NivelLog.INFO, "El valor de Log obtenido no corresponde al seteado");

            LogsJB.setGradeLog(NivelLog.WARNING);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getGradeLog()==NivelLog.WARNING, "El valor de Log obtenido no corresponde al seteado");

            LogsJB.setGradeLog(NivelLog.ERROR);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getGradeLog()==NivelLog.ERROR, "El valor de Log obtenido no corresponde al seteado");

            LogsJB.setGradeLog(NivelLog.FATAL);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getGradeLog()==NivelLog.FATAL, "El valor de Log obtenido no corresponde al seteado");

        }catch (Exception e){
            System.err.println("Excepcion capturada en el metodo main: "+e.getMessage());
            System.err.println("Trace de la Exepción : "+ ExceptionUtils.getStackTrace(e));
        }
    }



    @Test(testName = "Setear Size Log txt", dependsOnMethods = "setearNivelLog")
    public void setearSizeLog() {
        try{
            LogsJB.setSizeLog(SizeLog.Little_Little);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getSizeLog().getSizeLog()==SizeLog.Little_Little.getSizeLog(), "El Size de Log obtenido no corresponde al seteado");


            LogsJB.setSizeLog(SizeLog.Little);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getSizeLog().getSizeLog()==SizeLog.Little.getSizeLog(), "El Size de Log obtenido no corresponde al seteado");

            LogsJB.setSizeLog(SizeLog.Small_Medium);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getSizeLog().getSizeLog()==SizeLog.Small_Medium.getSizeLog(), "El Size de Log obtenido no corresponde al seteado");

            LogsJB.setSizeLog(SizeLog.Medium);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getSizeLog().getSizeLog()==SizeLog.Medium.getSizeLog(), "El Size de Log obtenido no corresponde al seteado");

            LogsJB.setSizeLog(SizeLog.Small_Large);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getSizeLog().getSizeLog()==SizeLog.Small_Large.getSizeLog(), "El Size de Log obtenido no corresponde al seteado");

            LogsJB.setSizeLog(SizeLog.Large);
            LogsJB.getLogsJBProperties();
            Assert.assertTrue(LogsJB.getSizeLog().getSizeLog()==SizeLog.Large.getSizeLog(), "El Size de Log obtenido no corresponde al seteado");



        }catch (Exception e){
            System.err.println("Excepcion capturada en el metodo main: "+e.getMessage());
            System.err.println("Trace de la Exepción : "+ ExceptionUtils.getStackTrace(e));
        }
    }


    @Test(testName = "Write Log txt Llegar a 8MB", dependsOnMethods = "setearSizeLog")
    public void writeLog() {
    try{
        LogsJB.setGradeLog(NivelLog.TRACE);
        LogsJB.setSizeLog(SizeLog.Little_Little);
        ThreadLocalRandom.current().nextInt(5,14);
        Integer i=0;
        Random random= new Random();
        while(i<55000){

            trace( i+" comentario grado"+" Trace".repeat(ThreadLocalRandom.current().nextInt(5,14)));

            debug( i+" comentario grado "+"Debug".repeat(ThreadLocalRandom.current().nextInt(0,10)));

            info( i+" comentario grado "+"Info".repeat(ThreadLocalRandom.current().nextInt(5,14)));

            warning( i+" comentario grado "+"Warning".repeat(ThreadLocalRandom.current().nextInt(0,10)));

            error( i+" comentario grado "+"Error".repeat(ThreadLocalRandom.current().nextInt(5,14)));

            fatal( i+" comentario grado "+" Fatal".repeat(ThreadLocalRandom.current().nextInt(0,10)));

            i=i+6;
        }
        LogsJB.waitForOperationComplete();

    }catch (Exception e){
        System.err.println("Excepcion capturada en el metodo main: "+e.getMessage());
        System.err.println("Trace de la Exepción : "+ ExceptionUtils.getStackTrace(e));
    }
}

    @Test(testName = "Write Log txt Llegar a 15MB", dependsOnMethods = "writeLog")
    public void writeLogVeintiNueveTxt() {
        try{
            LogsJB.setGradeLog(NivelLog.TRACE);
            LogsJB.setSizeLog(SizeLog.Little_Little);
            ThreadLocalRandom.current().nextInt(5,14);
            Integer i=0;
            Random random= new Random();
            while(i<55000){

                trace( i+" comentario grado"+" Trace".repeat(ThreadLocalRandom.current().nextInt(5,14)));

                debug( i+" comentario grado "+"Debug".repeat(ThreadLocalRandom.current().nextInt(0,10)));

                info( i+" comentario grado "+"Info".repeat(ThreadLocalRandom.current().nextInt(5,14)));

                warning( i+" comentario grado "+"Warning".repeat(ThreadLocalRandom.current().nextInt(0,10)));

                error( i+" comentario grado "+"Error".repeat(ThreadLocalRandom.current().nextInt(5,14)));

                fatal( i+" comentario grado "+" Fatal".repeat(ThreadLocalRandom.current().nextInt(0,10)));

                i=i+6;
            }
            LogsJB.waitForOperationComplete();

        }catch (Exception e){
            System.err.println("Excepcion capturada en el metodo main: "+e.getMessage());
            System.err.println("Trace de la Exepción : "+ ExceptionUtils.getStackTrace(e));
        }
    }

    @Test(testName = "Write Log txt Llegar a 25MB", dependsOnMethods = "writeLogVeintiNueveTxt")
    public void writeLogTreintaYSeisTexto333() {
        try{
            LogsJB.setGradeLog(NivelLog.TRACE);
            LogsJB.setSizeLog(SizeLog.Little_Little);
            ThreadLocalRandom.current().nextInt(5,14);
            Integer i=0;
            Random random= new Random();
            while(i<55000){

                trace( i+" comentario grado"+" Trace".repeat(ThreadLocalRandom.current().nextInt(5,14)));

                debug( i+" comentario grado "+"Debug".repeat(ThreadLocalRandom.current().nextInt(0,10)));

                info( i+" comentario grado "+"Info".repeat(ThreadLocalRandom.current().nextInt(5,14)));

                warning( i+" comentario grado "+"Warning".repeat(ThreadLocalRandom.current().nextInt(0,10)));

                error( i+" comentario grado "+"Error".repeat(ThreadLocalRandom.current().nextInt(5,14)));

                fatal( i+" comentario grado "+" Fatal".repeat(ThreadLocalRandom.current().nextInt(0,10)));

                i=i+6;
            }
            LogsJB.waitForOperationComplete();

        }catch (Exception e){
            System.err.println("Excepcion capturada en el metodo main: "+e.getMessage());
            System.err.println("Trace de la Exepción : "+ ExceptionUtils.getStackTrace(e));
        }
    }



    @Test(testName = "Write Log Segunda Vez txt", dependsOnMethods = "writeLogTreintaYSeisTexto333")
    public void writeLogSegundaOcasionMayorTreintaYSeis() {
        try{
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
            FileUtils.deleteDirectory(directorio);


            Integer i=0;
            Random random= new Random();
            while(i<1200){

                trace( i+" comentario grado"+" Trace".repeat(ThreadLocalRandom.current().nextInt(5,14)));

                debug( i+" comentario grado"+" Debug".repeat(ThreadLocalRandom.current().nextInt(0,10)));

                info( i+" comentario grado"+" Info".repeat(ThreadLocalRandom.current().nextInt(5,14)));

                warning( i+" comentario grado"+" Warning".repeat(ThreadLocalRandom.current().nextInt(0,10)));

                error( i+" comentario grado"+" Error".repeat(ThreadLocalRandom.current().nextInt(5,14)));

                fatal( i+" comentario grado"+" Fatal".repeat(ThreadLocalRandom.current().nextInt(0,10)));

                i=i+6;
            }
            LogsJB.waitForOperationComplete();
            Thread.sleep(1000);
            while(i<1200){

                trace( i+"cadena contar caracteres26");

                debug( i+"cadena contar caracteres treinta3");

                info( i+"cadena contar caracteres treinta 6");

                warning( i+"cadena contar caracteres treinta Siete");

                error( i+" comentario grado");

                fatal( i+" comentario grado");

                i=i+6;
            }
            LogsJB.waitForOperationComplete();

        }catch (Exception e){
            System.err.println("Excepcion capturada en el metodo main: "+e.getMessage());
            System.err.println("Trace de la Exepción : "+ ExceptionUtils.getStackTrace(e));
        }
    }



}