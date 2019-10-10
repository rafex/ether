package mx.rafex.utils.rest.logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.eclipse.jetty.util.log.AbstractLogger;

public class MyLogger extends AbstractLogger {
    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static private FileHandler fileHTML;
    static private Formatter formatterHTML;

    static public void setup() throws IOException {

        // get the global logger to configure it
        final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        // suppress the logging output to the console
        final Logger rootLogger = Logger.getLogger("");
        final Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler("/var/log/microservicios/Logging.txt");
        fileHTML = new FileHandler("/var/log/microservicios/Logging.html");

        // create a TXT formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);

        // create an HTML formatter
        formatterHTML = new MyHtmlFormatter();
        fileHTML.setFormatter(formatterHTML);
        logger.addHandler(fileHTML);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void warn(final String msg, final Object... args) {
        // TODO Auto-generated method stub

    }

    @Override
    public void warn(final Throwable thrown) {
        // TODO Auto-generated method stub

    }

    @Override
    public void warn(final String msg, final Throwable thrown) {
        // TODO Auto-generated method stub

    }

    @Override
    public void info(final String msg, final Object... args) {
        // TODO Auto-generated method stub

    }

    @Override
    public void info(final Throwable thrown) {
        // TODO Auto-generated method stub

    }

    @Override
    public void info(final String msg, final Throwable thrown) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isDebugEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setDebugEnabled(final boolean enabled) {
        // TODO Auto-generated method stub

    }

    @Override
    public void debug(final String msg, final Object... args) {
        // TODO Auto-generated method stub

    }

    @Override
    public void debug(final Throwable thrown) {
        // TODO Auto-generated method stub

    }

    @Override
    public void debug(final String msg, final Throwable thrown) {
        // TODO Auto-generated method stub

    }

    @Override
    public void ignore(final Throwable ignored) {
        // TODO Auto-generated method stub

    }

    @Override
    protected org.eclipse.jetty.util.log.Logger newLogger(final String fullname) {
        // TODO Auto-generated method stub
        return null;
    }
}
