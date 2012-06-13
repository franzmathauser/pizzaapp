/**
 * 
 */
package edu.hm.lip.pizza.api.object;

/**
 * Klasse enthaelt alle Konstanten der PizzaApp API die auch fuer Clients notwendig bzw. sinnvoll sind.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public final class ApiConstants
{

	/**
	 * Privater Konstructor.
	 */
	private ApiConstants()
	{

	}

	/**
	 * Konstante mit der Versionsnummer der Server-Anteile.
	 */
	public static final String VERSION = "1.000.0";

	/**
	 * Datumsformatierungs-String.
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	/**
	 * BASE64 Encodierte Benutzer, Passwort Credentials. Erlaubt den Zugriff auf die ActiveMQ via HTTP
	 */
	public static final String ACTIVEMQ_AUTHORIZATION_CREDENTIALS_BASE64 = "YWRtaW46bGlwLnBpenphYXBwLmFjdGl2ZW1xYWRtaW4=";

	/**
	 * Längengrad-Koordinate des Pizza-Shops.
	 */
	public static final Double PIZZA_STORE_ORIGIN_LAT = 48.0972;

	/**
	 * Breitengrad-Koordinate des Pizza-Shops.
	 */
	public static final Double PIZZA_STORE_ORIGIN_LON = 11.5265;

	/**
	 * Produktionsserver URL.
	 */
	public static final String PRODUCTION_SERVER_URL = "23.23.183.8";
}
