package mx.rafex.utils.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

public final class Properties {

    private final static Logger LOGGER = Logger.getLogger(Properties.class.getName());

    private static Properties INSTANCE = null;
    private static String FILE_NAME = "app.properties";

    private static final java.util.Properties properties = new java.util.Properties();

    static {
        INSTANCE = new Properties();
    }

    private Properties() {
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream(FILE_NAME));
        } catch (final IOException ex) {
            ex.printStackTrace();
            LOGGER.info("Fail load properties");
        }
    }

    public static void loadProperties(final String file) {
        try {
            properties.load(new FileInputStream(file));
        } catch (final IOException ex) {
            ex.printStackTrace();
            LOGGER.info("Fail load properties");
        }
    }

    public static String getProperty(final String key) {
        return properties.getProperty(key);
    }

    public static Properties getInstance() {
        if (INSTANCE == null) {
            synchronized (Properties.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Properties();
                }
            }
        }
        return INSTANCE;
    }

}
