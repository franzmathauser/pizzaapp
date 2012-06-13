package edu.hm.lip.pizza.internal.converter;

import edu.hm.lip.pizza.api.object.resource.ProductConfiguration;
import edu.hm.lip.pizza.internal.object.entity.EntityProductConfiguration;

/**
 * Konverter-Klasse fuer ProcuctConfiguration-Objekte, um zwischen dem Service-Datenmodell und dem
 * Entitaeten-Datenmodell zu konvertieren.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public final class ProductConfigurationConverter
{

	private ProductConfigurationConverter()
	{

	}

	/**
	 * Konvertiert das Entity-ProductConfiguration-Objekt in ein ProductConfiguration-Objekt im Service-Datenmodell.
	 * 
	 * @param eConfiguration
	 *            Entity-Product-Objekt
	 * @return Product-Objekt aus dem Service-Datenmodell
	 */
	public static ProductConfiguration convertEntityToServiceProductConfiguration( EntityProductConfiguration eConfiguration )
	{
		if (eConfiguration == null)
		{
			return null;
		}

		ProductConfiguration configuration = new ProductConfiguration();
		configuration.setId( eConfiguration.getId() );
		configuration.setSize( eConfiguration.getSize() );
		configuration.setPrice( eConfiguration.getPrice() );

		return configuration;
	}

	/**
	 * Konvertiert ein ProductConfiguration-Objekt aus dem Service-Datenmodell in ein Entity-Product-Objekt.
	 * 
	 * @param configuration
	 *            ProductConfiguration-Objekt aus dem Service-Datenmodell
	 * @return Entity-ProductConfiguration-Objekt
	 */
	public static EntityProductConfiguration convertServiceToEntityProductConfiguration( ProductConfiguration configuration )
	{
		if (configuration == null)
		{
			return null;
		}

		EntityProductConfiguration eCconfiguration = new EntityProductConfiguration();
		eCconfiguration.setId( configuration.getId() );
		eCconfiguration.setSize( configuration.getSize() );
		eCconfiguration.setPrice( configuration.getPrice() );

		return eCconfiguration;
	}

}
