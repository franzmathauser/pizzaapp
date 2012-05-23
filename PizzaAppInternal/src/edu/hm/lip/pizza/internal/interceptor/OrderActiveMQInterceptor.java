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
import edu.hm.lip.pizza.api.object.resources.Order;
import edu.hm.lip.pizza.internal.annotation.OrderActiveMQInterceptorMethodSelector;

/**
 * Interceptor Klasse pushed neue Bestelldaten an die Bestellverwaltung. Dies wird ermöglicht durch einen POST
 * Rest-Call, welcher ein JSON-Objekt in die ActiveMQ stellt.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Interceptor
public class OrderActiveMQInterceptor
{

	private static final String ACTIVEMQ_BASE_URL = "http://localhost:8161/demo/message/orders?type=topic";

	/**
	 * Interceptor Methode führt die den eigentlichen Call zunächst aus und überprüft ob es sich bei der Methode um die
	 * "DriverGPSActiveMQInterceptorMethodSelector"-Annotierte Methode handelt. Die erhaltenen Bestellungsdaten werden
	 * in die ActiveMQ < JSON-Objekt versand.
	 * 
	 * @param ctx
	 *            Interception Context
	 * @return proceed object for call chain
	 * @throws Exception
	 *             Exception
	 */
	@AroundInvoke
	public Object orderInterception( InvocationContext ctx ) throws Exception
	{
		Object ret = ctx.proceed();

		if (ctx.getMethod().getAnnotation( OrderActiveMQInterceptorMethodSelector.class ) != null)
		{
			// use the database updated resource
			Order order = (Order) ret;

			String input = "body=" + createRequestJSONOrder( order );

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

	/**
	 * Erzeugt einen JSON-String aus einem Order-Ressource Object
	 * 
	 * @param order
	 *            Ressource Objekt einer Bestellung
	 * @return JSON-String repräsentation der Bestellung
	 * @throws IOException
	 */
	private String createRequestJSONOrder( Order order ) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		String jsonOrder = mapper.writeValueAsString( order );

		return jsonOrder;
	}

}
