package edu.hm.lip.pizza.service.bean.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import edu.hm.lip.pizza.api.communication.request.ICustomerService;
import edu.hm.lip.pizza.api.object.resource.Customer;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal;
import edu.hm.lip.pizza.internal.converter.CustomerConverter;
import edu.hm.lip.pizza.internal.interceptor.LoggingInterceptor;
import edu.hm.lip.pizza.internal.object.entity.EntityCustomer;

/**
 * REST-Service für die Kundendomäne. Verfügbare Aktionen: GET, POST, PUT, DELETE
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Stateless
@Interceptors( LoggingInterceptor.class )
public class CustomerService extends AbstractBean implements ICustomerService
{

	@EJB
	private ICustomerDAOLocal customerDAOBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.ICustomerService#findAll()
	 */
	@Override
	public List<Customer> findAll()
	{
		return CustomerConverter.convertEntityToServiceCustomerList( customerDAOBean.readAll() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.ICustomerService#create(edu.hm.lip.pizza.api.object.resource.Customer)
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
	 * @see edu.hm.lip.pizza.api.communication.request.ICustomerService#find(int)
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
	 * @see edu.hm.lip.pizza.api.communication.request.ICustomerService#update(int,
	 *      edu.hm.lip.pizza.api.object.resource.Customer)
	 */
	@Override
	public Customer update( int id, Customer customer )
	{
		if (id != customer.getId())
		{
			return null;
		}

		EntityCustomer eCustomer = CustomerConverter.convertServiceToEntityCustomer( customer );
		customerDAOBean.update( eCustomer );

		return CustomerConverter.convertEntityToServiceCustomer( eCustomer );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.ICustomerService#remove(int)
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
