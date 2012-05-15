package edu.hm.lip.pizza.internal.converter;

import java.util.ArrayList;
import java.util.List;

import edu.hm.lip.pizza.api.object.enums.Size;
import edu.hm.lip.pizza.api.object.resources.OrderLine;
import edu.hm.lip.pizza.internal.object.entities.EntityOrderLine;
import edu.hm.lip.pizza.internal.object.entities.EntityProductConfiguration;

/**
 * Konverter-Klasse fuer OrderLine-Objekte, um zwischen dem Service-Datenmodell und dem Entitäten-Datenmodell zu
 * konvertieren.
 * 
 * @author Franz Mathauser
 */
public final class OrderLineConverter
{

	private OrderLineConverter()
	{

	}

	/**
	 * Konvertiert das Entity-OrderLine-Objekt in ein OrderLine-Objekt im Service-Datenmodell.
	 * 
	 * @param eOrderLine
	 *            Entity-OrderLine-Objekt
	 * @return OrderLine-Objekt aus dem Service-Datenmodell
	 */
	public static OrderLine convertEntityToServiceOrderLine( EntityOrderLine eOrderLine )
	{
		if (eOrderLine == null)
		{
			return null;
		}

		OrderLine orderLine = new OrderLine();
		orderLine.setId( eOrderLine.getId() );
		orderLine.setProductId( eOrderLine.getProductConfiguration().getProduct().getId() );
		orderLine.setQuantity( eOrderLine.getQuantity() );
		orderLine.setSize( eOrderLine.getProductConfiguration().getSize().toString().toLowerCase() );
		return orderLine;

	}

	/**
	 * Konvertiert ein OrderLine-Objekt aus dem Service-Datenmodell in ein Entity-OrderLine-Objekt.
	 * 
	 * @param orderLine
	 *            OrderLine-Objekt aus dem Service-Datenmodell
	 * @return Entity-OrderLine-Objekt
	 */
	public static EntityOrderLine convertServiceToEntityOrderLine( OrderLine orderLine )
	{
		if (orderLine == null)
		{
			return null;
		}

		EntityOrderLine eOrderLine = new EntityOrderLine();
		eOrderLine.setId( orderLine.getId() );
		eOrderLine.setQuantity( orderLine.getQuantity() );
		EntityProductConfiguration eProductConfiguration = new EntityProductConfiguration();

		Size size = null;
		if (orderLine.getSize().toLowerCase().equals( "l" ))
		{
			size = Size.L;
		}
		else if (orderLine.getSize().toLowerCase().equals( "xl" ))
		{
			size = Size.XL;
		}
		else if (orderLine.getSize().toLowerCase().equals( "xxl" ))
		{
			size = Size.XXL;
		}

		eProductConfiguration.setSize( size );
		eOrderLine.setProductConfiguration( eProductConfiguration );

		return eOrderLine;

	}

	/**
	 * Konvertiert eine Liste von Entity-OrderLine-Objekten in eine Liste von OrderLine-Objekten im Service-Datenmodell.
	 * 
	 * @param eOrderLineList
	 *            Liste von Entity-OrderLine-Objekten
	 * @return Liste von OrderLine-Objekten aus dem Service-Datenmodell
	 */
	public static List<OrderLine> convertEntityToServiceOrderLineList( List<EntityOrderLine> eOrderLineList )
	{
		if (eOrderLineList == null || eOrderLineList.isEmpty())
		{
			return null;
		}

		List<OrderLine> orderLineList = new ArrayList<OrderLine>();

		for (EntityOrderLine eOrderLine : eOrderLineList)
		{
			orderLineList.add( convertEntityToServiceOrderLine( eOrderLine ) );
		}

		return orderLineList;
	}

	/**
	 * Konvertiert eine Liste von Service-OrderLine-Objekten in eine Liste von OrderLine-Objekten im Entity-Datenmodell.
	 * 
	 * @param orderLineList
	 *            Liste von Service-OrderLine-Objekten
	 * @return Liste von OrderLine-Objekten aus dem Entity-Datenmodell
	 */
	public static List<EntityOrderLine> convertServiceToEntityOrderLineList( List<OrderLine> orderLineList )
	{
		if (orderLineList == null || orderLineList.isEmpty())
		{
			return null;
		}

		List<EntityOrderLine> eOrderLineList = new ArrayList<EntityOrderLine>();

		for (OrderLine orderLine : orderLineList)
		{
			eOrderLineList.add( convertServiceToEntityOrderLine( orderLine ) );
		}

		return eOrderLineList;
	}

}
