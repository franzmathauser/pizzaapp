package edu.hm.lip.pizza.bean.database;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.IProductConfigurationDAOLocal;
import edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal;
import edu.hm.lip.pizza.internal.object.entity.EntityProduct;
import edu.hm.lip.pizza.internal.object.entity.EntityProductConfiguration;

/**
 * Bean für den Datenbankzugriff auf die Product Entität.
 * 
 * @author Franz Mathauser
 */
@Stateless
public class ProductDAO extends AbstractBean implements IProductDAOLocal
{

	@PersistenceContext( unitName = "PizzaAppManager" )
	private EntityManager em;

	@EJB
	private IProductConfigurationDAOLocal productConfigurationDAO;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal#create(edu.hm.lip.pizza.api.object.ressources.Product)
	 */
	@Override
	public EntityProduct create( EntityProduct product )
	{
		em.persist( product );
		em.flush();
		return product;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal#readAll()
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public List<EntityProduct> readAll()
	{
		Query query = em.createQuery( "SELECT p FROM EntityProduct p", EntityProduct.class );
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal#read(int)
	 */
	@Override
	public EntityProduct read( int id )
	{
		return em.find( EntityProduct.class, id );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal#update(edu.hm.lip.pizza.api.object.ressources.Product)
	 */
	@Override
	public EntityProduct update( EntityProduct product )
	{
		List<EntityProductConfiguration> productConfigurations = new ArrayList<EntityProductConfiguration>();

		for (EntityProductConfiguration productConfiguration : product.getConfigurations())
		{
			EntityProductConfiguration eProductConfiguration = productConfigurationDAO.getProductConfiguration(
					product.getId(), productConfiguration.getSize() );

			// ProductConfiguration in Datenbank
			if (eProductConfiguration != null)
			{
				eProductConfiguration.setPrice( productConfiguration.getPrice() );
				productConfigurations.add( eProductConfiguration );
			}
			else
			{
				productConfigurations.add( productConfiguration );
			}

		}
		product.setConfigurations( productConfigurations );
		product = em.merge( product );
		em.flush();
		return product;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal#delete(edu.hm.lip.pizza.api.object.ressources.Product)
	 */
	@Override
	public void delete( EntityProduct product )
	{
		em.remove( product );
		em.flush();
	}

}
