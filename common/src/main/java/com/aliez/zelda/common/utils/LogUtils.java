package com.aliez.zelda.common.utils;

import com.aliez.zelda.common.log.AccessLogger;
import com.aliez.zelda.common.log.DebugLogger;
import com.aliez.zelda.common.log.ErrorLogger;
import com.aliez.zelda.common.log.LoggerResources;
import com.aliez.zelda.common.log.exception.LoggerException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

/**
 * @author: z.j.
 * @time: 2020-12-01 11:55
 */
public class LogUtils {

    /**
     * config
     */
    private static boolean isDebug;
    private static String logIdentifier = "zelda-log";
    private static String logFileBasePath = "";

    /**
     * debug.log
     */
    private static LoggerResources debugRes = null;

    /**
     * access.log
     */
    private static LoggerResources accessRes = null;

    /**
     * error.log
     */
    private static LoggerResources errorRes = null;

    /**
     * instance
     */
    private static DebugLogger debugLog = null;
    private static AccessLogger accessLog = null;
    private static ErrorLogger errorLog = null;


    /**
     * write lock
     */
    private static final Object lockDebug;
    private static final Object lockError;
    private static Object lockAccess;



    static {

        logFileBasePath = ZeldaPathUtils.getConfigPath();
        isDebug = false;

        //init debug res
        debugRes = new LoggerResources();
        debugRes.setLoggerName("com.aliez.zelda.debug");
        String debugFileName =getLogPath()+logIdentifier+ZeldaPathUtils.getOsFileSeparator()+"debug.log";
        debugRes.setLogFile(debugFileName);
        debugRes.setMaxBackupIndex(10);
        debugRes.setMaxFileSize("20MB");

        //init access res
        accessRes = new LoggerResources();
        accessRes.setLoggerName("com.aliez.zelda.access");
        String accessFileName =getLogPath()+logIdentifier+ZeldaPathUtils.getOsFileSeparator()+"access.log";
        accessRes.setLogFile(accessFileName);
        accessRes.setMaxBackupIndex(10);
        accessRes.setMaxFileSize("20MB");


        //init error res
        errorRes = new LoggerResources();
        errorRes.setLoggerName("com.aliez.zelda.error");
        String errorFileName =getLogPath()+logIdentifier+ZeldaPathUtils.getOsFileSeparator()+"error.log";
        errorRes.setLogFile(errorFileName);
        errorRes.setMaxBackupIndex(10);
        errorRes.setMaxFileSize("20MB");

        lockDebug = new Object();
        lockError = new Object();
        lockAccess = new Object();

    }



    private LogUtils() {
    }




    public static void debuglog(String s) {
        if (isDebug) {
            if (debugLog == null) {
                synchronized(lockDebug) {
                    try {
                        debugLog = new DebugLogger(debugRes);
                    } catch (LoggerException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (debugLog != null) {
                debugLog.Log(s);
            }

        }
    }

    public static void debuglog(String log, Throwable tr) {
        if (isDebug) {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(bao);
            tr.printStackTrace(out);
            debuglog(log);
            debuglog(bao.toString());
        }
    }



    public static void errorlog(String log, Throwable tr) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bao);
        tr.printStackTrace(out);
        errorlog(log);
        errorlog(bao.toString());
    }

    public static void errorlog(String s) {
        if (errorLog == null) {
            synchronized(lockError) {
                try {
                    errorLog = new ErrorLogger(errorRes);
                } catch (LoggerException e) {
                    e.printStackTrace();
                }
            }
        }
        errorLog.Log(s);
    }



    public static void accesslog(String s) {
        if (accessLog == null) {
            synchronized(lockAccess) {
                try {
                    accessLog = new AccessLogger(accessRes);
                } catch (LoggerException e) {
                    e.printStackTrace();
                }
            }
        }
        accessLog.Log(s);
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean isDebug) {
        isDebug = isDebug;
    }




    private static String getLogPath() {
        File f = new File(logFileBasePath);
        if (!f.exists()) {
            boolean b = f.mkdir();
        }

        return logFileBasePath;
    }
}
