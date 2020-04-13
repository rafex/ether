package mx.rafex.utils.cli;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.apache.commons.lang3.math.NumberUtils;

import mx.rafex.utils.properties.Properties;


public final class CliDefault implements ICliDefault {

    private final Logger LOGGER = Logger.getLogger(CliDefault.class.getName());

    private static CliDefault instance;

    protected String[] args = null;
    protected final Options options = new Options();
    protected final CommandLineParser parser = new DefaultParser();
    protected final Map<String, Object> optionsMap = new HashMap<>();

    private CliDefault(final String[] args) {
        this.args = args;
        options.addOption(OPTION_LOWERCASE_H, "help", false, "show help.");
        options.addOption(OPTION_LOWERCASE_V, "version", false, "shows the version.");
        options.addOption(OPTION_LOWERCASE_P, "properties", true, "select properties.");
        options.addOption(OPTION_UPPERCASE_H, "host", true, "IP host.");
        options.addOption(OPTION_UPPERCASE_P, "port", true, "port.");
        arguments(this.args);
    }

    public static CliDefault getInstance(final String[] args) {
        if (instance == null) {
            synchronized (CliDefault.class) {
                if (instance == null) {
                    instance = new CliDefault(args);
                }
            }
        }
        return instance;
    }

    @Override
    public ValuesCli parse() {
        CommandLine cmd = null;
        try {
            final ValuesCli valuesCli = new ValuesCli();
            cmd = parser.parse(options, args);

            if (cmd.hasOption(OPTION_LOWERCASE_H)) {
                help();
            }

            if (cmd.hasOption(OPTION_LOWERCASE_V)) {
                System.out.println("Version: " + Properties.getProperty("app.version"));
                help();
            }

            if (cmd.hasOption(OPTION_LOWERCASE_P)) {
                Properties.loadProperties(cmd.getOptionValue("p"));
            }

            if (cmd.hasOption(OPTION_UPPERCASE_P)) {
                valuesCli.setPort(NumberUtils.toInt(cmd.getOptionValue(OPTION_UPPERCASE_P, "8080")));
            }

            if (cmd.hasOption(OPTION_UPPERCASE_H)) {
                valuesCli.setHost(cmd.getOptionValue(OPTION_UPPERCASE_H, "0.0.0.0"));
            }

            return valuesCli;
        } catch (final UnrecognizedOptionException ex) {
            LOGGER.severe("Failed ");
        } catch (final ParseException ex) {
            LOGGER.info("Failed to parse comand line properties");
            help();
        }
        return null;
    }

    protected void help() {
        final HelpFormatter formater = new HelpFormatter();
        formater.printHelp("Main", options);
        System.exit(0);
    }

    private void arguments(final String[] args) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                LOGGER.info("Argument: [" + i + "][" + args[i] + "]");
            }
        }
    }

}
