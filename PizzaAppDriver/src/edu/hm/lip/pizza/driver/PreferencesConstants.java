package edu.hm.lip.pizza.driver;

/**
 * KLasse enthält alle applikationsweit verwendeten Konstanten.
 * 
 * @author Stefan Wörner
 */
public final class PreferencesConstants
{

	/**
	 * Privater Kontructor.
	 */
	private PreferencesConstants()
	{

	}

	/**
	 * Dateiname des Preferences File der Applikation.
	 */
	public static final String FILENAME = "PizzaAppDriver_PrefFile";

	/**
	 * Trenner für die Fahrer Attribute bei der Serialisierung der Preferences.
	 */
	public static final String DRIVER_ATTRIBUTE_SEPERATOR = "|";

	/**
	 * Trenner für die Fahrer bei der Serialisierung der Preferences.
	 */
	public static final String DRIVER_SEPERATOR = "~";

}
