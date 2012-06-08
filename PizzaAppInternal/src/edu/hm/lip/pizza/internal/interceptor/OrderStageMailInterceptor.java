package edu.hm.lip.pizza.internal.interceptor;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import edu.hm.lip.pizza.api.object.resource.Order;
import edu.hm.lip.pizza.internal.bean.service.async.IAsyncOrderStageMailer;

/**
 * Interceptor Klasse sendet eine Email an den Customer mit der aktuellen Order Stage.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Interceptor
public class OrderStageMailInterceptor
{

	@EJB
	private IAsyncOrderStageMailer asyncOrderStageMailer;

	/**
	 * Interceptor führt zunächst die Annotierte Methode aus und sendet die Informationen der Order-Ressource an den
	 * Customer.
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

		asyncOrderStageMailer.sendCurrentStage( order );

		return order;
	}

}
