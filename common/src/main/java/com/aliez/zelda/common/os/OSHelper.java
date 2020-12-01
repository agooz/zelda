package com.aliez.zelda.common.os;

import java.util.Locale;

/**
 * @author: z.j.
 * @time: 2020-11-25 15:06
 */
public class OSHelper {

    /**
     * 操作系统名称
     */
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
    /**
     * 操作系统架构
     */
    private static final String OS_ARCH = System.getProperty("os.arch").toLowerCase(Locale.ENGLISH);
    /**
     * 操作系统版本
     */
    private static final String OS_VERSION = System.getProperty("os.version").toLowerCase(Locale.ENGLISH);

    private OSHelper(){}


    private static OSEnum osEnum;


    static{
        if(OS_NAME.startsWith("linux")){
            osEnum = OSEnum.LINUX;
        }else if(OS_NAME.startsWith("windows")){
            osEnum = OSEnum.WINDOWS;
        }else if(OS_NAME.startsWith("mac") || OS_NAME.startsWith("darwin")){
            osEnum = OSEnum.MACOSX;
        }else {
            osEnum = OSEnum.UNKNOWN;
        }

    }


    public static boolean isWindows(){
        return osEnum == OSEnum.WINDOWS;
    }

    public static boolean isLinux(){
        return osEnum == OSEnum.LINUX;
    }

    public static boolean isMacOS(){
        return osEnum == OSEnum.MACOSX;
    }

}
