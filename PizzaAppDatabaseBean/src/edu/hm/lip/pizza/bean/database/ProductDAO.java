package edu.hm.lip.pizza.bean.database;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal;
import edu.hm.lip.pizza.internal.object.entities.EntityProduct;

/**
 * @author Franz Mathauser
 */
@Stateless
public class ProductDAO extends AbstractBean implements IProductDAOLocal
{

	@PersistenceContext( unitName = "PizzaAppManager" )
	private EntityManager em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal#create(edu.hm.lip.pizza.api.object.ressources.Product)
	 */
	@Override
	public EntityProduct create( EntityProduct entityProduct )
	{
		em.persist( entityProduct );
		em.flush();
		return entityProduct;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal#readAll()
	 */
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
	public EntityProduct update( EntityProduct entityProduct )
	{
		entityProduct = em.merge( entityProduct );
		em.flush();
		return entityProduct;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal#delete(edu.hm.lip.pizza.api.object.ressources.Product)
	 */
	@Override
	public void delete( EntityProduct entityProduct )
	{
		em.remove( entityProduct );
		em.flush();

	}

}
