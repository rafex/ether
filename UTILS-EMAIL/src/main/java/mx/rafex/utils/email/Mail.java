package mx.rafex.utils.email;

public interface Mail extends Runnable {

	void from(String from);

	void to(String to);

	void subject(String subject);

	void message(String message);

	void build(String from, String to, String subject, String message);

}
