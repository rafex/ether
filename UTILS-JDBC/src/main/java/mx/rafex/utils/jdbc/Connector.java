package mx.rafex.utils.jdbc;

import java.sql.Connection;
import java.sql.Driver;

public interface Connector {

    String JDBC_PROPERTIES = "jdbc.properties";

    Connection connect(String className, String url, String user, String password);

    Connection connect(String className, String url);

    Connection connect(Driver driver, String url, String user, String password);

    Connection connect(Driver driver, String url);

    Connection connect(Driver driver, String url, Class<?> clazz);

    Connection connect(String className, String url, Class<?> clazz);

    void close();
}
