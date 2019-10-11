package mx.rafex.utils.jdbc.properties;

import java.io.InputStream;
import java.util.logging.Logger;

public final class LoaderProperties {

    private static final Logger LOGGER = Logger.getLogger(LoaderProperties.class.getName());

    static {
        try {
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();
            final InputStream inputStream = loader.getResourceAsStream(LoaderProperties.DATA_BASE_PROPERTIES);
        } catch (final SecurityException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    private static final String DATA_BASE_PROPERTIES = "database.properties";

}
