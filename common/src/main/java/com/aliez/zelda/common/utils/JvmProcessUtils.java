package com.aliez.zelda.common.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: z.j.
 * @time: 2020-12-09 17:23
 */
public class JvmProcessUtils {

    private static final String SPARK_PROCESS_KEYWORD = "spark.yarn.app.container.log.dir";
    private static final String SPARK_CMDLINE_KEYWORD = "spark.";
    private static final String SPARK_EXECUTOR_CLASS_NAME = "spark.executor.CoarseGrainedExecutorBackend";
    private static final String SPARK_EXECUTOR_KEYWORD = "spark.driver.port";

    private static final Pattern XMX_REGEX = Pattern.compile("-[xX][mM][xX]([a-zA-Z0-9]+)");

    public static String getCurrentProcessName() {
        try {
            return ManagementFactory.getRuntimeMXBean().getName();
        } catch (Throwable ex) {
            return ex.getMessage();
        }
    }

    public static String getJvmClassPath() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return runtimeMXBean.getClassPath();
    }

    public static List<String> getJvmInputArguments() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        List<String> jvmArgs = runtimeMXBean.getInputArguments();
        return jvmArgs == null ? new ArrayList<>() : jvmArgs;
    }

    public static Long getJvmXmxBytes() {
        Long result = null;

        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        List<String> jvmArgs = runtimeMXBean.getInputArguments();
        if (jvmArgs == null) {
            return null;
        }

        for (String entry : jvmArgs) {
            Matcher matcher = XMX_REGEX.matcher(entry);
            if (matcher.matches()) {
                String str = matcher.group(1);
                result = getBytesValueOrNull(str);
            }
        }

        return result;
    }

    public static boolean isSparkProcess(String cmdline) {
        if (cmdline != null && !cmdline.isEmpty()) {
            if (cmdline.contains(SPARK_CMDLINE_KEYWORD)) {
                return true;
            }
        }

        List<String> strList = JvmProcessUtils.getJvmInputArguments();
        for (String str : strList) {
            if (str.toLowerCase().contains(SPARK_PROCESS_KEYWORD.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSparkExecutor(String cmdline) {
        if (cmdline != null && !cmdline.isEmpty()) {
            if (cmdline.contains(SPARK_EXECUTOR_CLASS_NAME)) {
                return true;
            }
        }

        List<String> strList = JvmProcessUtils.getJvmInputArguments();
        for (String str : strList) {
            if (str.toLowerCase().contains(SPARK_EXECUTOR_KEYWORD.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSparkDriver(String cmdline) {
        return isSparkProcess(cmdline) && !isSparkExecutor(cmdline);
    }

    private static final int KB_SIZE = 1024;
    private static final int MB_SIZE = 1024 * 1024;
    private static final int GB_SIZE = 1024 * 1024 * 1024;

    public static Long getBytesValueOrNull(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }

        str = str.toLowerCase();
        int scale = 1;

        try {
            if (str.endsWith("kb")) {
                str = str.substring(0, str.length() - 2).trim();
                scale = KB_SIZE;
            } if (str.endsWith("k")) {
                str = str.substring(0, str.length() - 1).trim();
                scale = KB_SIZE;
            } else if (str.endsWith("mb")) {
                str = str.substring(0, str.length() - 2).trim();
                scale = MB_SIZE;
            } else if (str.endsWith("m")) {
                str = str.substring(0, str.length() - 1).trim();
                scale = MB_SIZE;
            } else if (str.endsWith("gb")) {
                str = str.substring(0, str.length() - 2).trim();
                scale = GB_SIZE;
            } else if (str.endsWith("g")) {
                str = str.substring(0, str.length() - 1).trim();
                scale = GB_SIZE;
            } else if (str.endsWith("bytes")) {
                str = str.substring(0, str.length() - "bytes".length()).trim();
                scale = 1;
            }

            str = str.replace(",", "");

//            if (!NumberUtils.isNumber(str)) {
//                return null;
//            }

            double doubleValue = Double.parseDouble(str);
            return (long)(doubleValue * scale);
        } catch (Throwable ex) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(getCurrentProcessName());
        System.out.println(isSparkProcess(null));
        System.out.println(isSparkExecutor(null));
        System.out.println(isSparkDriver(null));
    }
}
