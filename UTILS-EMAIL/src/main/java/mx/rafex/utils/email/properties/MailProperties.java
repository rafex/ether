package mx.rafex.utils.email.properties;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

public final class MailProperties {

	private static final Logger LOGGER = Logger.getLogger(MailProperties.class.getName());

	static {
		try {
			MailProperties.loadProperties(MailProperties.JDBC_PROPERTIES, MailProperties.PROPERTIES);
		} catch (final SecurityException e) {
			LOGGER.warning(e.getMessage());
		}
	}

	public static final String JDBC_PROPERTIES = "mail.properties";
	public static Properties PROPERTIES;

	private MailProperties() {

	}

	static void loadProperties(final String resourceName, final Properties props) {
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		final URL testProps = loader.getResource(resourceName);
		if (testProps != null) {
			try (InputStream in = testProps.openStream()) {
				MailProperties.PROPERTIES = new Properties();
				MailProperties.PROPERTIES.load(in);
			} catch (final IOException e) {
				LOGGER.warning("[WARN] Error loading logging config: " + testProps);
				e.printStackTrace();
			}
		}
	}
}
