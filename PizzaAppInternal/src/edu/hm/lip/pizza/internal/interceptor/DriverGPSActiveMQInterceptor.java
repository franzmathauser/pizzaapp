package edu.hm.lip.pizza.internal.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import edu.hm.lip.pizza.api.object.ApiConstants;
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

	private static final String ACTIVEMQ_BASE_URL = "http://localhost:8161/demo/message/driverlocation?type=topic";

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
		}

		return ret;
	}

	private String createRequestJSONObject( int driverId, GPSData gpsData )
	{
		StringBuilder jsonObject = new StringBuilder();

		jsonObject.append( "{" );
		jsonObject.append( "\"id\":" + driverId + "," );
		jsonObject.append( "\"lat\":" + gpsData.getLat() + "," );
		jsonObject.append( "\"lon\":" + gpsData.getLon() );
		jsonObject.append( "}" );

		return jsonObject.toString();
	}

}
