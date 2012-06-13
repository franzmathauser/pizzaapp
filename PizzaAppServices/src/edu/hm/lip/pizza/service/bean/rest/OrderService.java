package edu.hm.lip.pizza.service.bean.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import edu.hm.lip.pizza.api.communication.request.IOrderService;
import edu.hm.lip.pizza.api.object.enumeration.Stage;
import edu.hm.lip.pizza.api.object.resource.Order;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal;
import edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal;
import edu.hm.lip.pizza.internal.bean.database.IProductConfigurationDAOLocal;
import edu.hm.lip.pizza.internal.bean.service.async.IAsyncGeoEncoder;
import edu.hm.lip.pizza.internal.converter.OrderConverter;
import edu.hm.lip.pizza.internal.interceptor.BillingPrintMailInterceptor;
import edu.hm.lip.pizza.internal.interceptor.LoggingInterceptor;
import edu.hm.lip.pizza.internal.interceptor.OrderActiveMQInterceptor;
import edu.hm.lip.pizza.internal.interceptor.OrderDeliveredActiveMQInterceptor;
import edu.hm.lip.pizza.internal.interceptor.OrderStageMailInterceptor;
import edu.hm.lip.pizza.internal.manager.OrderStageManager;
import edu.hm.lip.pizza.internal.object.entity.EntityCustomer;
import edu.hm.lip.pizza.internal.object.entity.EntityOrder;
import edu.hm.lip.pizza.internal.object.entity.EntityOrderLine;
import edu.hm.lip.pizza.internal.object.entity.EntityOrderStage;
import edu.hm.lip.pizza.internal.object.entity.EntityProductConfiguration;

/**
 * REST-Service für die Bestelldomäne. Verfügbare Aktionen: GET, POST, PUT, DELETE
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Stateless
@Interceptors( LoggingInterceptor.class )
public class OrderService extends AbstractBean implements IOrderService
{

	@EJB
	private IOrderDAOLocal orderDAOBean;

	@EJB
	private IProductConfigurationDAOLocal productConfigurationDAOBean;

	@EJB
	private ICustomerDAOLocal customerDAOBean;

	@EJB
	private IAsyncGeoEncoder asyncGeoEncoder;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IOrderService#findAll()
	 */
	@Override
	public List<Order> findAll()
	{
		return OrderConverter.convertEntityToServiceOrderList( orderDAOBean.readAll() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IOrderService#create(edu.hm.lip.pizza.api.object.resource.Order)
	 */
	@Override
	@Interceptors( { OrderActiveMQInterceptor.class, OrderStageMailInterceptor.class } )
	public Order create( Order order )
	{
		EntityOrder eOrder = OrderConverter.convertServiceToEntityOrder( order );
		EntityCustomer eCustomer = customerDAOBean.create( eOrder.getCustomer() );
		eOrder.setCustomer( eCustomer );

		int i = 0;
		for (EntityOrderLine eOrderLine : eOrder.getOrderLines())
		{
			EntityProductConfiguration eProductConfiguration = productConfigurationDAOBean.getProductConfiguration( order
					.getOrderLines().get( i++ ).getProductId(), eOrderLine.getProductConfiguration().getSize() );

			eOrderLine.setOrder( eOrder );
			eOrderLine.setProductConfiguration( eProductConfiguration );
		}

		List<EntityOrderStage> stages = new ArrayList<EntityOrderStage>();
		EntityOrderStage eOrderStage = OrderStageManager.firstStage();
		eOrderStage.setOrder( eOrder );
		stages.add( eOrderStage );
		eOrder.setStages( stages );

		eOrder = orderDAOBean.create( eOrder );

		asyncGeoEncoder.doGeoEncoding( eCustomer );

		return OrderConverter.convertEntityToServiceOrder( eOrder );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IOrderService#find(int)
	 */
	@Override
	public Order find( int id )
	{
		return OrderConverter.convertEntityToServiceOrder( orderDAOBean.read( id ) );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IOrderService#remove(int)
	 */
	@Override
	public void remove( int id )
	{
		EntityOrder eOrder = orderDAOBean.read( id );
		orderDAOBean.delete( eOrder );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IOrderService#updateOrderToDelivered(int)
	 */
	@Override
	@Interceptors( OrderDeliveredActiveMQInterceptor.class )
	public Order updateOrderToDelivered( int id )
	{
		EntityOrder eOrder = orderDAOBean.read( id );
		List<EntityOrderStage> eOrderStages = eOrder.getStages();

		EntityOrderStage nextStage = OrderStageManager.next( eOrderStages );
		if (nextStage != null && nextStage.getStage() == Stage.DELIVERED)
		{
			nextStage.setOrder( eOrder );

			eOrderStages.add( nextStage );

			orderDAOBean.update( eOrder );
		}

		return OrderConverter.convertEntityToServiceOrder( eOrder );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IOrderService#getUndeliveredOrders()
	 */
	@Override
	public List<Order> getUndeliveredOrders()
	{
		return OrderConverter.convertEntityToServiceOrderList( orderDAOBean.getUndeliveredOrders() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IOrderService#createNextOrderStage(int)
	 */
	@Override
	@Interceptors( { OrderStageMailInterceptor.class, BillingPrintMailInterceptor.class } )
	public Order createNextOrderStage( int id )
	{
		EntityOrder eOrder = orderDAOBean.read( id );
		List<EntityOrderStage> eOrderStages = eOrder.getStages();

		EntityOrderStage nextStage = OrderStageManager.next( eOrderStages );
		if (nextStage != null)
		{
			nextStage.setOrder( eOrder );
			eOrderStages.add( nextStage );
			orderDAOBean.update( eOrder );
		}

		return OrderConverter.convertEntityToServiceOrder( eOrder );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IOrderService#createPreviousOrderStage(int)
	 */
	@Override
	public Order createPreviousOrderStage( int id )
	{
		EntityOrder eOrder = orderDAOBean.read( id );
		List<EntityOrderStage> eOrderStages = eOrder.getStages();

		EntityOrderStage currentStage = OrderStageManager.current( eOrderStages );
		if (currentStage != null)
		{
			eOrderStages.remove( currentStage );
			eOrder.setStages( eOrderStages );
			orderDAOBean.update( eOrder );
		}

		return OrderConverter.convertEntityToServiceOrder( eOrder );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IOrderService#getNextOrderStage(int)
	 */
	@Override
	public String getNextOrderStage( int id )
	{
		EntityOrder eOrder = orderDAOBean.read( id );
		List<EntityOrderStage> eOrderStages = eOrder.getStages();
		EntityOrderStage nextStage = OrderStageManager.next( eOrderStages );

		if (nextStage == null)
		{
			return null;
		}
		return "{\"nextStage\": " + nextStage.getStage().name() + "}";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IOrderService#getPreviousOrderStage(int)
	 */
	@Override
	public String getPreviousOrderStage( int id )
	{
		EntityOrder eOrder = orderDAOBean.read( id );
		List<EntityOrderStage> eOrderStages = eOrder.getStages();
		EntityOrderStage previousStage = OrderStageManager.previous( eOrderStages );

		if (previousStage == null)
		{
			return null;
		}
		return "{\"previousStage\": " + previousStage.getStage().name() + "}";
	}

}
