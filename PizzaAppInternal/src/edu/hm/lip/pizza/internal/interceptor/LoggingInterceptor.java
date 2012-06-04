package edu.hm.lip.pizza.internal.interceptor;

import java.util.Arrays;
import java.util.Date;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import edu.hm.basic.logging.BasicLogger;

/**
 * Interceptor Klasse logged einen Methodenaufruf in Applikations Log-File.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Interceptor
public class LoggingInterceptor
{

	/**
	 * Interceptor Methode logged die übergebenen Parameter, falls Parameter existieren. Im zweiten Schritt wird die
	 * Interceptor-Chain aufgerufen und die Zeit der benötigten dauer gemessen, diese wir zusammen mit dem Rückgabewert
	 * in das Log-File geschrieben. Tritt eine Exception auf, so wird diese ins Log geschrieben und weitergeworfen.
	 * 
	 * @param ctx
	 *            Interception Context
	 * @return proceed object for call chain
	 * @throws Exception
	 *             Exception
	 */
	@AroundInvoke
	public Object log( InvocationContext ctx ) throws Exception
	{
		Class<?> callerClass = ctx.getMethod().getDeclaringClass();

		Object ret = null;
		long duration = -1;

		Object[] parameters = ctx.getParameters();
		String stringParam = Arrays.toString( parameters );

		if (parameters.length > 0)
		{
			BasicLogger.logInfo( callerClass, "Method:" + ctx.getMethod().getName() + ", Parameters: " + stringParam );
		}

		try
		{
			long startMethod = new Date().getTime();
			ret = ctx.proceed();
			long endMethod = new Date().getTime();

			duration = endMethod - startMethod;

		}
		catch (Exception e)
		{
			BasicLogger.logError( callerClass, "Method:" + ctx.getMethod().getName() + ", " + e.getMessage() );
			throw e;
		}

		BasicLogger.logInfo( callerClass, "Method:" + ctx.getMethod().getName() + ", Return: " + ret + ", Durration:" + duration
				+ " ms" );

		return ret;
	}

}
