package edu.hm.basic.logging;

import org.apache.log4j.Logger;

/**
 * Statische Klasse um fuer die Server Komponenten die Logging Funktionalitaet zur Verfuegung zu stellen.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public final class BasicLogger
{

	/**
	 * Name des {@link Logger} fuer Trace-Logging.
	 */
	public static final String LOGGER_TRACE = "Logger.trace";

	/**
	 * Name des {@link Logger} fuer Debug-Logging.
	 */
	public static final String LOGGER_DEBUG = "Logger.debug";

	/**
	 * Name des {@link Logger} fuer Info-Logging.
	 */
	public static final String LOGGER_INFO = "Logger.info";

	/**
	 * Name des {@link Logger} fuer Warn-Logging.
	 */
	public static final String LOGGER_WARN = "Logger.warn";

	/**
	 * Name des {@link Logger} fuer Error-Logging.
	 */
	public static final String LOGGER_ERROR = "Logger.error";

	/**
	 * Name des {@link Logger} fuer Fatal-Logging.
	 */
	public static final String LOGGER_FATAL = "Logger.fatal";

	/**
	 * System property um die Konfigurationsdatei zu finden.
	 */
	public static final String LOG4J_CONFIG_PROPERTY = "log4j.configuration";

	/**
	 * Default Konfigurationsdatei fuer das Logging.
	 */
	public static final String LOG4J_XML_CONFIG = "log4j.xml";

	/**
	 * Initalisiert das Logging mit der uebergebenen Konfigurationsdatei.
	 * 
	 * @param config
	 *            Datei mit der Konfiguration
	 */
	public static void init( String config )
	{
		System.setProperty( LOG4J_CONFIG_PROPERTY, config );
	}

	/**
	 * Statischer Initialiser.
	 */
	static
	{
		init( LOG4J_XML_CONFIG );
	}

	/**
	 * Privater Constructor, um Instanzen dieser Klasse zu verhindern.
	 */
	private BasicLogger()
	{

	}

	private static Logger getLogger( Object object, String ifNull )
	{
		if (object != null)
		{
			if (object instanceof Class<?>)
			{
				return Logger.getLogger( (Class<?>) object );
			}

			Class<?> aClass = object.getClass();
			return Logger.getLogger( aClass );
		}
		else
		{
			return Logger.getLogger( new LoggerClassGetter().getCallingClass( ifNull ) );
		}
	}

	/**
	 * Legt einen Info-Logeintrag an.
	 * 
	 * @param object
	 *            Aufrufer
	 * @param text
	 *            Nachricht
	 */
	public static void logInfo( Object object, String text )
	{
		Logger log = getLogger( object, LOGGER_INFO );
		log.info( text );
	}

	/**
	 * Legt einen Info-Logeintrag an.
	 * 
	 * @param object
	 *            Aufrufer
	 * @param throwable
	 *            Fehler
	 */
	public static void logInfo( Object object, Throwable throwable )
	{
		Logger log = getLogger( object, LOGGER_INFO );
		log.info( throwable, throwable );
	}

	/**
	 * Legt einen Warn-Logeintrag an.
	 * 
	 * @param object
	 *            Aufrufer
	 * @param text
	 *            Nachricht
	 */
	public static void logWarn( Object object, String text )
	{
		Logger log = getLogger( object, LOGGER_WARN );
		log.warn( text );
	}

	/**
	 * Legt einen Warn-Logeintrag an.
	 * 
	 * @param object
	 *            Aufrufer
	 * @param throwable
	 *            Fehler
	 */
	public static void logWarn( Object object, Throwable throwable )
	{
		Logger log = getLogger( object, LOGGER_WARN );
		log.warn( throwable, throwable );
	}

	/**
	 * Legt einen Debug-Logeintrag an.
	 * 
	 * @param object
	 *            Aufrufer
	 * @param text
	 *            Nachricht
	 */
	public static void logDebug( Object object, String text )
	{
		Logger log = getLogger( object, LOGGER_DEBUG );
		log.debug( text );
	}

	/**
	 * Legt einen Debug-Logeintrag an.
	 * 
	 * @param object
	 *            Aufrufer
	 * @param throwable
	 *            Fehler
	 */
	public static void logDebug( Object object, Throwable throwable )
	{
		Logger log = getLogger( object, LOGGER_DEBUG );
		log.debug( throwable, throwable );
	}

	/**
	 * Legt einen Error-Logeintrag an.
	 * 
	 * @param object
	 *            Aufrufer
	 * @param text
	 *            Nachricht
	 */
	public static void logError( Object object, String text )
	{
		Logger log = getLogger( object, LOGGER_ERROR );
		log.error( text );
	}

	/**
	 * Legt einen Error-Logeintrag an.
	 * 
	 * @param object
	 *            Aufrufer
	 * @param throwable
	 *            Fehler
	 */
	public static void logError( Object object, Throwable throwable )
	{
		Logger log = getLogger( object, LOGGER_ERROR );
		log.error( throwable, throwable );
	}

	/**
	 * Legt einen Fatal Error-Logeintrag an.
	 * 
	 * @param object
	 *            Aufrufer
	 * @param text
	 *            Nachricht
	 */
	public static void logFatal( Object object, String text )
	{
		Logger log = getLogger( object, LOGGER_FATAL );
		log.fatal( text );
	}

	/**
	 * Legt einen Fatal Error-Logeintrag an.
	 * 
	 * @param object
	 *            Aufrufer
	 * @param throwable
	 *            Fehler
	 */
	public static void logFatal( Object object, Throwable throwable )
	{
		Logger log = getLogger( object, LOGGER_FATAL );
		log.fatal( throwable, throwable );
	}

	/**
	 * Legt einen Trace-Logeintrag an.
	 * 
	 * @param object
	 *            Aufrufer
	 * @param text
	 *            Nachricht
	 */
	public static void logTrace( Object object, String text )
	{
		Logger log = getLogger( object, LOGGER_TRACE );
		log.trace( text );
	}

	/**
	 * Hilfsklasse fuer den Logger um die aufrufende Klasse zu erfragen.
	 */
	private static class LoggerClassGetter extends SecurityManager
	{

		/**
		 * Gibt die dritte Klasse im ClassContext der aufrufenden Methode zurueck. Dies ist im Allgemeinen die Klasse,
		 * welche den Logger aufgerufen hat.
		 * 
		 * @param ifNull
		 *            Rueckgabewert im Fehlerfall.
		 * @return Vollstaendiger Pfad der Klasse.
		 */
		public String getCallingClass( String ifNull )
		{
			try
			{
				return getClassContext()[3].getName();
			}
			catch (Exception e)
			{
				return ifNull;
			}
		}
	}

}
