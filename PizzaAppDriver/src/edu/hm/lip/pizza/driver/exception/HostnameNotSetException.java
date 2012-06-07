package edu.hm.lip.pizza.driver.exception;

/**
 * Exception zeigt an, dass der Hostname für die Serververbindung nicht gesetzt ist.
 * 
 * @author Stefan Wörner
 */
public class HostnameNotSetException extends RuntimeException
{

	private static final long serialVersionUID = 5536311157691373752L;

	/**
	 * Konstruktor.
	 */
	public HostnameNotSetException()
	{

	}

	/**
	 * Konstruktor.
	 * 
	 * @param message
	 *            Fehlermeldung
	 */
	public HostnameNotSetException( String message )
	{
		super( message );
	}

}
