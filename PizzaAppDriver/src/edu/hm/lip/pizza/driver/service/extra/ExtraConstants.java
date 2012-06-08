package edu.hm.lip.pizza.driver.service.extra;

/**
 * Konstenten für Intent, Broadcast und Service Extras.
 * 
 * @author Stefan Wörner
 */
public final class ExtraConstants
{

	/**
	 * Privater Konstruktor.
	 */
	private ExtraConstants()
	{

	}

	/**
	 * Extra-Konstante für das Successful-Flag.
	 */
	public static final String SUCCESSFUL_EXTRA = "successful";

	/**
	 * Extra-Konstante für das Refresh-Flag.
	 */
	public static final String REFRESH_EXTRA = "refresh";

	/**
	 * Extra-Konstante für Fehlermeldungen.
	 */
	public static final String ERROR_MSG_EXTRA = "error_message";

	/**
	 * Extra-Konstante für die Latitude der aktuellen Position.
	 */
	public static final String LATITUDE_EXTRA = "lat";

	/**
	 * Extra-Konstante für die Longitude der aktuellen Position.
	 */
	public static final String LONGITUDE_EXTRA = "lon";

}
