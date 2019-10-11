package mx.rafex.utils.jdbc.connectors.impl;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import mx.rafex.utils.jdbc.connectors.Connector;

public class ConnectorImpl implements Connector {

    private final Logger LOGGER = Logger.getLogger(ConnectorImpl.class.getName());

    private static ConnectorImpl instance;
    private Connection connection;

    private ConnectorImpl() {
    }

    public static ConnectorImpl getInstance() {
        if (instance == null) {
            synchronized (ConnectorImpl.class) {
                if (instance == null) {
                    instance = new ConnectorImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Connection get(final Driver driver, final String url, final String user, final String password) {
        try {
            DriverManager.registerDriver(driver);
        } catch (final SQLException e) {
            LOGGER.warning(e.getMessage());
        }
        getConnection(new StringBuilder(url), user, password);

        return connection;
    }

    @Override
    public Connection get(final Driver driver, final String url) {
        return get(driver, url, null, null);
    }

    @Override
    public Connection get(final String className, final String url, final String user, final String password) {
        try {
            Class.forName(className);
        } catch (final ClassNotFoundException e) {
            LOGGER.warning(e.getMessage());
        }
        getConnection(new StringBuilder(url), user, password);
        return connection;
    }

    @Override
    public Connection get(final String className, final String url) {
        return get(className, url, null, null);
    }

    @Override
    public Connection get(final Properties properties) {
        try {
            final String className = properties.getProperty("className");
            Class.forName(className);
        } catch (final ClassNotFoundException e) {
            LOGGER.warning(e.getMessage());
        }
        final StringBuilder url = new StringBuilder("jdbc:");
        url.append(properties.getProperty("url"));
        url.append(":");
        url.append(properties.getProperty("port"));
        url.append("/");
        url.append(properties.getProperty("database"));
        getConnection(url, properties);
        return connection;
    }

    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed !!");
            }
        } catch (final SQLException e) {
            LOGGER.warning(e.getMessage());
        } catch (final Exception e) {
            LOGGER.warning(e.getMessage());
        }
    }

    private void getConnection(final StringBuilder url, final String user, final String password) {
        try {
            if (user == null && password == null) {
                connection = DriverManager.getConnection(url.toString());
            } else {
                final Properties properties = new Properties();
                properties.setProperty("user", user);
                properties.setProperty("password", password);
                getConnection(url, properties);
            }
        } catch (final SQLException e) {
            LOGGER.warning(String.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage()));
            close();
        } catch (final Exception e) {
            LOGGER.warning(e.getMessage());
            close();
        }
    }

    private void getConnection(final StringBuilder url, final Properties properties) {
        try {
            connection = DriverManager.getConnection(url.toString(), properties);
        } catch (final SQLException e) {
            LOGGER.warning(String.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage()));
        }
    }

}
