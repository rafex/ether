package mx.rafex.utils.email;

import java.util.Properties;

public interface Mail {

	void from(String from);

	void to(String to);

	void subject(String subject);

	void message(String message);

	void send(String from, String to, String subject, String message);

	void send(Properties properties);

	void send();

}
