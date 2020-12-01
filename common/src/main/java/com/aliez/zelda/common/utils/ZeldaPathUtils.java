package com.aliez.zelda.common.utils;


/**
 * @author: z.j.
 * @time: 2020-12-01 14:25
 */
public class ZeldaPathUtils {



    private ZeldaPathUtils() {
    }

    public static String getConfigPath() {

        String fileSep = System.getProperty("file.separator");
        return System.getProperty("user.dir")+fileSep;

    }

    public static void main(String[] args) {
        System.out.println(getConfigPath());
    }

    public static String getOsFileSeparator() {
        String fileSep = System.getProperty("file.separator");
        return fileSep;
    }


}
