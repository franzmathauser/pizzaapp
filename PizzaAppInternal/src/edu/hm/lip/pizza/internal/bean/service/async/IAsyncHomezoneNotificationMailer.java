package edu.hm.lip.pizza.internal.bean.service.async;

import edu.hm.lip.pizza.api.object.resource.Order;

/**
 * Interface der Bean für das asynchrone versenden der Homenotification Nachricht an den Customer.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public interface IAsyncHomezoneNotificationMailer
{

	/**
	 * Sendet eine Email an den Customer, sobald sich der Faher in der nähe des Lieferorts befindet.
	 * 
	 * @param order
	 *            Ressource-Objekt der Bestellung.
	 */
	void sendHomezoneNotification( Order order );

}
