package edu.hm.lip.pizza.internal.interceptor;

import java.io.IOException;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import edu.hm.lip.pizza.api.object.ApiConstants;
import edu.hm.lip.pizza.api.object.enumeration.MessageType;
import edu.hm.lip.pizza.api.object.resource.DriverArrival;
import edu.hm.lip.pizza.api.object.resource.DriverRoute;
import edu.hm.lip.pizza.api.object.resource.MessageContainer;

/**
 * Interceptor Klasse pushed die Vorraussichtliche Ankunftszeit des Fahrers in die ActiveMQ.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Interceptor
public class DriverArrivalActiveMQInterceptor
{

	private static final String ACTIVEMQ_BASE_URL = "http://localhost:8161/demo/message/pizzaapp?type=topic";

	/**
	 * Interceptor Methode führt die den eigentlichen Call zunächst aus und überprüft ob es sich bei der Methode um die
	 * Annotierte Methode handelt. Die erhaltene Dauer der Fahrerroute wird in die ActiveMQ als JSON-Objekt versand.
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

		DriverRoute driverRoute = (DriverRoute) ret;

		String input = "body=" + createRequestJSONObject( id, driverRoute );

		ClientRequest request = new ClientRequest( ACTIVEMQ_BASE_URL );
		request.header( "Authorization", "BASIC " + ApiConstants.ACTIVEMQ_AUTHORIZATION_CREDENTIALS_BASE64 );
		request.body( MediaType.APPLICATION_FORM_URLENCODED, input );

		ClientResponse<String> response = request.post( String.class );

		if (response.getStatus() >= 400)
		{
			throw new RuntimeException( "Failed : HTTP error code : " + response.getStatus() );
		}

		return ret;
	}

	/**
	 * Erzeugt ein Json String der Ressource DriverArrival.
	 * 
	 * @param driverId
	 *            Fahreridentifikator
	 * @param driverRoute
	 *            Fahrerroute
	 * @return JSON-String
	 * @throws IOException
	 */
	private String createRequestJSONObject( int driverId, DriverRoute driverRoute ) throws IOException
	{

		DriverArrival driverArrival = new DriverArrival();
		driverArrival.setDriverId( driverId );
		driverArrival.setSeconds( driverRoute.getMeassurementValue() );

		ObjectMapper mapper = new ObjectMapper();
		MessageContainer message = new MessageContainer();
		message.setMessage( driverArrival );
		message.setMessageType( MessageType.DRIVER_ARRIVAL );

		String jsonOrder = mapper.writeValueAsString( message );

		return jsonOrder;

	}

}
