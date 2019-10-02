package mx.rafex.utils.rest.server;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import mx.rafex.utils.rest.servlets.CORSFilter;
import mx.rafex.utils.rest.servlets.ServletUtil;

public class ServerRafex {

    private final Server server;
    private final ServerConnector connector;
    private final int port;
    private final String host;
    private final ServletContextHandler context;
    private final QueuedThreadPool threadPool;
    private int maxThreads = 100;
    private int minThreads = 10;
    private int idleTimeout = 120;

    private ServerRafex(final Builder builder) {

        port = builder.port;
        host = builder.host;
        maxThreads = builder.maxThreads;
        minThreads = builder.minThreads;
        idleTimeout = builder.idleTimeout;
        threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);
        server = new Server(threadPool);

        connector = new ServerConnector(server);
        connector.setPort(port);
        connector.setHost(host);
        server.addConnector(connector);
        context = new ServletContextHandler();

        final FilterHolder filter = new FilterHolder(CORSFilter.class);
        filter.setName("CorsFilter");
        final CORSFilter corsFilter = new CORSFilter();
        filter.setFilter(corsFilter);

        context.addFilter(filter, "/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC));

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
        private String host;
        private int maxThreads;
        private int minThreads;
        private int idleTimeout;

        public Builder() {
            port = 8080;
            host = "0.0.0.0";
            maxThreads = 100;
            minThreads = 10;
            idleTimeout = 120;
        }

        public Builder maxThreads(final int maxThreads) {
            this.maxThreads = maxThreads;
            return this;
        }

        public Builder minThreads(final int minThreads) {
            this.minThreads = minThreads;
            return this;
        }

        public Builder timeout(final int idleTimeout) {
            this.idleTimeout = idleTimeout;
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
