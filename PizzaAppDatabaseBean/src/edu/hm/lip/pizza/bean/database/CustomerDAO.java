package edu.hm.lip.pizza.bean.database;

import java.util.List;

import javax.ejb.Stateless;

import edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal;
import edu.hm.lip.pizza.internal.object.entities.EntityCustomer;

/**
 * @author Franz Mathauser
 */
@Stateless
public class CustomerDAO implements ICustomerDAOLocal
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal#create(edu.hm.lip.pizza.api.object.ressources.Customer)
	 */
	@Override
	public EntityCustomer create( EntityCustomer entityCustomer )
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal#readAll()
	 */
	@Override
	public List<EntityCustomer> readAll()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal#read(int)
	 */
	@Override
	public EntityCustomer read( int id )
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal#update(edu.hm.lip.pizza.api.object.ressources.Customer)
	 */
	@Override
	public EntityCustomer update( EntityCustomer entityCustomer )
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal#delete(edu.hm.lip.pizza.api.object.ressources.Customer)
	 */
	@Override
	public void delete( EntityCustomer entityCustomer )
	{
		// TODO Auto-generated method stub

	}

}
