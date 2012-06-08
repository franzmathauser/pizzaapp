package edu.hm.lip.pizza.internal.bean.service.async;

import edu.hm.lip.pizza.api.object.resource.Order;

/**
 * Interface der Bean für das asynchrone versenden der OrderStages einer Bestellung.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public interface IAsyncOrderStageMailer
{

	/**
	 * Sendet eine Email an den Customer mit der aktuellen Stage stand seiner Bestellung.
	 * 
	 * @param order
	 *            Ressource-Objekt der Bestellung.
	 */
	void sendCurrentStage( Order order );

}
