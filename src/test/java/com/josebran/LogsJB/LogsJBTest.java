package com.josebran.LogsJB;

import com.josebran.LogsJB.Numeracion.NivelLog;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Random;

import static com.josebran.LogsJB.LogsJB.*;


@Listeners({org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class})
public class LogsJBTest {


    @Test(testName = "Write Log txt")
    public void writeLog() {
    try{
        LogsJB.setGradeLog(NivelLog.TRACE);
        //LogsJB.debug( "Primer comentario grado Debug");
        Integer i=0;
        Random random= new Random();
        while(i<6000){

            trace( i+" comentario grado Trace".repeat(random.nextInt(5)));

            debug( i+" comentario grado Debug".repeat(random.nextInt(5)));

            info( i+" comentario grado Info".repeat(random.nextInt(5)));

            warning( i+" comentario grado Warning".repeat(random.nextInt(5)));

            error( i+" comentario grado Error".repeat(random.nextInt(5)));

            fatal( i+" comentario grado Fatal".repeat(random.nextInt(5)));

            i=i+6;
        }
        LogsJB.waitForOperationComplete();

    }catch (Exception e){
        System.err.println("Excepcion capturada en el metodo main: "+e.getMessage());
        System.err.println("Trace de la Exepción : "+ ExceptionUtils.getStackTrace(e));
    }
}



}