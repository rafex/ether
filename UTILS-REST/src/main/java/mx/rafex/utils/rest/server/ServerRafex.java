package mx.rafex.utils.rest.server;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import mx.rafex.utils.rest.servlets.ServletUtil;

public class ServerRafex {

    private final Server server;
    private final ServerConnector connector;
    private final int port;
    private final String host;
    private final long timeout;
    private final ServletContextHandler context;

    private ServerRafex(final Builder builder) {
        server = new Server();
        port = builder.port;
        host = builder.host;
        timeout = builder.timeout;

        connector = new ServerConnector(server);
        connector.setPort(port);
        connector.setHost(host);
        connector.setIdleTimeout(timeout);
        server.addConnector(connector);
        context = new ServletContextHandler();
        context.addServlet(DefaultServlet.class, "/");
        server.setHandler(context);
    }

    public void run() {
        try {
            if (server != null) {
                server.start();
                server.join();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void addServlet(final Class<? extends Servlet> servlet) {
        if (context != null) {
            context.addServlet(servlet, ServletUtil.getBasePath(servlet));
        } else {
            throw new NullPointerException("ServletContextHandler null");
        }
    }

    public static class Builder {
        private int port;
        private long timeout;
        private String host;

        public Builder() {
            port = 8080;
            timeout = 30000l;
            host = "0.0.0.0";
        }

        public Builder timeout(final long timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder host(final String host) {
            this.host = host;
            return this;
        }

        public Builder port(final int port) {
            this.port = port;
            return this;
        }

        public ServerRafex build() {
            return new ServerRafex(this);
        }
    }

}
