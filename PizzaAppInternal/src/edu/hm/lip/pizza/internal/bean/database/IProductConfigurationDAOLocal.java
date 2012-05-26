package edu.hm.lip.pizza.internal.bean.database;

import javax.ejb.Local;

import edu.hm.lip.pizza.api.object.enumeration.Size;
import edu.hm.lip.pizza.internal.object.entity.EntityProductConfiguration;

/**
 * Interface der Bean für den Datenbankzugriff auf die ProductConfiguration Entität.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Local
public interface IProductConfigurationDAOLocal
{

	/**
	 * Liest die Produktkonfiguration aus der Datenbank aus.
	 * 
	 * @param productId
	 *            Produktidentifikator
	 * @param size
	 *            Produktgröße
	 * @return Produktkonfiguration
	 */
	EntityProductConfiguration getProductConfiguration( Integer productId, Size size );

}
