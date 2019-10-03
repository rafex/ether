package mx.rafex.utils.jdbc;

import java.sql.Connection;
import java.sql.Driver;

public interface Connector {

    Connection connect(final String className, final String url, final String user, final String password);

    Connection connect(final String className, final String url);

    Connection connect(final Driver driver, final String url, final String user, final String password);

    Connection connect(final Driver driver, final String url);

    void close();
}
