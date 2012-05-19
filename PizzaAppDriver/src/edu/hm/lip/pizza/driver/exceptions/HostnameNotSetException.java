package edu.hm.lip.pizza.driver.exceptions;

/**
 * @author Stefan Wörner
 */
public class HostnameNotSetException extends RuntimeException
{

	private static final long serialVersionUID = 5536311157691373752L;

	public HostnameNotSetException()
	{

	}

	public HostnameNotSetException( String message )
	{
		super( message );
	}

}
