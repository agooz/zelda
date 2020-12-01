package com.aliez.zelda.common.log.exception;

/**
 * @author: z.j.
 * @time: 2020-11-25 16:35
 */
public class LoggerException extends Exception {


    public LoggerException() {
    }

    public LoggerException(String message) {
        super(message);
    }

    public LoggerException(Throwable cause) {
        super(cause.getMessage());
    }
}
