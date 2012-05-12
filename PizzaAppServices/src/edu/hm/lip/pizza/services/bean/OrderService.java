package edu.hm.lip.pizza.services.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import edu.hm.lip.pizza.api.communication.request.IOrderServiceLocal;
import edu.hm.lip.pizza.api.object.enums.Size;
import edu.hm.lip.pizza.api.object.resources.Order;
import edu.hm.lip.pizza.api.object.resources.OrderLine;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal;
import edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal;
import edu.hm.lip.pizza.internal.bean.database.IProductConfigurationDAOLocal;
import edu.hm.lip.pizza.internal.converter.OrderConverter;
import edu.hm.lip.pizza.internal.object.entities.EntityOrder;
import edu.hm.lip.pizza.internal.object.entities.EntityOrderLine;
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
		// TODO Auto-generated method stub
		return null;
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
		for (OrderLine orderLine : order.getOrderLines())
		{

			EntityProductConfiguration eProductConfiguration = productConfigurationDAOBean.getProductConfiguration(
					orderLine.getProductId(), convertStringSizeToSize( orderLine.getSize() ) );
			
			
			EntityOrderLine eOrderLine = eOrder.getOrderLines().get( i++ );
			
			eOrderLine.setOrder( eOrder );
			
			eOrderLine.setProductConfiguration( eProductConfiguration );
		}

		eOrder = orderDAOBean.create( eOrder );
		//return OrderConverter.convertEntityToServiceOrder( eOrder );
		return null;
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

	private static Size convertStringSizeToSize( String size )
	{
		Size eSize = null;
		if (size.equals( "l" ))
		{
			eSize = Size.L;
		}
		else if (size.equals( "xl" ))
		{
			eSize = Size.XL;
		}
		else if (size.equals( "xxl" ))
		{
			eSize = Size.XXL;
		}
		return eSize;
	}

}
