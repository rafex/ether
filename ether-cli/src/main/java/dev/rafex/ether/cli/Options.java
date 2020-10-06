package dev.rafex.ether.cli;

/**
 *
 * Date: 05/10/2020
 *
 * @author Raul Eduardo Gonzalez Argote by @rafex
 *
 */

public enum Options {

	PORT("P", "change the microservice listening port"), PROPERTIES("p", "load file properties"), HOST("H", "change the microservice ip host"), HELP("h", "show help"),
	VERSION("v", "show version"), MAX_THREADS("T", "max Threads"), MIN_THREADS("t", "min Threads"), IDLE_TIMEOUT("i", "timeout response");

	private String argument;
	private String resumen;

	Options(final String argument, final String resumen) {
		this.argument = argument;
		this.resumen = resumen;
	}

	public String getArgument() {
		return argument;
	}

	public String getResumen() {
		return resumen;
	}

	public static Options findByType(final String argument) {
		for (final Options option : values()) {
			if (option.argument.equals(argument)) {
				return option;
			}
		}
		return null;
	}

}
