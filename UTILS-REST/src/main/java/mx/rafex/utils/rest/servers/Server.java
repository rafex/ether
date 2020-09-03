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
		port = builder.port;
		host = builder.host;
		maxThreads = builder.maxThreads;
		minThreads = builder.minThreads;
		idleTimeout = builder.idleTimeout;
		queuedThreadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);
		server = new org.eclipse.jetty.server.Server(queuedThreadPool);

		LOGGER.info("Host: " + host);
		LOGGER.info("Puerto: " + port);
		LOGGER.info("Tiempo muerto: " + idleTimeout);
		LOGGER.info("Minimo de hilos: " + minThreads);
		LOGGER.info("Maximo de hilos: " + maxThreads);

		connector = new ServerConnector(server);
		connector.setPort(port);
		connector.setHost(host);
		server.addConnector(connector);
		servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

		final FilterHolder filter = new FilterHolder(new CORSFilter());
		filter.setName("CorsFilter");

		servletContextHandler.addFilter(filter, "/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST,
				DispatcherType.FORWARD, DispatcherType.ASYNC));

		server.setHandler(servletContextHandler);

		LOGGER.info("Server construido");

	}

	public void run() throws Exception {
		try {
			if (server != null) {
				LOGGER.info("Server ejecutandose");
				server.start();
				server.join();
			}
		} catch (final InterruptedException e) {
			LOGGER.info("Error al unirse al server");
			LOGGER.warning(e.getMessage());
		} catch (final Exception e) {
			LOGGER.warning(e.getMessage());
		}
	}

	public void stop() {
		try {
			if (server != null) {
				server.stop();
				LOGGER.info("Server detenido");
			}
		} catch (final Exception e) {
			LOGGER.warning(e.getMessage());
		}
	}

	public void destroy() {
		try {
			if (server != null) {
				server.destroy();
				LOGGER.info("Server detenido");
			}
		} catch (final Exception e) {
			LOGGER.warning(e.getMessage());
		}
	}

	public void addServlet(final Class<? extends HttpServlet> httpServlet) {
		if (servletContextHandler != null) {
			servletContextHandler.addServlet(httpServlet, UtilServlet.getBasePath(httpServlet));
			LOGGER.info("Se agrego servlet: [" + httpServlet + "] con la ruta: [" + UtilServlet.getBasePath(httpServlet)
					+ "]");
		} else {
			LOGGER.warning("ServletContextHandler null");
			throw new NullPointerException("ServletContextHandler null");
		}
	}

	public static class Builder {
		private int port;
		private String host;
		private int maxThreads;
		private int minThreads;
		private int idleTimeout;
		private static Server instance;

		public Builder() {
			port = 8080;
			host = "0.0.0.0";
			maxThreads = 500;
			minThreads = 100;
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
			if (instance == null) {
				synchronized (Server.class) {
					if (instance == null) {
						instance = new Server(this);
					}
				}
			}
			return instance;
		}
	}

}
