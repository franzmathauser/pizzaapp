package edu.hm.lip.pizza.services.bean;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import edu.hm.lip.pizza.api.communication.request.IDriverServiceLocal;
import edu.hm.lip.pizza.api.communication.request.IOrderServiceLocal;
import edu.hm.lip.pizza.api.object.resources.Driver;
import edu.hm.lip.pizza.api.object.resources.GPSData;
import edu.hm.lip.pizza.api.object.resources.Order;
import edu.hm.lip.pizza.api.object.resources.OrderId;
import edu.hm.lip.pizza.internal.annotation.DriverGPSActiveMQInterceptorMethodSelector;
import edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal;
import edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal;
import edu.hm.lip.pizza.internal.converter.DriverConverter;
import edu.hm.lip.pizza.internal.converter.GPSDataConverter;
import edu.hm.lip.pizza.internal.converter.OrderConverter;
import edu.hm.lip.pizza.internal.interceptor.DriverGPSActiveMQInterceptor;
import edu.hm.lip.pizza.internal.object.entities.EntityDriver;
import edu.hm.lip.pizza.internal.object.entities.EntityGPSData;
import edu.hm.lip.pizza.internal.object.entities.EntityOrder;

/**
 * REST-Service für die Fahrerdomäne. Verfügbare Aktionen: GET, POST, PUT, DELETE
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Stateless
@Interceptors( DriverGPSActiveMQInterceptor.class )
public class DriverService implements IDriverServiceLocal
{

	@EJB
	private IDriverDAOLocal driverDAO;

	@EJB
	private IOrderDAOLocal orderDAO;
	
	@EJB
	private IOrderServiceLocal orderService;
	
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
		return DriverConverter.convertEntityToServiceDriver( driverDAO.read( id ) );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverServiceLocal#update(int,
	 *      edu.hm.lip.pizza.api.object.resources.Driver)
	 */
	@Override
	public Driver update( int id, Driver driver )
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverServiceLocal#remove(int)
	 */
	@Override
	public Driver remove( int id )
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
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverServiceLocal#addOrder(int,
	 *      edu.hm.lip.pizza.api.object.resources.Order)
	 */
	@Override
	public void addOrder( int id, OrderId orderId )
	{
		EntityDriver driver = driverDAO.read( id );
		EntityOrder order = orderDAO.read( orderId.getId() );
		order.setDriver( driver );
		orderDAO.update( order );
		
		//set next order stage
		orderService.createNextOrderStage( orderId.getId() );
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverServiceLocal#removeOrder(int, int)
	 */
	@Override
	public void removeOrder( int driverId, int orderId )
	{
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverServiceLocal#createGPSData(int,
	 *      edu.hm.lip.pizza.api.object.resources.GPSData)
	 */
	@Override
	@DriverGPSActiveMQInterceptorMethodSelector
	public void createGPSData( int id, GPSData gpsData )
	{
		if (gpsData.getDate() == null)
		{
			gpsData.setDate( new Date() );
		}

		EntityDriver eDriver = driverDAO.read( id );
		if (eDriver != null)
		{
			List<EntityGPSData> gpsDatas = eDriver.getGpsData();

			EntityGPSData eGPSData = GPSDataConverter.convertServiceToEntityGPSData( gpsData );
			eGPSData.setDriver( eDriver );
			gpsDatas.add( eGPSData );
			eDriver.setGpsData( gpsDatas );
			driverDAO.update( eDriver );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverServiceLocal#getUndeliveredOrders(int)
	 */
	@Override
	public List<Order> getUndeliveredOrders( int id )
	{
		List<EntityOrder> eOrderList = driverDAO.getUndeliverdOrders( id );
		return OrderConverter.convertEntityToServiceOrderList( eOrderList );
	}

}
