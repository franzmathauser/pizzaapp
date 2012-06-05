package edu.hm.lip.pizza.internal.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.lip.pizza.internal.config.ConfigurationConstants;
import edu.hm.lip.pizza.internal.config.ConfigurationSingleton;
import edu.hm.lip.pizza.internal.config.PizzaAppConfiguration;

/**
 * Klasse implementiert Methoden fuer dem Mail versandt.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public final class MailUtility
{

	private static PizzaAppConfiguration CONFIGURATION;

	private static boolean MAIL_ENABLED;

	private static boolean DEBUG_ENABLED;

	private static String HOST;

	private static String PORT;

	private static boolean AUTH_REQUIRED;

	private static String AUTH_USERNAME;

	private static String AUTH_CREDENTIAL;

	private static String SENDER_ADDRESS;

	private static String SENDER_NAME;

	private MailUtility()
	{

	}

	private static void initialize()
	{
		CONFIGURATION = ConfigurationSingleton.getInstance();

		MAIL_ENABLED = CONFIGURATION.getBoolean( ConfigurationConstants.MAIL_ENABLED );
		HOST = CONFIGURATION.getString( ConfigurationConstants.MAIL_SMTP_HOST );
		PORT = CONFIGURATION.getString( ConfigurationConstants.MAIL_SMTP_PORT );
		DEBUG_ENABLED = CONFIGURATION.getBoolean( ConfigurationConstants.MAIL_DEBUG_ENABLED );
		AUTH_REQUIRED = CONFIGURATION.getBoolean( ConfigurationConstants.MAIL_AUTHENTICATION_REQUIRED );
		AUTH_USERNAME = CONFIGURATION.getString( ConfigurationConstants.MAIL_AUTHENTICATION_USERNAME );
		AUTH_CREDENTIAL = CONFIGURATION.getString( ConfigurationConstants.MAIL_AUTHENTICATION_PASSWORD );
		SENDER_ADDRESS = CONFIGURATION.getString( ConfigurationConstants.MAIL_SENDER_ADDRESS );
		SENDER_NAME = CONFIGURATION.getString( ConfigurationConstants.MAIL_SENDER_NAME );
	}

	/**
	 * Versendet eine Mail mit den Rechnungsdaten den Pizzaapp-Store.
	 * 
	 * @param receipientAddress
	 *            E-Mail-Adresse des Empfaengers
	 * @param receipientName
	 *            Name des Empfaengers
	 * @param password
	 *            Generiertes Passwort
	 * @return Flag: Erfolgreich versandt.
	 */
	public static boolean sendMail( String receipientAddress, String receipientName, String password )
	{
		boolean success = false;

		// Config initialisieren
		initialize();

		if (MAIL_ENABLED)
		{
			try
			{
				EspMailAuthenticator auth = null;
				Session session = null;

				// Properties definieren
				Properties properties = new Properties();
				properties.put( "mail.smtp.host", HOST );
				properties.put( "mail.smtp.port", PORT );
				properties.put( "mail.debug", DEBUG_ENABLED );

				if (AUTH_REQUIRED)
				{
					properties.put( "mail.smtp.auth", AUTH_REQUIRED );
					auth = new MailUtility.EspMailAuthenticator( AUTH_USERNAME, AUTH_CREDENTIAL );

					// Session erzeugen
					session = Session.getDefaultInstance( properties, auth );
				}
				else
				{
					// Session erzeugen
					session = Session.getDefaultInstance( properties );
				}

				// Message erzeugen
				Message msg = new MimeMessage( session );

				// Sendedatum festlegen
				msg.setSentDate( new Date() );

				// Senderadresse festlegen
				msg.setFrom( new InternetAddress( SENDER_ADDRESS, SENDER_NAME, "UTF-8" ) );

				// Empfaenger festlegen
				msg.setRecipient( MimeMessage.RecipientType.TO, new InternetAddress( receipientAddress, receipientName, "UTF-8" ) );

				// Betreff festlegen
				msg.setSubject( "Rechnung: automatischer Druck!" );

				// Inhalt festlegen
				msg.setText( getMailContent( receipientName, password ) );

				// Versenden
				Transport.send( msg );
				success = true;
			}
			catch (Exception e)
			{
				BasicLogger.logError( MailUtility.class, e.getMessage() );
				success = false;
			}
		}
		else
		{
			BasicLogger.logInfo( MailUtility.class, password );
			success = true;
		}

		return success;
	}

	private static String getMailContent( String displayName, String password )
	{
		StringBuilder sb = new StringBuilder();
		// TODO append email message
		return sb.toString();
	}

	/**
	 * Mail-Authentifizierungs-Klasse.
	 * 
	 * @author Stefan Wörner
	 */
	private static class EspMailAuthenticator extends Authenticator
	{

		private final String m_user;

		private final String m_password;

		/**
		 * Der Konstruktor erzeugt ein MailAuthenticator Objekt aus den beiden Parametern user und passwort.
		 * 
		 * @param user
		 *            String, der Username fuer den Mailaccount.
		 * @param password
		 *            String, das Passwort fuer den Mailaccount.
		 */
		public EspMailAuthenticator( String user, String password )
		{
			this.m_user = user;
			this.m_password = password;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see javax.mail.Authenticator#getPasswordAuthentication()
		 */
		@Override
		protected PasswordAuthentication getPasswordAuthentication()
		{
			return new PasswordAuthentication( this.m_user, this.m_password );
		}

	}

}
