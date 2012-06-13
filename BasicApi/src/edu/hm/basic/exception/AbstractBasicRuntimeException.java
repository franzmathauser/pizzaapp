package edu.hm.basic.exception;

/**
 * Basic Excpetion fuer alle Server-Komponenten.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public abstract class AbstractBasicRuntimeException extends RuntimeException
{

	private static final long serialVersionUID = 5892768902239971268L;

	/**
	 * Standardkonstruktor.
	 */
	public AbstractBasicRuntimeException()
	{
		super();
	}

	/**
	 * Exception mit Nachricht.
	 * 
	 * @param msg
	 *            Nachricht
	 */
	public AbstractBasicRuntimeException( String msg )
	{
		super( msg );
	}

	/**
	 * Exception mit ausloesender Exception.
	 * 
	 * @param e
	 *            ausloesende Exception
	 */
	public AbstractBasicRuntimeException( Throwable e )
	{
		super( e );
	}

	/**
	 * Exception mit Nachricht und ausloesender Exception.
	 * 
	 * @param msg
	 *            Nachricht
	 * @param e
	 *            ausloesende Exception
	 */
	public AbstractBasicRuntimeException( String msg, Throwable e )
	{
		super( msg, e );
	}

}
