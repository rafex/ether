package mx.rafex.utils.jdbc.impl;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

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
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (final SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            close();
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            close();
        }
    }

}
