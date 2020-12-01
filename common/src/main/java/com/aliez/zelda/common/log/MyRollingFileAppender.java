package com.aliez.zelda.common.log;

import org.apache.log4j.Layout;
import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.LoggingEvent;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 * @author: z.j.
 * @time: 2020-11-27 20:53
 */
public class MyRollingFileAppender extends MyFileAppender{

    protected long maxFileSize = 30*1024*1024;
    protected int maxBackupIndex = 1;

    public MyRollingFileAppender() {
    }

    public MyRollingFileAppender(Layout layout, String filename, boolean append) throws IOException {
        super(layout, filename, append);
    }

    public MyRollingFileAppender(Layout layout, String filename) throws IOException {
        super(layout, filename);
    }

    public int getMaxBackupIndex() {
        return this.maxBackupIndex;
    }

    public long getMaximumFileSize() {
        return this.maxFileSize;
    }


    public void rollOver() {
        LogLog.debug("rolling over count=" + ((CountingQuietWriter)this.qw).getCount());
        LogLog.debug("maxBackupIndex=" + this.maxBackupIndex);
        if (this.maxBackupIndex > 0) {
            File file = new File(this.fileName + '.' + this.maxBackupIndex);
            if (file.exists()) {
                file.delete();
            }

            File target;
            for(int i = this.maxBackupIndex - 1; i >= 1; --i) {
                file = new File(this.fileName + "." + i);
                if (file.exists()) {
                    target = new File(this.fileName + '.' + (i + 1));
                    LogLog.debug("Renaming file " + file + " to " + target);
                    file.renameTo(target);
                }
            }

            target = new File(this.fileName + "." + 1);
            this.closeFile();
            file = new File(this.fileName);
            LogLog.debug("Renaming file " + file + " to " + target);
            file.renameTo(target);
        }

        try {
            this.setFile(this.fileName, false, this.bufferedIO, this.bufferSize);
        } catch (IOException var4) {
            LogLog.error("setFile(" + this.fileName + ", false) call failed.", var4);
        }

    }

    public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize) throws IOException {
        super.setFile(fileName, append, this.bufferedIO, this.bufferSize);
        if (append) {
            File f = new File(fileName);
            ((CountingQuietWriter)this.qw).setCount(f.length());
        }

    }

    public void setMaxBackupIndex(int maxBackups) {
        this.maxBackupIndex = maxBackups;
    }

    public void setMaximumFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public void setMaxFileSize(String value) {
        this.maxFileSize = OptionConverter.toFileSize(value, this.maxFileSize + 1L);
    }

    protected void setQWForFiles(Writer writer) {
        this.qw = new CountingQuietWriter(writer, this.errorHandler);
    }

    protected void subAppend(LoggingEvent event) {
        super.subAppend(event);
        if (this.fileName != null && ((CountingQuietWriter)this.qw).getCount() >= this.maxFileSize) {
            this.rollOver();
        }

    }

}
