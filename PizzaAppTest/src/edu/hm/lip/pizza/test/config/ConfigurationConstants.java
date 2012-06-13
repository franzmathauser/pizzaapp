package edu.hm.lip.pizza.test.config;

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
	 * Hostname. Default: localhost.
	 */
	public static final String HOSTNAME = "lip.pizza.test.hostname";

	/**
	 * Port. Default: 80.
	 */
	public static final String PORT = "lip.pizza.test.port";

}
