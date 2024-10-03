package com.djm.inventa.admin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerApp {
    //TRACE|DEBUG|INFO|WARN|ERROR|FATAL
    private static final Logger logger = LoggerFactory.getLogger(LoggerApp.class);
    private static final Logger loggerFile = LoggerFactory.getLogger("LoogerFile");

    public static void trace(String msg){
        logger.trace(msg);
    }
    public static void debug(String msg){
        logger.debug(msg);
    }
    public static void warn(String msg){
        logger.trace(msg);
    }
    public static void error(String msg){
        loggerFile.error(msg);
    }
    public static void errorException(Exception exc){
        String msgExc = null;
        // Obtener la traza de pila de la excepción
        StackTraceElement[] stackTrace = exc.getStackTrace();

        // Imprimir el método donde ocurrió la excepción
        if (stackTrace.length > 0) {
            StackTraceElement element = stackTrace[0];
            msgExc = element.getClassName() + "." + element.getMethodName() ;
        }

        loggerFile.error(msgExc+" "+exc.getMessage());
    }
    public static void info(String msg){
        loggerFile.info(msg);
    }
}
