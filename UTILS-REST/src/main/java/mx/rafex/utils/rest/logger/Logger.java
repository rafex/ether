package mx.rafex.utils.rest.logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.log.AbstractLogger;
import org.eclipse.jetty.util.log.Log;

public class Logger extends AbstractLogger {

    static {
        try {
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();
            final InputStream inputStream = loader.getResourceAsStream(Logger.LOGGING_PROPERTIES);
            LogManager.getLogManager().readConfiguration(inputStream);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    private static final String THIS_CLASS = Logger.class.getName();
    private static final String LOGGING_PROPERTIES = "logging.properties";
    private static final String JETTY_PROPERTIES = "jetty-logging.properties";

    private final java.util.logging.Logger logger;
    private final String name;
    private final Properties properties = new Properties();
    private final boolean source = Boolean
            .parseBoolean(properties.getProperty("org.eclipse.jetty.util.log.SOURCE", properties.getProperty("org.eclipse.jetty.util.log.logger.SOURCE", "true")));
    private Level configuredLevel;

    public Logger() {
        this("org.eclipse.jetty.util.log.logger");
    }

    public Logger(final String name) {
        this.name = name;
        logger = java.util.logging.Logger.getLogger(this.name);

        loadProperties(Logger.JETTY_PROPERTIES, properties);

        switch (lookupLoggingLevel(properties, name)) {
            case LEVEL_ALL:
                logger.setLevel(Level.ALL);
                break;
            case LEVEL_DEBUG:
                logger.setLevel(Level.FINE);
                break;
            case LEVEL_INFO:
                logger.setLevel(Level.INFO);
                break;
            case LEVEL_WARN:
                logger.setLevel(Level.WARNING);
                break;
            case LEVEL_OFF:
                logger.setLevel(Level.OFF);
                break;
            case LEVEL_DEFAULT:
            default:
                break;
        }

        configuredLevel = logger.getLevel();

    }

    @Override
    protected org.eclipse.jetty.util.log.Logger newLogger(final String name) {
        return new Logger(name);
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public void warn(final String msg, final Object... args) {
        if (logger.isLoggable(Level.WARNING)) {
            log(Level.WARNING, format(msg, args), null);
        }
    }

    @Override
    public void warn(final Throwable thrown) {
        if (logger.isLoggable(Level.WARNING)) {
            log(Level.WARNING, "", thrown);
        }
    }

    @Override
    public void warn(final String msg, final Throwable thrown) {
        if (logger.isLoggable(Level.WARNING)) {
            log(Level.WARNING, msg, thrown);
        }
    }

    @Override
    public void info(final String msg, final Object... args) {
        if (logger.isLoggable(Level.INFO)) {
            log(Level.INFO, format(msg, args), null);
        }
    }

    @Override
    public void info(final Throwable thrown) {
        if (logger.isLoggable(Level.INFO)) {
            log(Level.INFO, "", thrown);
        }
    }

    @Override
    public void info(final String msg, final Throwable thrown) {
        if (logger.isLoggable(Level.INFO)) {
            log(Level.INFO, msg, thrown);
        }
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isLoggable(Level.FINE);
    }

    @Override
    public void setDebugEnabled(final boolean enabled) {
        if (enabled) {
            configuredLevel = logger.getLevel();
            logger.setLevel(Level.FINE);
        } else {
            logger.setLevel(configuredLevel);
        }
    }

    @Override
    public void debug(final String msg, final Object... args) {
        if (logger.isLoggable(Level.FINE)) {
            log(Level.FINE, format(msg, args), null);
        }
    }

    @Override
    public void debug(final String msg, final long arg) {
        if (logger.isLoggable(Level.FINE)) {
            log(Level.FINE, format(msg, arg), null);
        }
    }

    @Override
    public void debug(final Throwable thrown) {
        if (logger.isLoggable(Level.FINE)) {
            log(Level.FINE, "", thrown);
        }
    }

    @Override
    public void debug(final String msg, final Throwable thrown) {
        if (logger.isLoggable(Level.FINE)) {
            log(Level.FINE, msg, thrown);
        }
    }

    @Override
    public void ignore(final Throwable ignored) {
        if (logger.isLoggable(Level.FINEST)) {
            log(Level.FINEST, Log.IGNORED, ignored);
        }
    }

    protected void log(final Level level, final String msg, final Throwable thrown) {
        final LogRecord record = new LogRecord(level, msg);
        if (thrown != null) {
            record.setThrown(thrown);
        }
        record.setLoggerName(logger.getName());
        if (source) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            for (final StackTraceElement e : stack) {
                if (!e.getClassName().equals(THIS_CLASS)) {
                    record.setSourceClassName(e.getClassName());
                    record.setSourceMethodName(e.getMethodName());
                    break;
                }
            }
        }
        logger.log(record);
    }

    private String format(String msg, final Object... args) {
        msg = String.valueOf(msg); // Avoids NPE
        final String braces = "{}";
        final StringBuilder builder = new StringBuilder();
        int start = 0;
        for (final Object arg : args) {
            final int bracesIndex = msg.indexOf(braces, start);
            if (bracesIndex < 0) {
                builder.append(msg.substring(start));
                builder.append(" ");
                builder.append(arg);
                start = msg.length();
            } else {
                builder.append(msg, start, bracesIndex);
                builder.append(arg);
                start = bracesIndex + braces.length();
            }
        }
        builder.append(msg.substring(start));
        return builder.toString();
    }

    private void loadProperties(final String resourceName, final Properties props) {
        final URL testProps = Loader.getResource(resourceName);
        if (testProps != null) {
            try (InputStream in = testProps.openStream()) {
                final Properties p = new Properties();
                p.load(in);
                for (final Object key : p.keySet()) {
                    final Object value = p.get(key);
                    if (value != null) {
                        props.put(key, value);
                    }
                }
            } catch (final IOException e) {
                System.err.println("[WARN] Error loading logging config: " + testProps);
                e.printStackTrace();
            }
        }
    }
}
