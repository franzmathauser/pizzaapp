package edu.hm.lip.pizza.services.bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import edu.hm.lip.pizza.api.communication.request.IOrderServiceLocal;
import edu.hm.lip.pizza.api.object.resources.Order;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal;
import edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal;
import edu.hm.lip.pizza.internal.bean.database.IProductConfigurationDAOLocal;
import edu.hm.lip.pizza.internal.converter.OrderConverter;
import edu.hm.lip.pizza.internal.manager.OrderStageManager;
import edu.hm.lip.pizza.internal.object.entities.EntityOrder;
import edu.hm.lip.pizza.internal.object.entities.EntityOrderLine;
import edu.hm.lip.pizza.internal.object.entities.EntityOrderStage;
import edu.hm.lip.pizza.internal.object.entities.EntityProductConfiguration;

/**
 * @author Franz Mathauser
 */
@Stateless
public class OrderService extends AbstractBean implements IOrderServiceLocal
{

	@EJB
	private IOrderDAOLocal orderDAOBean;

	@EJB
	private IProductConfigurationDAOLocal productConfigurationDAOBean;

	@EJB
	private ICustomerDAOLocal customerDAOBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IOrderServiceLocal#findAll()
	 */
	@Override
	public List<Order> findAll()
	{
		return OrderConverter.convertEntityToServiceOrderList( orderDAOBean.readAll() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IOrderServiceLocal#create(edu.hm.lip.pizza.api.object.resources.Order)
	 */
	@Override
	public Order create( Order order )
	{

		EntityOrder eOrder = OrderConverter.convertServiceToEntityOrder( order );
		eOrder.setCustomer( customerDAOBean.create( eOrder.getCustomer() ) );

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
		return OrderConverter.convertEntityToServiceOrder( eOrder );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IOrderServiceLocal#find(int)
	 */
	@Override
	public Order find( int id )
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IOrderServiceLocal#remove(int)
	 */
	@Override
	public void remove( int id )
	{
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IOrderServiceLocal#updateOrderToDelivered(int)
	 */
	@Override
	public Order updateOrderToDelivered( int id )
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IOrderServiceLocal#getUndeliveredOrders()
	 */
	@Override
	public List<Order> getUndeliveredOrders()
	{
		return OrderConverter.convertEntityToServiceOrderList( orderDAOBean.getUndeliveredOrders() );

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IOrderServiceLocal#create(int)
	 */
	@Override
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

}
