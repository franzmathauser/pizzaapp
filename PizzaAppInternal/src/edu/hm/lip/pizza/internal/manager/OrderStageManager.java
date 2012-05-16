package edu.hm.lip.pizza.internal.manager;

import java.util.Date;
import java.util.List;

import edu.hm.lip.pizza.api.object.enums.Stage;
import edu.hm.lip.pizza.internal.object.entities.EntityOrderStage;

/**
 * Hilfsklasse für die Berechnung der einzelnen Bestellstati.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public final class OrderStageManager
{

	private OrderStageManager()
	{

	}

	/**
	 * Gibt den initialen Bestellstatus zurück.
	 * 
	 * @return Initialer Bestellstatus
	 */
	public static EntityOrderStage fistStage()
	{
		return next( null );
	}

	/**
	 * Gibt den nächsten Bestellstatus basierend auf der übergebenen Liste zurück.
	 * 
	 * @param orderStages
	 *            Liste der vorhandenen Bestellstati
	 * @return Nächster Bestellstatus
	 */
	public static EntityOrderStage next( List<EntityOrderStage> orderStages )
	{
		EntityOrderStage eOrderStage = new EntityOrderStage();
		eOrderStage.setCreateDate( new Date() );

		if (orderStages == null)
		{
			eOrderStage.setStage( Stage.ORDERED );
			return eOrderStage;
		}

		EntityOrderStage lastOrderStage = orderStages.get( orderStages.size() - 1 );

		switch (lastOrderStage.getStage())
		{
			case ORDERED:
				eOrderStage.setStage( Stage.IN_PREPARATION );
				break;
			case IN_PREPARATION:
				eOrderStage.setStage( Stage.IN_STOVE );
				break;
			case IN_STOVE:
				eOrderStage.setStage( Stage.IN_DELIVERY );
				break;
			case IN_DELIVERY:
				eOrderStage.setStage( Stage.DELIVERED );
				break;
			default:
				eOrderStage.setStage( Stage.ORDERED );
				break;
		}

		return eOrderStage;
	}

}
