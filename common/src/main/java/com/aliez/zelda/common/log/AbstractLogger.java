package com.aliez.zelda.common.log;

import org.apache.log4j.Logger;

/**
 * @author: z.j.
 * @time: 2020-11-25 16:46
 */
public abstract class AbstractLogger {
    protected Logger logger = null;

    public AbstractLogger() {
    }

    public abstract void Log(String str);
}
