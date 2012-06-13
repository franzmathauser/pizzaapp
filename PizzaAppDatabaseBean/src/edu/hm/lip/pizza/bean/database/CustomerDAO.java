package edu.hm.lip.pizza.bean.database;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal;
import edu.hm.lip.pizza.internal.object.entity.EntityCustomer;
import edu.hm.lip.pizza.internal.object.query.CustomerQueryConstants;

/**
 * Bean für den Datenbankzugriff auf die Customer Entität.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Stateless
public class CustomerDAO extends AbstractBean implements ICustomerDAOLocal
{

	@PersistenceContext( unitName = "PizzaAppManager" )
	private EntityManager em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal#create(edu.hm.lip.pizza.internal.object.entity.EntityCustomer)
	 */
	@Override
	public EntityCustomer create( EntityCustomer customer )
	{
		em.persist( customer );
		em.flush();
		return customer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal#readAll()
	 */
	@Override
	public List<EntityCustomer> readAll()
	{
		TypedQuery<EntityCustomer> query = em.createNamedQuery( CustomerQueryConstants.GET_ALL_CUSTOMERS, EntityCustomer.class );
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
	 * @see edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal#update(edu.hm.lip.pizza.internal.object.entity.EntityCustomer)
	 */
	@Override
	public EntityCustomer update( EntityCustomer customer )
	{
		customer = em.merge( customer );
		em.flush();
		return customer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal#delete(edu.hm.lip.pizza.internal.object.entity.EntityCustomer)
	 */
	@Override
	public void delete( EntityCustomer customer )
	{
		em.remove( customer );
		em.flush();
	}

}
