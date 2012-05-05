package edu.hm.lip.pizza.internal.config;

/**
 * Singleton Implementierung für den globalen Zugriff auf die Konfiguration.
 * 
 * @author Stefan Wörner
 */
public final class PizzaAppConfigurationSingleton extends PizzaAppConfiguration
{

	private static PizzaAppConfigurationSingleton m_Config = new PizzaAppConfigurationSingleton();

	/**
	 * Konstruktor.
	 */
	private PizzaAppConfigurationSingleton()
	{
		super( PizzaAppConfigurationConstants.CONFIG_FILE_NAME );
	}

	/**
	 * Singleton Factory Methode.
	 * 
	 * @return unique Instanz von {@link PizzaAppConfigurationSingleton}
	 */
	public static PizzaAppConfigurationSingleton getInstance()
	{
		synchronized (m_Config)
		{
			return m_Config;
		}
	}
}
