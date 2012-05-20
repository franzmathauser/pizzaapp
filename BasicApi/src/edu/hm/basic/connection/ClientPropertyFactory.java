package edu.hm.basic.connection;

import java.util.Properties;

import javax.naming.Context;

import org.apache.commons.lang.StringUtils;

import edu.hm.basic.object.BasicConstants;

/**
 * Klasse stellt Methoden zur Verfügung mit denen Clients die Properties für Lookups auf dem Server erzeugen lassen
 * können.
 * 
 * @author Stefan Wörner
 */
public abstract class ClientPropertyFactory extends AbstractConnection
{

	/**
	 * Konstruktor.
	 */
	protected ClientPropertyFactory()
	{

	}

	/**
	 * Methode erzeugt anhand der übergebenen Parameter die für einen Lookup benötigten Properties und gibt diese
	 * zurück.
	 * 
	 * @param host
	 *            Hostname unter dem der Server erreichbar ist
	 * @param port
	 *            Port über den der Lookup laufen soll
	 * @return Properties zum erzeugen eines Context, z.B. über InitialContext. Tritt ein Fehler auf, so wird NULL
	 *         zurückgegeben.
	 */
	public static Properties getClientProperties( String host, String port )
	{
		return getClientProperties( host, port, null, null, ConnectionType.JNDI, false );
	}

	/**
	 * Methode erzeugt anhand der übergebenen Parameter die für einen Lookup benötigten Properties und gibt diese
	 * zurück.
	 * 
	 * @param host
	 *            Hostname unter dem der Server erreichbar ist
	 * @param port
	 *            Port über den der Lookup laufen soll
	 * @param connectionType
	 *            Zu verwendender Verbindungstyp (HTTP oder RMI)
	 * @return Properties zum erzeugen eines Context, z.B. über InitialContext. Tritt ein Fehler auf, so wird NULL
	 *         zurückgegeben.
	 */
	public static Properties getClientProperties( String host, String port, ConnectionType connectionType )
	{
		return getClientProperties( host, port, null, null, connectionType, false );
	}

	/**
	 * Methode erzeugt anhand der übergebenen Parameter die für einen Lookup benötigten Properties und gibt diese
	 * zurück.
	 * 
	 * @param host
	 *            Hostname unter dem der Server erreichbar ist
	 * @param port
	 *            Port über den der Lookup laufen soll
	 * @param userName
	 *            Benutzername der für den Verbindungsaufbau verwendet werden soll
	 * @param userCredential
	 *            Benutzer-Credential für den Verbindungsaufbau
	 * @param connectionType
	 *            Zu verwendender Verbindungstyp (HTTP oder RMI)
	 * @return Properties zum erzeugen eines Context, z.B. über InitialContext. Tritt ein Fehler auf, so wird NULL
	 *         zurückgegeben.
	 */
	public static Properties getClientProperties( String host, String port, String userName, String userCredential,
			ConnectionType connectionType )
	{
		return getClientProperties( host, port, userName, userCredential, connectionType, false );
	}

	/**
	 * Methode erzeugt anhand der übergebenen Parameter die für einen Lookup benötigten Properties und gibt diese
	 * zurück.
	 * 
	 * @param host
	 *            Hostname unter dem der Server erreichbar ist
	 * @param port
	 *            Port über den der Lookup laufen soll
	 * @param userName
	 *            Benutzername der für den Verbindungsaufbau verwendet werden soll
	 * @param userCredential
	 *            Benutzer-Credential für den Verbindungsaufbau
	 * @param connectionType
	 *            Zu verwendender Verbindungstyp (HTTP oder RMI)
	 * @param clustered
	 *            Flag ob der Server im Cluster läuft oder nicht (HAJNDI vs. JNDI)
	 * @return Properties zum erzeugen eines Context, z.B. über InitialContext. Tritt ein Fehler auf, so wird NULL
	 *         zurückgegeben.
	 */
	public static Properties getClientProperties( String host, String port, String userName, String userCredential,
			ConnectionType connectionType, boolean clustered )
	{
		if (StringUtils.isBlank( host ) || StringUtils.isBlank( port ))
		{
			return null;
		}

		String contextFactory = null;
		String connectionUrl = null;

		switch (connectionType)
		{
			case HTTP:
				contextFactory = BasicConstants.HTTP_CONTEXT_FACTORY;
				if (clustered)
				{
					connectionUrl = "http://" + host + ":" + port + "/invoker/HAJNDIFactory";
				}
				else
				{
					connectionUrl = "http://" + host + ":" + port + "/invoker/JNDIFactory";
				}
				break;

			case JNDI:
				contextFactory = BasicConstants.RMI_CONTEXT_FACTORY;
				connectionUrl = "jnp://" + host + ":" + port;
				break;

			default:
				contextFactory = BasicConstants.RMI_CONTEXT_FACTORY;
				connectionUrl = "jnp://" + host + ":" + port;
				break;
		}

		return getClientProperties( connectionUrl, contextFactory, userName, userCredential );
	}

	/**
	 * Methode erzeugt anhand der übergebenen Parameter die für einen Lookup benötigten Properties und gibt diese
	 * zurück.
	 * 
	 * @param connectionUrl
	 *            URL für die Verbindung zum Server
	 * @param contextFactory
	 *            ContextFactory die für den Verbindungsaufbau verwendet werden soll. (z.B. RMI oder HTTP)
	 * @param userName
	 *            Benutzername der für den Verbindungsaufbau verwendet werden soll
	 * @param userCredential
	 *            Benutzer-Credential für den Verbindungsaufbau
	 * @return Properties zum erzeugen eines Context, z.B. über InitialContext. Tritt ein Fehler auf, so wird NULL
	 *         zurückgegeben.
	 */
	public static Properties getClientProperties( String connectionUrl, String contextFactory, String userName,
			String userCredential )
	{
		if (StringUtils.isBlank( contextFactory ) || StringUtils.isBlank( connectionUrl ))
		{
			return null;
		}

		Properties properties = new Properties();
		properties.put( Context.URL_PKG_PREFIXES, BasicConstants.URL_PKG_PREFIXES );
		properties.put( Context.INITIAL_CONTEXT_FACTORY, contextFactory );
		properties.put( Context.PROVIDER_URL, connectionUrl );

		if (StringUtils.isNotBlank( userName ) && StringUtils.isNotBlank( userCredential ))
		{
			properties.put( Context.SECURITY_PRINCIPAL, userName );
			properties.put( Context.SECURITY_CREDENTIALS, userCredential );
		}

		return properties;
	}
}
