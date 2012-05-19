package edu.hm.lip.pizza.driver.exceptions;

/**
 * @author Stefan Wörner
 */
public class HttpStatusCodeException extends RuntimeException
{

	private static final long serialVersionUID = 5536311157691373752L;

	public HttpStatusCodeException()
	{

	}

	public HttpStatusCodeException( String message )
	{
		super( message );
	}

}
