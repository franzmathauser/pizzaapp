package edu.hm.lip.pizza.driver.exception;

/**
 * Exception zeigt an, dass ein HTTP Status Code ab 400 aufwärts empfangen wurde (HTTP Fehler).
 * 
 * @author Stefan Wörner
 */
public class HttpStatusCodeException extends RuntimeException
{

	private static final long serialVersionUID = 5536311157691373752L;

	private int m_statusCode;

	private String m_reasonPhrase;

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

	/**
	 * Konstruktor.
	 * 
	 * @param message
	 *            Fehlermeldung
	 * @param statusCode
	 *            HTTP Status Code
	 * @param reasonPhrase
	 *            Beschreibung zum HTTP Status Code
	 */
	public HttpStatusCodeException( String message, int statusCode, String reasonPhrase )
	{
		super( message );
		m_statusCode = statusCode;
		m_reasonPhrase = reasonPhrase;
	}

	/**
	 * Liefert das Attribut statusCode.
	 * 
	 * @return statusCode
	 */
	public int getStatusCode()
	{
		return m_statusCode;
	}

	/**
	 * Setzt das Attribut statusCode.
	 * 
	 * @param statusCode
	 *            zu setzender Wert für das Attribut statusCode
	 */
	public void setStatusCode( int statusCode )
	{
		m_statusCode = statusCode;
	}

	/**
	 * Liefert das Attribut reasonPhrase.
	 * 
	 * @return reasonPhrase
	 */
	public String getReasonPhrase()
	{
		return m_reasonPhrase;
	}

	/**
	 * Setzt das Attribut reasonPhrase.
	 * 
	 * @param reasonPhrase
	 *            zu setzender Wert für das Attribut reasonPhrase
	 */
	public void setReasonPhrase( String reasonPhrase )
	{
		m_reasonPhrase = reasonPhrase;
	}

}
