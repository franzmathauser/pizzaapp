package edu.hm.lip.pizza.services.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import edu.hm.lip.pizza.api.communication.request.ICustomerServiceLocal;
import edu.hm.lip.pizza.api.object.resources.Customer;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal;
import edu.hm.lip.pizza.internal.converter.CustomerConverter;
import edu.hm.lip.pizza.internal.object.entities.EntityCustomer;

/**
 * @author Franz Mathauser
 */
@Stateless
public class CustomerService extends AbstractBean implements ICustomerServiceLocal
{

	@EJB
	private ICustomerDAOLocal customerDAOBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.ICustomerServiceLocal#findAll()
	 */
	@Override
	public List<Customer> findAll()
	{
		return CustomerConverter.convertEntityToServiceCustomerList( customerDAOBean.readAll() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.ICustomerServiceLocal#create(edu.hm.lip.pizza.api.object.resources.Customer)
	 */
	@Override
	public Customer create( Customer customer )
	{
		EntityCustomer eCustomer = customerDAOBean.create( CustomerConverter.convertServiceToEntityCustomer( customer ) );
		return CustomerConverter.convertEntityToServiceCustomer( eCustomer );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.ICustomerServiceLocal#find(int)
	 */
	@Override
	public Customer find( int id )
	{
		EntityCustomer eCustomer = customerDAOBean.read( id );
		return CustomerConverter.convertEntityToServiceCustomer( eCustomer );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.ICustomerServiceLocal#update(edu.hm.lip.pizza.api.object.resources.Customer)
	 */
	@Override
	public Customer update( Customer customer )
	{
		EntityCustomer eCustomer = CustomerConverter.convertServiceToEntityCustomer( customer );
		customerDAOBean.update( eCustomer );

		return CustomerConverter.convertEntityToServiceCustomer( eCustomer );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.ICustomerServiceLocal#remove(int)
	 */
	@Override
	public void remove( int id )
	{
		EntityCustomer eCustomer = customerDAOBean.read( id );
		if (eCustomer != null)
		{
			customerDAOBean.delete( eCustomer );
		}

	}

}
