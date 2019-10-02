package mx.rafex.utils.rest.server;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;

import mx.rafex.utils.rest.servlets.ServletUtil;

public class ServerRafex {

    private final Server server;
    private ServerConnector connector;
    private final int port;
    private final String host;
    private final long timeout;
    private ServletContextHandler context;

    private ServerRafex(final ServerBuilder builder) {
        server = new Server();

        port = builder.port;
        host = builder.host;
        timeout = builder.timeout;
    }

    public void run() {
        connector = new ServerConnector(server);
        connector.setPort(port);
        connector.setHost(host);
        connector.setIdleTimeout(timeout);
        server.addConnector(connector);

        final URL webRootLocation = this.getClass().getResource("/webroot/index.html");
        if (webRootLocation == null) {
            throw new IllegalStateException("Unable to determine webroot URL location");
        }

        URI webRootUri = null;
        try {
            webRootUri = URI.create(webRootLocation.toURI().toASCIIString().replaceFirst("/index.html$", "/"));
        } catch (final URISyntaxException e) {
            e.printStackTrace();
        }

        System.out.printf("Web Root URI: %s%n", webRootUri);

        context = new ServletContextHandler();
        context.setContextPath("/");
        try {
            context.setBaseResource(Resource.newResource(webRootUri));
        } catch (final MalformedURLException e) {
            e.printStackTrace();
        }
        context.setWelcomeFiles(new String[] { "index.html" });
        context.getMimeTypes().addMimeMapping("txt", "text/plain;charset=utf-8");
        server.setHandler(context);

        context.addServlet(DefaultServlet.class, "/");

    }

    public void start() {
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
        context.addServlet(servlet, ServletUtil.getBasePath(servlet));
    }

    public final class ServerBuilder {
        private int port;
        private long timeout;
        private String host;

        public ServerBuilder() {
            port = 8080;
            timeout = 30000l;
            host = "0.0.0.0";
        }

        public ServerBuilder timeout(final long timeout) {
            this.timeout = timeout;
            return this;
        }

        public ServerBuilder host(final String host) {
            this.host = host;
            return this;
        }

        public ServerBuilder port(final int port) {
            this.port = port;
            return this;
        }

        public ServerRafex build() {
            return new ServerRafex(this);
        }
    }

}
