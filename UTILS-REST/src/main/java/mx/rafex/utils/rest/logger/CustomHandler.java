package mx.rafex.utils.rest.logger;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class CustomHandler extends Handler {

    @Override
    public void publish(final LogRecord record) {
        final StringBuilder buf = new StringBuilder();
        buf.append("#CH# [").append(record.getLevel().getName()).append("] ");
        String logname = record.getLoggerName();
        final int idx = logname.lastIndexOf('.');
        if (idx > 0) {
            logname = logname.substring(idx + 1);
        }
        buf.append(logname);
        buf.append(": ");
        buf.append(record.getMessage());

        System.out.println(buf.toString());
        if (record.getThrown() != null) {
            record.getThrown().printStackTrace(System.out);
        }
    }

    @Override
    public void flush() {
        /* do nothing */

    }

    @Override
    public void close() throws SecurityException {
        /* do nothing */
    }
}
