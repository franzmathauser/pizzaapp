package edu.hm.lip.pizza.internal.converter;

import java.util.ArrayList;
import java.util.List;

import edu.hm.lip.pizza.api.object.resource.Order;
import edu.hm.lip.pizza.internal.object.entity.EntityOrder;
import edu.hm.lip.pizza.internal.object.entity.EntityOrderLine;
import edu.hm.lip.pizza.internal.object.entity.EntityOrderStage;

/**
 * Konverter-Klasse fuer Order-Objekte, um zwischen dem Service-Datenmodell und dem Entitaeten-Datenmodell zu
 * konvertieren.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public final class OrderConverter
{

	private OrderConverter()
	{

	}

	/**
	 * Konvertiert das Entity-Order-Objekt in ein ProductConfiguration-Objekt im Service-Datenmodell.
	 * 
	 * @param eOrder
	 *            Entity-Order-Objekt
	 * @return Order-Objekt aus dem Service-Datenmodell
	 */
	public static Order convertEntityToServiceOrder( EntityOrder eOrder )
	{
		if (eOrder == null)
		{
			return null;
		}

		Order order = new Order();

		order.setId( eOrder.getId() );
		order.setNote( eOrder.getNote() );
		List<EntityOrderStage> eOrderStages = eOrder.getStages();

		order.setCurrentStage( eOrderStages.get( eOrderStages.size() - 1 ).getStage() );
		order.setOrderDate( eOrderStages.get( 0 ).getCreateDate() );
		order.setOrderLines( OrderLineConverter.convertEntityToServiceOrderLineList( eOrder.getOrderLines() ) );
		order.setCustomer( CustomerConverter.convertEntityToServiceCustomer( eOrder.getCustomer() ) );

		order.setPrice( sumPrices( eOrder.getOrderLines() ) );

		return order;
	}

	private static Double sumPrices( List<EntityOrderLine> orderLines )
	{
		Double sum = 0.0;
		for (EntityOrderLine orderLine : orderLines)
		{
			sum += orderLine.getQuantity() * orderLine.getProductConfiguration().getPrice();
		}
		return sum;
	}

	/**
	 * Konvertiert ein Order-Objekt aus dem Service-Datenmodell in ein Entity-Order-Objekt.
	 * 
	 * @param order
	 *            Order-Objekt aus dem Service-Datenmodell
	 * @return Entity-Order-Objekt
	 */
	public static EntityOrder convertServiceToEntityOrder( Order order )
	{
		if (order == null)
		{
			return null;
		}

		EntityOrder eOrder = new EntityOrder();

		eOrder.setId( order.getId() );
		eOrder.setNote( order.getNote() );
		eOrder.setOrderLines( OrderLineConverter.convertServiceToEntityOrderLineList( order.getOrderLines() ) );
		eOrder.setCustomer( CustomerConverter.convertServiceToEntityCustomer( order.getCustomer() ) );

		return eOrder;
	}

	/**
	 * Konvertiert eine Liste von Entity-Order-Objekten in eine Liste von Order-Objekten im Service-Datenmodell.
	 * 
	 * @param eOrderList
	 *            Liste von Entity-Order-Objekten
	 * @return Liste von Order-Objekten
	 */
	public static List<Order> convertEntityToServiceOrderList( List<EntityOrder> eOrderList )
	{
		List<Order> orders = new ArrayList<Order>();

		for (EntityOrder eOrder : eOrderList)
		{
			orders.add( convertEntityToServiceOrder( eOrder ) );
		}

		return orders;
	}

}
