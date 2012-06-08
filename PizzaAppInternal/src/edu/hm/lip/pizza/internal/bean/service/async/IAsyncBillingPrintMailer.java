package edu.hm.lip.pizza.internal.bean.service.async;

import edu.hm.lip.pizza.api.object.resource.Order;

/**
 * Interface der Bean für das asynchrone versenden der Rechnung einer Bestellung.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public interface IAsyncBillingPrintMailer
{

	/**
	 * Sendet eine Email an den Pizzastore und wird via Outlook regeln automatisch ausgedruckt.
	 * 
	 * @param order
	 *            Ressource-Objekt der Bestellung.
	 */
	void sendOrderBilling( Order order );

}
