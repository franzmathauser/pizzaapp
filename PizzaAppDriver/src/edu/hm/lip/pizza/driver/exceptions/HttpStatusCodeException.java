package edu.hm.lip.pizza.driver.exceptions;

/**
 * Exception zeigt an, dass ein HTTP Status Code ab 400 aufwärts empfangen wurde (HTTP Fehler).
 * 
 * @author Stefan Wörner
 */
public class HttpStatusCodeException extends RuntimeException
{

	private static final long serialVersionUID = 5536311157691373752L;

	/**
	 * Konstruktor.
	 */
	public HttpStatusCodeException()
	{

	}

	/**
	 * Konstruktor.
	 * 
	 * @param message
	 *            Fehlermeldung
	 */
	public HttpStatusCodeException( String message )
	{
		super( message );
	}

}
