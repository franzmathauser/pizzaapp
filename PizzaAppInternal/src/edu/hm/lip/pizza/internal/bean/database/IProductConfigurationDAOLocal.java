package edu.hm.lip.pizza.internal.bean.database;

import javax.ejb.Local;

import edu.hm.lip.pizza.api.object.enumeration.Size;
import edu.hm.lip.pizza.internal.object.entity.EntityProductConfiguration;

/**
 * @author Franz Mathauser
 */
@Local
public interface IProductConfigurationDAOLocal
{

	/**
	 * @param product
	 *            Produktidentifikator
	 * @param size
	 *            Produktgröße
	 * @return Produktkonfiguration
	 */
	EntityProductConfiguration getProductConfiguration( Integer product, Size size );

}
