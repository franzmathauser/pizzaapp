package edu.hm.lip.pizza.bean.database;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import edu.hm.lip.pizza.api.object.enumeration.Size;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.IProductConfigurationDAOLocal;
import edu.hm.lip.pizza.internal.object.entity.EntityProductConfiguration;
import edu.hm.lip.pizza.internal.object.query.ProductConfigurationQueryConstants;

/**
 * Bean für den Datenbankzugriff auf die ProductConfiguration Entität.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Stateless
public class ProductConfigurationDAO extends AbstractBean implements IProductConfigurationDAOLocal
{

	@PersistenceContext( unitName = "PizzaAppManager" )
	private EntityManager em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IProductConfigurationDAOLocal#getProductConfiguration(java.lang.Integer,
	 *      edu.hm.lip.pizza.api.object.enumeration.Size)
	 */
	@Override
	public EntityProductConfiguration getProductConfiguration( Integer productId, Size size )
	{
		TypedQuery<EntityProductConfiguration> query = em.createNamedQuery(
				ProductConfigurationQueryConstants.PRODUCT_CONFIG_BY_PRODUCT_AND_SIZE, EntityProductConfiguration.class );
		query.setParameter( "product_id", productId );
		query.setParameter( "size", size );
		return query.getSingleResult();
	}

}
