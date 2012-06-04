package edu.hm.lip.pizza.service.bean.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import edu.hm.lip.pizza.api.communication.request.IDriverService;
import edu.hm.lip.pizza.api.communication.request.IOrderService;
import edu.hm.lip.pizza.api.object.enumeration.Stage;
import edu.hm.lip.pizza.api.object.resource.Driver;
import edu.hm.lip.pizza.api.object.resource.DriverRoute;
import edu.hm.lip.pizza.api.object.resource.GPSData;
import edu.hm.lip.pizza.api.object.resource.Order;
import edu.hm.lip.pizza.api.object.resource.OrderId;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal;
import edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal;
import edu.hm.lip.pizza.internal.bean.service.manager.IDriverRouteManagerLocal;
import edu.hm.lip.pizza.internal.converter.DriverConverter;
import edu.hm.lip.pizza.internal.converter.GPSDataConverter;
import edu.hm.lip.pizza.internal.converter.OrderConverter;
import edu.hm.lip.pizza.internal.interceptor.DriverArrivalActiveMQInterceptor;
import edu.hm.lip.pizza.internal.interceptor.DriverGPSActiveMQInterceptor;
import edu.hm.lip.pizza.internal.interceptor.LoggingInterceptor;
import edu.hm.lip.pizza.internal.manager.OrderStageManager;
import edu.hm.lip.pizza.internal.object.entity.EntityDriver;
import edu.hm.lip.pizza.internal.object.entity.EntityGPSData;
import edu.hm.lip.pizza.internal.object.entity.EntityOrder;
import edu.hm.lip.pizza.internal.object.entity.EntityOrderStage;

/**
 * REST-Service für die Fahrerdomäne. Verfügbare Aktionen: GET, POST, PUT, DELETE
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Stateless
@Interceptors( LoggingInterceptor.class )
public class DriverService extends AbstractBean implements IDriverService
{

	@EJB
	private IDriverDAOLocal driverDAO;

	@EJB
	private IOrderDAOLocal orderDAO;

	@EJB
	private IOrderService orderService;

	@EJB
	private IDriverRouteManagerLocal driverRouteManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverService#findAll()
	 */
	@Override
	public List<Driver> findAll()
	{
		return DriverConverter.convertEntityToServiceDriverList( driverDAO.readAll() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverService#create(edu.hm.lip.pizza.api.object.resource.Driver)
	 */
	@Override
	public Driver create( Driver driver )
	{
		EntityDriver eDriver = DriverConverter.convertServiceToEntityDriver( driver );
		return DriverConverter.convertEntityToServiceDriver( driverDAO.create( eDriver ) );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverService#find(int)
	 */
	@Override
	public Driver find( int id )
	{
		return DriverConverter.convertEntityToServiceDriver( driverDAO.read( id ) );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverService#update(int,
	 *      edu.hm.lip.pizza.api.object.resource.Driver)
	 */
	@Override
	public Driver update( int id, Driver driver )
	{
		if (id != driver.getId())
		{
			return null;
		}

		EntityDriver eDriver = driverDAO.update( DriverConverter.convertServiceToEntityDriver( driver ) );
		return DriverConverter.convertEntityToServiceDriver( eDriver );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverService#remove(int)
	 */
	@Override
	public void remove( int id )
	{
		EntityDriver eDriver = driverDAO.read( id );
		driverDAO.delete( eDriver );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverService#getRoute(int)
	 */
	@Override
	@Interceptors( DriverArrivalActiveMQInterceptor.class )
	public DriverRoute getRoute( int id )
	{
		return driverRouteManager.getRoute( id );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverService#addOrder(int,
	 *      edu.hm.lip.pizza.api.object.resource.OrderId)
	 */
	@Override
	public void addOrder( int id, OrderId orderId )
	{
		EntityDriver eDriver = driverDAO.read( id );
		EntityOrder eOrder = orderDAO.read( orderId.getId() );
		List<EntityOrderStage> eOrderStages = eOrder.getStages();

		if (OrderStageManager.current( eOrderStages ).getStage() == Stage.IN_STOVE)
		{
			eOrder.setDriver( eDriver );
			orderDAO.update( eOrder );

			// set next order stage
			orderService.createNextOrderStage( orderId.getId() );
		}
		else
		{
			// TODO Fehler melden!
			return;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverService#removeOrder(int, int)
	 */
	@Override
	public void removeOrder( int driverId, int orderId )
	{
		EntityDriver eDriver = driverDAO.read( driverId );
		EntityOrder eOrder = orderDAO.read( orderId );
		List<EntityOrderStage> eOrderStages = eOrder.getStages();

		if (eOrder.getDriver().equals( eDriver ) && OrderStageManager.current( eOrderStages ).getStage() == Stage.IN_DELIVERY)
		{
			eOrder.setDriver( null );
			orderDAO.update( eOrder );

			// set previous order stage
			orderService.createPreviousOrderStage( orderId );
		}
		else
		{
			// TODO Fehler melden!
			return;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverService#createGPSData(int,
	 *      edu.hm.lip.pizza.api.object.resource.GPSData)
	 */
	@Override
	@Interceptors( DriverGPSActiveMQInterceptor.class )
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
	 * @see edu.hm.lip.pizza.api.communication.request.IDriverService#getUndeliveredOrders(int)
	 */
	@Override
	public List<Order> getUndeliveredOrders( int id )
	{
		List<EntityOrder> eOrderList = driverDAO.getUndeliverdOrders( id );
		return OrderConverter.convertEntityToServiceOrderList( eOrderList );
	}

}
