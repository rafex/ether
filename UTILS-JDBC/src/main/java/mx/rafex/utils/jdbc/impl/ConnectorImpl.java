package mx.rafex.utils.jdbc.impl;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import mx.rafex.utils.jdbc.Connector;

public class ConnectorImpl implements Connector {

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
    public Connection connect(final Driver driver, final String url, final Class<?> clazz) {
        try {
            DriverManager.registerDriver(driver);
        } catch (final SQLException e) {
            System.err.println(e.getMessage());
        }

        getConnection(clazz, url);
        return connection;
    }

    @Override
    public Connection connect(final String className, final String url, final Class<?> clazz) {
        try {
            Class.forName(className);
        } catch (final ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        getConnection(clazz, url);
        return connection;
    }

    @Override
    public Connection connect(final Driver driver, final String url, final String user, final String password) {
        try {
            DriverManager.registerDriver(driver);
        } catch (final SQLException e) {
            System.err.println(e.getMessage());
        }
        getConnection(url, user, password);

        return connection;
    }

    @Override
    public Connection connect(final Driver driver, final String url) {
        return connect(driver, url, null, null);
    }

    @Override
    public Connection connect(final String className, final String url, final String user, final String password) {
        try {
            Class.forName(className);
        } catch (final ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        getConnection(url, user, password);
        return connection;
    }

    @Override
    public Connection connect(final String className, final String url) {
        return connect(className, url, null, null);
    }

    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed !!");
            }
        } catch (final SQLException e) {
            System.err.println(e.getMessage());
        } catch (final Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void getConnection(final String url, final String user, final String password) {
        try {
            if (user == null && password == null) {
                connection = DriverManager.getConnection(url);
            } else {
                final Properties properties = new Properties();
                properties.setProperty("user", user);
                properties.setProperty("password", password);
                getConnection(url, properties);
            }
        } catch (final SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            close();
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            close();
        }
    }

    private void getConnection(final String url, final Properties properties) throws SQLException {
        connection = DriverManager.getConnection(url, properties);
    }

    private void getConnection(final Class<?> clazz, final String url) {
        final Properties properties = new Properties();
        try {
            properties.load(clazz.getResourceAsStream(JDBC_PROPERTIES));
            getConnection(url, properties);
        } catch (final SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            close();
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            close();
        }
    }

}
