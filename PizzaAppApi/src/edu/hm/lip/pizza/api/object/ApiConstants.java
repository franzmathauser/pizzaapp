/**
 * 
 */
package edu.hm.lip.pizza.api.object;

/**
 * Klasse enthaelt alle Konstanten der PizzaApp API die auch fuer Clients notwendig bzw. sinnvoll sind.
 * 
 * @author Stefan Wörner
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
	public static final String VERSION = "0.001.0";

	/**
	 * Datumsformatierungs-String.
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
}
