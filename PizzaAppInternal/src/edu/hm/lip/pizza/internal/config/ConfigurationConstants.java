package edu.hm.lip.pizza.internal.config;

/**
 * Diese Klasse enthält die Konstanten zum Auslesen aus der Konfiguration.
 * 
 * @author Stefan Wörner
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

	public static final String MAIL_ENABLED = "lip.pizza.mail.enabled";

	public static final String MAIL_SMTP_HOST = "lip.pizza.mail.smtp.host";

	public static final String MAIL_SMTP_PORT = "lip.pizza.mail.smtp.port";

	public static final String MAIL_DEBUG_ENABLED = "lip.pizza.mail.debug.enabled";

	public static final String MAIL_AUTHENTICATION_REQUIRED = "lip.pizza.mail.auth.required";

	public static final String MAIL_AUTHENTICATION_USERNAME = "lip.pizza.mail.auth.username";

	public static final String MAIL_AUTHENTICATION_PASSWORD = "lip.pizza.mail.auth.password";

	public static final String MAIL_SENDER_ADDRESS = "lip.pizza.mail.sender.address";

	public static final String MAIL_SENDER_NAME = "lip.pizza.mail.sender.name";

}
