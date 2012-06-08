package edu.hm.lip.pizza.internal.interceptor;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import edu.hm.lip.pizza.api.object.resource.Order;
import edu.hm.lip.pizza.internal.bean.service.async.IAsyncBillingPrintMailer;

/**
 * Interceptor Klasse sendet eine Email an den PizzaStore mit den Rechnungsinformationen.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Interceptor
public class BillingPrintMailInterceptor
{

	@EJB
	private IAsyncBillingPrintMailer asyncBillingPrintMailer;

	/**
	 * Interceptor führt zunächst die Annotierte Methode aus und sendet die Informationen der Order-Ressource an den
	 * Pizza-Store.
	 * 
	 * @param ctx
	 *            Interception Context
	 * @return proceed object for call chain
	 * @throws Exception
	 *             Exception
	 */
	@AroundInvoke
	public Object sendCurrentStage( InvocationContext ctx ) throws Exception
	{

		Order order = null;

		order = (Order) ctx.proceed();

		asyncBillingPrintMailer.sendOrderBilling( order );

		return order;
	}

}
