package edu.hm.lip.pizza.bean.database;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.hm.lip.pizza.api.object.enums.Size;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.IProductConfigurationDAOLocal;
import edu.hm.lip.pizza.internal.object.entities.EntityProductConfiguration;

/**
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
	 *      edu.hm.lip.pizza.api.object.enums.Size)
	 */
	@Override
	public EntityProductConfiguration getProductConfiguration( Integer product, Size size )
	{
		Query query = em.createQuery( "from EntityProductConfiguration where product_id = :product_id and size = :size",
				EntityProductConfiguration.class );
		query.setParameter( "product_id", product );
		query.setParameter( "size", size );
		return (EntityProductConfiguration) query.getSingleResult();
	}

}
