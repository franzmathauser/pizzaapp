package edu.hm.lip.pizza.services.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import edu.hm.lip.pizza.api.communication.request.IDriverServiceLocal;
import edu.hm.lip.pizza.api.object.resources.Driver;
import edu.hm.lip.pizza.api.object.resources.Order;
import edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal;
import edu.hm.lip.pizza.internal.converter.DriverConverter;

/**
 * @author Franz Mathauser, Stefan Wörner
 */
@Stateless
public class DriverService implements IDriverServiceLocal
{

	@EJB
	private IDriverDAOLocal driverDAO;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverServiceLocal#findAll()
	 */
	@Override
	public List<Driver> findAll()
	{
		return DriverConverter.convertEntityToServiceDriverList( driverDAO.readAll() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverServiceLocal#create(edu.hm.lip.pizza.api.object.resources.Driver)
	 */
	@Override
	public Driver create( Driver driver )
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverServiceLocal#find(int)
	 */
	@Override
	public Driver find( int id )
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverServiceLocal#getRoute(int)
	 */
	@Override
	public List<Order> getRoute( int id )
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverServiceLocal#getOrders(int)
	 */
	@Override
	public List<Order> getOrders( int id )
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverServiceLocal#remove(int, int)
	 */
	@Override
	public void remove( int dId, int oId )
	{
		// TODO Auto-generated method stub

	}

}