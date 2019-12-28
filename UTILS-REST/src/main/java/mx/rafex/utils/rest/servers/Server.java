package mx.rafex.utils.rest.servers;

import java.util.EnumSet;
import java.util.logging.Logger;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import mx.rafex.utils.rest.filters.CORSFilter;
import mx.rafex.utils.rest.servlets.UtilServlet;

public class Server {

    private final Logger LOGGER = Logger.getLogger(Server.class.getName());

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
        try {
            Log.setLog(new mx.rafex.utils.rest.logger.Logger(java.util.logging.Logger.GLOBAL_LOGGER_NAME));
        } catch (final Exception e) {
            e.printStackTrace();
        }
        this.port = builder.port;
        this.host = builder.host;
        this.maxThreads = builder.maxThreads;
        this.minThreads = builder.minThreads;
        this.idleTimeout = builder.idleTimeout;
        this.queuedThreadPool = new QueuedThreadPool(this.maxThreads, this.minThreads, this.idleTimeout);
        this.server = new org.eclipse.jetty.server.Server(this.queuedThreadPool);

        this.LOGGER.info("Host: " + this.host);
        this.LOGGER.info("Puerto: " + this.port);
        this.LOGGER.info("Tiempo muerto: " + this.idleTimeout);
        this.LOGGER.info("Minimo de hilos: " + this.minThreads);
        this.LOGGER.info("Maximo de hilos: " + this.maxThreads);

        this.connector = new ServerConnector(this.server);
        this.connector.setPort(this.port);
        this.connector.setHost(this.host);
        this.server.addConnector(this.connector);
        this.servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

        final FilterHolder filter = new FilterHolder(new CORSFilter());
        filter.setName("CorsFilter");

        this.servletContextHandler.addFilter(filter, "/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST,
                DispatcherType.FORWARD, DispatcherType.ASYNC));

        this.server.setHandler(this.servletContextHandler);

        this.LOGGER.info("Server construido");

    }

    public void run() throws Exception {
        try {
            if (this.server != null) {
                this.LOGGER.info("Server ejecutandose");
                this.server.start();
                this.server.join();
            }
        } catch (final InterruptedException e) {
            this.LOGGER.info("Error al unirse al server");
            this.LOGGER.warning(e.getMessage());
        } catch (final Exception e) {
            this.LOGGER.warning(e.getMessage());
        }
    }

    public void stop() {
        try {
            if (this.server != null) {
                this.server.stop();
                this.LOGGER.info("Server detenido");
            }
        } catch (final Exception e) {
            this.LOGGER.warning(e.getMessage());
        }
    }

    public void destroy() {
        try {
            if (this.server != null) {
                this.server.destroy();
                this.LOGGER.info("Server detenido");
            }
        } catch (final Exception e) {
            this.LOGGER.warning(e.getMessage());
        }
    }

    public void addServlet(final Class<? extends HttpServlet> httpServlet) {
        if (this.servletContextHandler != null) {
            this.servletContextHandler.addServlet(httpServlet, UtilServlet.getBasePath(httpServlet));
            this.LOGGER.info("Se agrego servlet: [" + httpServlet + "] con la ruta: ["
                    + UtilServlet.getBasePath(httpServlet) + "]");
        } else {
            this.LOGGER.warning("ServletContextHandler null");
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
            this.port = 8080;
            this.host = "0.0.0.0";
            this.maxThreads = 100;
            this.minThreads = 10;
            this.idleTimeout = 3000;
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
