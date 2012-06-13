package edu.hm.lip.pizza.internal.config;

import java.net.URL;
import java.util.Iterator;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.DefaultConfigurationBuilder;

import edu.hm.basic.logging.BasicLogger;

/**
 * Konfigurationsklasse.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public class PizzaAppConfiguration extends AbstractConfiguration
{

	private Configuration m_Configuration;

	private String m_ConfigFilePath;

	/**
	 * Konstruktor mit Dateiname für die Konfiguration.
	 * 
	 * @param configFilePath
	 *            zur Konfiguration
	 */
	protected PizzaAppConfiguration( String configFilePath )
	{
		if (configFilePath == null)
		{
			throw new IllegalArgumentException( "Ungueltiger Parameter configFilePath: " + null );
		}

		m_ConfigFilePath = configFilePath;
		setDefaultListDelimiter( ';' );
		loadConfiguration();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.commons.configuration.AbstractConfiguration#addProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addProperty( String key, Object value )
	{
		m_Configuration.addProperty( key, value );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.commons.configuration.AbstractConfiguration#clearProperty(java.lang.String)
	 */
	@Override
	public void clearProperty( String key )
	{
		m_Configuration.clearProperty( key );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.commons.configuration.AbstractConfiguration#addPropertyDirect(java.lang.String, java.lang.Object)
	 */
	@Override
	protected void addPropertyDirect( String key, Object value )
	{
		throw new UnsupportedOperationException( "AddPropertyDirect wird nicht unterst�tzt." );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.commons.configuration.AbstractConfiguration#containsKey(java.lang.String)
	 */
	@Override
	public boolean containsKey( String key )
	{
		return m_Configuration.containsKey( key );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.commons.configuration.AbstractConfiguration#getKeys()
	 */
	@Override
	public Iterator<?> getKeys()
	{
		return m_Configuration.getKeys();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.commons.configuration.AbstractConfiguration#getProperty(java.lang.String)
	 */
	@Override
	public Object getProperty( String key )
	{
		return m_Configuration.getProperty( key );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.commons.configuration.AbstractConfiguration#isEmpty()
	 */
	@Override
	public boolean isEmpty()
	{
		return m_Configuration.isEmpty();
	}

	private void loadConfiguration()
	{
		try
		{
			URL configURL = getClass().getResource( m_ConfigFilePath );

			if (configURL == null)
			{
				configURL = getClass().getResource( "/" + m_ConfigFilePath );
			}

			DefaultConfigurationBuilder configBuilder = new DefaultConfigurationBuilder();
			configBuilder.setURL( configURL );
			m_Configuration = configBuilder.getConfiguration();
		}
		catch (Exception ex)
		{
			BasicLogger.logError( this, ex.getLocalizedMessage() );
			throw new IllegalArgumentException( ex.toString() );
		}
	}
}
