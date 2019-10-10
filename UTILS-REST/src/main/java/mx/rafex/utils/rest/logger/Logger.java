package mx.rafex.utils.rest.logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;

import org.eclipse.jetty.util.log.AbstractLogger;

public class Logger extends AbstractLogger {

    private final java.util.logging.Logger logger;
    private final String name;
    private Boolean debug;

    public Logger(final String name) {
        this.name = name;
        this.logger = java.util.logging.Logger.getLogger(this.name);
        try {
            final InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("logging.properties");
            LogManager.getLogManager().readConfiguration(is);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected org.eclipse.jetty.util.log.Logger newLogger(final String name) {
        return new Logger(name);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void warn(final String message, final Object... objects) {
        this.logger.log(Level.WARNING, message, objects);
    }

    @Override
    public void warn(final Throwable throwable) {
        this.logger.log(Level.WARNING, "", throwable);
    }

    @Override
    public void warn(final String message, final Throwable throwable) {
        this.logger.log(Level.WARNING, message, throwable);
    }

    @Override
    public void info(final String message, final Object... objects) {
        this.logger.log(Level.INFO, message, objects);
    }

    @Override
    public void info(final Throwable throwable) {
        this.logger.log(Level.INFO, "", throwable);
    }

    @Override
    public void info(final String message, final Throwable throwable) {
        this.logger.log(Level.INFO, message, throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return this.debug;
    }

    @Override
    public void setDebugEnabled(final boolean debug) {
        this.debug = debug;
    }

    @Override
    public void debug(final String message, final Object... objects) {
        this.logger.log(Level.FINE, message, objects);
    }

    @Override
    public void debug(final Throwable throwable) {
        this.logger.log(Level.FINE, "", throwable);
    }

    @Override
    public void debug(final String message, final Throwable throwable) {
        this.logger.log(Level.FINE, message, throwable);
    }

    @Override
    public void ignore(final Throwable throwable) {
        this.logger.log(Level.WARNING, "", throwable);
    }
}
