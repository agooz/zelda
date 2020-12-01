package com.aliez.zelda.common.log;

import com.aliez.zelda.common.log.exception.LoggerException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author: z.j.
 * @time: 2020-12-01 10:24
 */
public class ErrorLogger extends AbstractLogger{


    public ErrorLogger(LoggerResources res) throws LoggerException{
        this.createErrorLogger(res);
    }

    public void createErrorLogger(LoggerResources res) throws LoggerException{
        this.logger = Logger.getLogger(res.getLoggerName());
        this.logger.removeAllAppenders();


        MyPatternLayout layout = new MyPatternLayout();
        layout.SetHeader(res.getHeader());
        /**
         * %m：输出代码中指定的消息。
         * %p：输出优先级，即DEBUG，INFO，WARN，ERROR，FATAL。如果是调用debug()输出的，则为DEBUG，依此类推
         * %r：输入自应用启动到输出该log信息耗费的毫秒数。
         * %c：输出所属的类目，通常就是所在类的全名。
         * %n：输出一个回车换行符。Windows平台为“\r\n”，UNIX为“\n”。
         * %d：输出日志时间点的日期或时间，默认格式为ISO8601，推荐使用“%d{ABSOLUTE}”，这个输出格式形如：“2007-05-07 18:23:23,500”，符合中国人习惯。
         * %l：输出日志事件发生的位置，包括类名、线程名，以及所在代码的行数。
         * %f 输出日志信息所属的类的类名
         */

        layout.setConversionPattern("[%d{yyyy-MM-dd HH:mm:ss S}] %l %m%n");
        MyRollingFileAppender da = null;

        try {
            da = new MyRollingFileAppender(layout, res.getLogFile());
            da.setMaxBackupIndex(res.getMaxBackupIndex());
            da.setMaxFileSize(res.getMaxFileSize());
            this.logger.addAppender(da);
            this.logger.setLevel(Level.ERROR);
        } catch (Exception e) {
            throw new LoggerException(e);
        }
    }

    @Override
    public void Log(String msg) {
        this.logger.error(msg);
    }


}
