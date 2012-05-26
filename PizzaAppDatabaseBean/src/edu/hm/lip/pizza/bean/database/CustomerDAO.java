package edu.hm.lip.pizza.bean.database;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal;
import edu.hm.lip.pizza.internal.object.entities.EntityCustomer;

/**
 * @author Franz Mathauser
 */
@Stateless
public class CustomerDAO extends AbstractBean implements ICustomerDAOLocal
{

	@PersistenceContext( unitName = "PizzaAppManager" )
	private EntityManager em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal#create(edu.hm.lip.pizza.api.object.ressources.Customer)
	 */
	@Override
	public EntityCustomer create( EntityCustomer entityCustomer )
	{
		em.persist( entityCustomer );
		em.flush();
		return entityCustomer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal#readAll()
	 */
	@Override
	public List<EntityCustomer> readAll()
	{
		Query query = em.createQuery( "SELECT c FROM EntityCustomer c", EntityCustomer.class );
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal#read(int)
	 */
	@Override
	public EntityCustomer read( int id )
	{
		return em.find( EntityCustomer.class, id );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal#update(edu.hm.lip.pizza.api.object.ressources.Customer)
	 */
	@Override
	public EntityCustomer update( EntityCustomer entityCustomer )
	{
		entityCustomer = em.merge( entityCustomer );
		em.flush();
		return entityCustomer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal#delete(edu.hm.lip.pizza.api.object.ressources.Customer)
	 */
	@Override
	public void delete( EntityCustomer entityCustomer )
	{
		em.remove( entityCustomer );
		em.flush();

	}

}
