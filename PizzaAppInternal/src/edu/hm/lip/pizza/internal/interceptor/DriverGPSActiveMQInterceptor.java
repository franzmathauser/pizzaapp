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
import edu.hm.lip.pizza.api.object.resource.DriverLocation;
import edu.hm.lip.pizza.api.object.resource.GPSData;
import edu.hm.lip.pizza.api.object.resource.activemq.MessageContainer;

/**
 * Interceptor Klasse pushed neue GPS-Daten eines Fahers in die ActiveMQ via Rest-Webservice.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Interceptor
public class DriverGPSActiveMQInterceptor
{

	private static final String ACTIVEMQ_BASE_URL = "http://localhost:8161/demo/message/pizzaapp?type=topic";

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

		String input = "body=" + createRequestJSONObject( id, gpsData );

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

	private String createRequestJSONObject( int driverId, GPSData gpsData ) throws IOException
	{

		DriverLocation driverLocation = new DriverLocation();
		driverLocation.setDriverId( driverId );
		driverLocation.setLat( gpsData.getLat() );
		driverLocation.setLon( gpsData.getLon() );

		ObjectMapper mapper = new ObjectMapper();
		MessageContainer message = new MessageContainer();
		message.setMessage( driverLocation );
		message.setMessageType( MessageType.DRIVER_LOCATION );

		String jsonOrder = mapper.writeValueAsString( message );

		return jsonOrder;

	}

}
