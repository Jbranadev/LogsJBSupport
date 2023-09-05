package com.josebran.LogsJB;

import com.josebran.LogsJB.Numeracion.NivelLog;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.josebran.LogsJB.LogsJB.*;


@Listeners({org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class})
public class LogsJBTest {


    @Test(testName = "Write Log txt")
    public void writeLog() {
    try{
        LogsJB.setGradeLog(NivelLog.TRACE);
        //LogsJB.debug( "Primer comentario grado Debug");
        Integer i=0;
        while(i<6000){

            trace( i+" comentario grado Trace");

            debug( i+" comentario grado Debug");

            info( i+" comentario grado Info");

            warning( i+" comentario grado Warning");

            error( i+" comentario grado Error");

            fatal( i+" comentario grado Fatal");

            i=i+6;
        }

        LogsJB.waitForOperationComplete();


    }catch (Exception e){
        System.err.println("Excepcion capturada en el metodo main: "+e.getMessage());
    }
}



}