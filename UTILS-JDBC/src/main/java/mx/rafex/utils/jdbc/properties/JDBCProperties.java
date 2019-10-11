package mx.rafex.utils.jdbc.properties;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

public final class JDBCProperties {

    private static final Logger LOGGER = Logger.getLogger(JDBCProperties.class.getName());

    static {
        try {
            JDBCProperties.loadProperties(JDBCProperties.JDBC_PROPERTIES, JDBCProperties.PROPERTIES);
        } catch (final SecurityException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    public static final String JDBC_PROPERTIES = "jdbc.properties";
    public static Properties PROPERTIES;

    private JDBCProperties() {

    }

    static void loadProperties(final String resourceName, final Properties props) {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final URL testProps = loader.getResource(resourceName);
        if (testProps != null) {
            try (InputStream in = testProps.openStream()) {
                JDBCProperties.PROPERTIES = new Properties();
                JDBCProperties.PROPERTIES.load(in);
            } catch (final IOException e) {
                LOGGER.warning("[WARN] Error loading logging config: " + testProps);
                e.printStackTrace();
            }
        }
    }
}
