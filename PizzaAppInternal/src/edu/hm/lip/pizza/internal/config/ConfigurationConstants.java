package edu.hm.lip.pizza.internal.config;

/**
 * Diese Klasse enthält die Konstanten zum Auslesen aus der Konfiguration.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public final class ConfigurationConstants
{

	private ConfigurationConstants()
	{

	}

	/**
	 * ConfigFile Name.
	 */
	public static final String CONFIG_FILE_NAME = "pizza_app_config.xml";

	/**
	 * PropertiesFile Name.
	 */
	public static final String PROPERTIES_FILE_NAME = "pizza_app_user.properties";

	/**
	 * Konfigurations-Konstante für das Mailing-Aktiv-Flag.
	 */
	public static final String MAIL_ENABLED = "lip.pizza.mail.enabled";

	/**
	 * Konfigurations-Konstante für den SMTP Hostname.
	 */
	public static final String MAIL_SMTP_HOST = "lip.pizza.mail.smtp.host";

	/**
	 * Konfigurations-Konstante für den SMTP Port.
	 */
	public static final String MAIL_SMTP_PORT = "lip.pizza.mail.smtp.port";

	/**
	 * Konfigurations-Konstante für das Debugging-Aktiv-Flag.
	 */
	public static final String MAIL_DEBUG_ENABLED = "lip.pizza.mail.debug.enabled";

	/**
	 * Konfigurations-Konstante für das Authentifizierung-Benötigt-Flag.
	 */
	public static final String MAIL_AUTHENTICATION_REQUIRED = "lip.pizza.mail.auth.required";

	/**
	 * Konfigurations-Konstante für den Benutzernamen der Authentifizierung.
	 */
	public static final String MAIL_AUTHENTICATION_USERNAME = "lip.pizza.mail.auth.username";

	/**
	 * Konfigurations-Konstante für das Passwort der Authentifizierung.
	 */
	public static final String MAIL_AUTHENTICATION_PASSWORD = "lip.pizza.mail.auth.password";

	/**
	 * Konfigurations-Konstante für SMTP TLS-Aktiv-Flag.
	 */
	public static final String MAIL_SMTP_STARTTLS_ENABLE = "lip.pizza.mail.smtp.starttls.enabled";

	/**
	 * Konfigurations-Konstante für die Absenderadresse.
	 */
	public static final String MAIL_SENDER_ADDRESS = "lip.pizza.mail.sender.address";

	/**
	 * Konfigurations-Konstante für den Namen des Absenders.
	 */
	public static final String MAIL_SENDER_NAME = "lip.pizza.mail.sender.name";

}
