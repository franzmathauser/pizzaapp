package edu.hm.lip.pizza.internal.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import edu.hm.lip.pizza.api.object.resources.GPSData;
import edu.hm.lip.pizza.internal.annotation.DriverGPSActiveMQInterceptorMethodSelector;

/**
 * Interceptor Klasse pushed neue GPS-Daten eines Fahers in die ActiveMQ via Rest-Webservice.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Interceptor
public class DriverGPSActiveMQInterceptor
{

	/**
	 * Interceptor Methode führt die den eigentlichen Call zunächst aus und überprüft ob es sich bei der Methode um die
	 * Annotierte Methode handelt. Die erhaltenen GPS-Daten werden in die ActiveMQ als JSON-Objekt versand.
	 * 
	 * @param ctx
	 *            Interception Context
	 * @return proceed object for call chain
	 * @throws Exception
	 *             Exception
	 */
	@AroundInvoke
	public Object modifyGreeting( InvocationContext ctx ) throws Exception
	{
		Object[] parameters = ctx.getParameters();

		Object ret = ctx.proceed();
		if (ctx.getMethod().getAnnotation( DriverGPSActiveMQInterceptorMethodSelector.class ) != null)
		{
			int id = (Integer) parameters[0];
			GPSData gpsdata = (GPSData) parameters[1];
		}

//		try
//		{
//
//		}
//		catch (Exception e)
//		{
//			return null;
//		}

		return ret;
	}

}
