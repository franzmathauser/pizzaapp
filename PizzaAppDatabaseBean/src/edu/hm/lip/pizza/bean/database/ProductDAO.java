package edu.hm.lip.pizza.bean.database;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.hm.lip.pizza.internal.bean.AbstractPizzaAppBean;
import edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal;
import edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal;
import edu.hm.lip.pizza.internal.object.entities.Product;

/**
 * @author Franz Mathauser
 */
@Stateless
public class ProductDAO extends AbstractPizzaAppBean implements IProductDAOLocal
{

	@EJB
	private ICustomerDAOLocal customerDAOLocal;

	@PersistenceContext( unitName = "PizzaAppManager" )
	private EntityManager em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal#create(edu.hm.lip.pizza.api.object.ressources.Product)
	 */
	@Override
	public Product create( Product product )
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
	@Override
	public List<Product> readAll()
	{
		Query query = em.createQuery( "SELECT p FROM Product p" );
		return (List<Product>) query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal#read(int)
	 */
	@Override
	public Product read( int id )
	{
		return em.find( Product.class, id );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal#update(edu.hm.lip.pizza.api.object.ressources.Product)
	 */
	@Override
	public Product update( Product product )
	{
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
	public void delete( Product product )
	{
		em.remove( product );
		em.flush();

	}

}
