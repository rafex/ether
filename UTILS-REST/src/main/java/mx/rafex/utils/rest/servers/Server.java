package mx.rafex.utils.rest.servers;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.rafex.utils.rest.filters.CORSFilter;
import mx.rafex.utils.rest.servlets.ServletUtil;

public class Server {

    private final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private final org.eclipse.jetty.server.Server server;
    private final ServerConnector connector;
    private final int port;
    private final String host;
    private final ServletContextHandler servletContextHandler;
    private final QueuedThreadPool queuedThreadPool;
    private final int maxThreads;
    private final int minThreads;
    private final int idleTimeout;

    private Server(final Builder builder) {
        port = builder.port;
        host = builder.host;
        maxThreads = builder.maxThreads;
        minThreads = builder.minThreads;
        idleTimeout = builder.idleTimeout;
        queuedThreadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);
        server = new org.eclipse.jetty.server.Server(queuedThreadPool);

        connector = new ServerConnector(server);
        connector.setPort(port);
        connector.setHost(host);
        server.addConnector(connector);
        servletContextHandler = new ServletContextHandler();

        final FilterHolder filter = new FilterHolder(new CORSFilter());
        filter.setName("CorsFilter");

        servletContextHandler.addFilter(filter, "/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC));

        server.setHandler(servletContextHandler);

        LOGGER.info("Server construido");

    }

    public void run() {
        try {
            if (server != null) {
                server.start();
                server.join();
                LOGGER.info("Server ejecutandose");
            }
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage());
        }
    }

    public void stop() {
        try {
            if (server != null) {
                server.stop();
                LOGGER.info("Server detenido");
            }
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage());
        }
    }

    public void destroy() {
        try {
            if (server != null) {
                server.destroy();
                LOGGER.info("Server detenido");
            }
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage());
        }
    }

    public void addServlet(final Class<? extends HttpServlet> httpServlet) {
        if (servletContextHandler != null) {
            servletContextHandler.addServlet(httpServlet, ServletUtil.getBasePath(httpServlet));
            LOGGER.info("Se agrego servlet: " + httpServlet);
        } else {
            LOGGER.warn("ServletContextHandler null");
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
            idleTimeout = 3000;
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

        public Server build() {
            return new Server(this);
        }
    }

}
