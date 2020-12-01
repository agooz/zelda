package com.aliez.zelda.common.log;

/**
 * @author: z.j.
 * @time: 2020-11-25 16:58
 */
public class LoggerResources {

    /**
     * 日志路径以及名称
     */
    private String logFile = "log/access.log";
    /**
     * 日志分割数量
     */
    private int maxBackupIndex = 10;
    /**
     * 一份日志最大可写入数量
     */
    private String maxFileSize = "10MB";
    /**
     * 一份日志开始内容
     */
    private String header = null;
    /**
     * 日志对象名称 例：Logger.getLogger(this.getLoggerName());
     */
    private String loggerName = null;

    public LoggerResources(){
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public int getMaxBackupIndex() {
        return maxBackupIndex;
    }

    public void setMaxBackupIndex(int maxBackupIndex) {
        this.maxBackupIndex = maxBackupIndex;
    }

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }


}
