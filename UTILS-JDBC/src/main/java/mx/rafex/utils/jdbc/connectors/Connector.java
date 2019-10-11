package mx.rafex.utils.jdbc.connectors;

import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;

public interface Connector {

    Connection get(String className, String url, String user, String password);

    Connection get(String className, String url);

    Connection get(Driver driver, String url, String user, String password);

    Connection get(Driver driver, String url);

    Connection get(Properties properties);

    void close();
}
