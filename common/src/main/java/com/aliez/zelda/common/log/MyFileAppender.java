package com.aliez.zelda.common.log;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;

import java.io.*;

/**
 * @author: z.j.
 * @time: 2020-11-27 20:53
 */
public class MyFileAppender extends FileAppender {

    public MyFileAppender() {
    }

    private boolean needHeader(String fileName) throws IOException {
        File filetest = new File(fileName);
        boolean flag = false;
        if (!filetest.exists()) {
            flag = true;
        }

        return flag;
    }

    public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize) throws IOException {
        LogLog.debug("setFile called: " + fileName + ", " + append);
        if (bufferedIO) {
            this.setImmediateFlush(false);
        }

        this.reset();
        boolean needheader = this.needHeader(fileName);
        Writer fw = this.createWriter(new FileOutputStream(fileName, append));
        if (bufferedIO) {
            fw = new BufferedWriter((Writer)fw, bufferSize);
        }

        this.setQWForFiles((Writer)fw);
        this.fileName = fileName;
        this.fileAppend = append;
        this.bufferedIO = bufferedIO;
        this.bufferSize = bufferSize;
        if (needheader) {
            this.writeHeader();
        }

        LogLog.debug("setFile ended");
    }

    public MyFileAppender(Layout layout, String filename, boolean append, boolean bufferedIO, int bufferSize) throws IOException {
        super(layout, filename, append, bufferedIO, bufferSize);
    }

    public MyFileAppender(Layout layout, String filename, boolean append) throws IOException {
        super(layout, filename, append);
    }

    public MyFileAppender(Layout layout, String filename) throws IOException {
        super(layout, filename);
    }
}
