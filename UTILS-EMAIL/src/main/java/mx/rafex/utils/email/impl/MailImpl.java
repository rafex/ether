package mx.rafex.utils.email.impl;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import mx.rafex.utils.email.Mail;

public class MailImpl implements Mail {

	private final Logger LOGGER = Logger.getLogger(MailImpl.class.getName());

	private final String host;
	private final Integer port;
	private final String userName;
	private final String password;
	private final Properties properties;
	private String message;
	private String subject;
	private String from;
	private String to;

	private MailImpl(final Builder builder) {

		properties = builder.properties;

		if (properties != null) {
			host = properties.getProperty("host");
			port = Integer.valueOf(properties.getProperty("port"));
			userName = properties.getProperty("user");
			password = properties.getProperty("password");
		} else {
			host = builder.host;
			port = builder.port;
			userName = builder.userName;
			password = builder.password;
		}

	}

	@Override
	public void from(final String from) {
		this.from = from;
	}

	@Override
	public void to(final String to) {
		this.to = to;
	}

	@Override
	public void subject(final String subject) {
		this.subject = subject;
	}

	@Override
	public void message(final String message) {
		this.message = message;
	}

	@Override
	public void build(final String from, final String to, final String subject, final String message) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.message = message;

	}

	private void valid(final String from, final String to, final String subject, final String message) {
		if (from == null) {
			throw new NullPointerException("From is null");
		}

		if (to == null) {
			throw new NullPointerException("To is null");
		}

		if (subject == null) {
			throw new NullPointerException("Subject is null");
		}

		if (message == null) {
			throw new NullPointerException("Message is null");
		}
	}

	public static class Builder {

		private String host;
		private Integer port;
		private String userName;
		private String password;
		private Properties properties;
		private final static String ENVIRONMENT_MAIL_PASSWORD = "JAVA_ENVIRONMENT_MAIL_PASSWORD";
		private final static String ENVIRONMENT_MAIL_USER = "JAVA_ENVIRONMENT_MAIL_USER";

		private static MailImpl instance;

		public Builder() {
			if (System.getenv(ENVIRONMENT_MAIL_USER) != null) {
				userName = System.getenv(ENVIRONMENT_MAIL_USER);
			}

			if (System.getenv(ENVIRONMENT_MAIL_PASSWORD) != null) {
				password = System.getenv(ENVIRONMENT_MAIL_PASSWORD);
			}
		}

		public Builder loadProperties(final Properties properties) {
			this.properties = properties;

			if (System.getenv(ENVIRONMENT_MAIL_USER) != null) {
				this.properties.setProperty("user", System.getenv(ENVIRONMENT_MAIL_USER));
			}

			if (System.getenv(ENVIRONMENT_MAIL_PASSWORD) != null) {
				this.properties.setProperty("password", System.getenv(ENVIRONMENT_MAIL_PASSWORD));
			}
			return this;
		}

		public Builder host(final String host) {
			this.host = host;
			return this;
		}

		public Builder port(final Integer port) {
			this.port = port;
			return this;
		}

		public Builder userName(final String userName) {
			this.userName = userName;
			if (System.getenv(ENVIRONMENT_MAIL_USER) != null) {
				this.userName = System.getenv(ENVIRONMENT_MAIL_USER);
			}
			return this;
		}

		public Builder password(final String password) {
			this.password = password;
			if (System.getenv(ENVIRONMENT_MAIL_PASSWORD) != null) {
				this.password = System.getenv(ENVIRONMENT_MAIL_PASSWORD);
			}
			return this;
		}

		public MailImpl build() {
			if (instance == null) {
				synchronized (MailImpl.class) {
					if (instance == null) {
						instance = new MailImpl(this);
					}
				}
			}
			return instance;
		}

	}

	@Override
	public void run() {
		valid(from, to, subject, message);

		final Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.port", port);
		prop.put("mail.smtp.ssl.trust", host);

		final Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});

		try {

			final Message messageMail = new MimeMessage(session);
			messageMail.setFrom(new InternetAddress(from));
			messageMail.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			messageMail.setSubject(subject);

			final MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(message, "text/html");

			final Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);

			messageMail.setContent(multipart);

			Transport.send(messageMail);

		} catch (final Exception e) {
			LOGGER.warning(e.getMessage());
		}

	}

}
