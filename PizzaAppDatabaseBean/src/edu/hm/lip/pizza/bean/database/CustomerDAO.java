package edu.hm.lip.pizza.bean.database;

import java.util.List;

import javax.ejb.Stateless;

import edu.hm.lip.pizza.api.object.ressources.Customer;
import edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal;

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
	public Customer create( Customer customer )
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
	public List<Customer> readAll()
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
	public Customer read( int id )
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
	public Customer update( Customer customer )
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
	public void delete( Customer customer )
	{
		// TODO Auto-generated method stub

	}

}
