package mx.rafex.utils.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

    private static Connector instance;
    private Connection connection;

    private Connector() {
    }

    public static Connector getInstance() {
        if (instance == null) {
            synchronized (Connector.class) {
                if (instance == null) {
                    instance = new Connector();
                }
            }
        }
        return instance;
    }

    public Connection connect(final String className, final String url, final String user, final String password) {
        if (className != null) {
            try {
                Class.forName(className);
            } catch (final ClassNotFoundException e) {
                System.err.println("JDBC Driver not found !!");
                return null;
            }
        }

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

        return connection;
    }

    public Connection connect(final String className, final String url) {
        return connect(className, url, null, null);
    }

    public Connection connect(final String url) {
        return connect(null, url, null, null);
    }

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
}
