package com.josebran.LogsJB;

import com.josebran.LogsJB.Numeracion.NivelLog;
import com.josebran.LogsJB.Numeracion.SizeLog;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.josebran.LogsJB.LogsJB.*;

@Listeners({org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class})
public class Test2 {

    @Test(testName = "Write Log Segunda Vez txt")
    public void writeLogSegundaOcasion() {
        try{
            LogsJB.setGradeLog(NivelLog.TRACE);
            LogsJB.setSizeLog(SizeLog.Little_Little);
            LogsJB.getLogsJBProperties();
            Integer i=0;
            while(i<6){

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
