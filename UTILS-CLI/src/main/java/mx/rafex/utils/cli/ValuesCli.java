package mx.rafex.utils.cli;

public final class ValuesCli {

	private int port = 8080;
	private String host = "0.0.0.0";
	private int maxThreads = 200;
	private int minThreads = 50;
	private int idleTimeout = 3000;

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(final int port) {
		this.port = port;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(final String host) {
		this.host = host;
	}

	/**
	 * @return the maxThreads
	 */
	public int getMaxThreads() {
		return maxThreads;
	}

	/**
	 * @param maxThreads the maxThreads to set
	 */
	public void setMaxThreads(final int maxThreads) {
		this.maxThreads = maxThreads;
	}

	/**
	 * @return the minThreads
	 */
	public int getMinThreads() {
		return minThreads;
	}

	/**
	 * @param minThreads the minThreads to set
	 */
	public void setMinThreads(final int minThreads) {
		this.minThreads = minThreads;
	}

	/**
	 * @return the idleTimeout
	 */
	public int getIdleTimeout() {
		return idleTimeout;
	}

	/**
	 * @param idleTimeout the idleTimeout to set
	 */
	public void setIdleTimeout(final int idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

}
