package edu.hm.lip.pizza.internal.interceptor;

import java.util.List;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.lip.pizza.api.object.resource.GPSData;
import edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal;
import edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal;
import edu.hm.lip.pizza.internal.bean.service.async.IAsyncHomezoneNotificationMailer;
import edu.hm.lip.pizza.internal.converter.OrderConverter;
import edu.hm.lip.pizza.internal.object.entity.EntityOrder;
import edu.hm.lip.pizza.internal.util.GPSDistanceCalculator;

/**
 * Interceptor Klasse pushed neue GPS-Daten eines Fahers in die ActiveMQ via Rest-Webservice.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Interceptor
public class HomezoneNotificationInterceptor
{

	/**
	 * Schwellwert ab wann eine Nachricht versendet wird.
	 */
	private static final Double NOTIFICATION_THRESHOLD = 500.0;

	@EJB
	private IDriverDAOLocal driverDAO;

	@EJB
	private IOrderDAOLocal orderDAO;

	@EJB
	private IAsyncHomezoneNotificationMailer asyncHomezoneNotificationMailer;

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
	public Object gpsDataInterception( InvocationContext ctx ) throws Exception
	{
		Object[] parameters = ctx.getParameters();
		Object ret = ctx.proceed();

		int id = (Integer) parameters[0];
		GPSData gpsData = (GPSData) parameters[1];
		Double srcLat = gpsData.getLat();
		Double srcLon = gpsData.getLon();

		List<EntityOrder> orders = driverDAO.getUndeliverdOrders( id );

		for (EntityOrder order : orders)
		{
			Double destLat = order.getCustomer().getLat();
			Double destLon = order.getCustomer().getLon();

			if (!order.isHomezoneNotificationFlag() && destLat != null && destLon != null && srcLat != null && srcLon != null)
			{
				Double distance = GPSDistanceCalculator.calculate( srcLat, srcLon, destLat, destLon );

				BasicLogger.logInfo( HomezoneNotificationInterceptor.class, "Driver #" + id + " has a distance to order #"
						+ order.getId() + " of " + distance + " meters." );

				if (distance < NOTIFICATION_THRESHOLD)
				{
					order.setHomezoneNotificationFlag( true );
					orderDAO.update( order );

					BasicLogger.logInfo( HomezoneNotificationInterceptor.class, "Send a Homezone-Notification Mail to order #"
							+ order.getId() );
					asyncHomezoneNotificationMailer
							.sendHomezoneNotification( OrderConverter.convertEntityToServiceOrder( order ) );
				}

			}
		}

		return ret;
	}

}
