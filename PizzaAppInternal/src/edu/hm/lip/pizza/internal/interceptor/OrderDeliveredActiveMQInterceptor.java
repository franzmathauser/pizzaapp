package edu.hm.lip.pizza.internal.interceptor;

import java.io.IOException;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import edu.hm.lip.pizza.api.object.ApiConstants;
import edu.hm.lip.pizza.api.object.enumeration.MessageType;
import edu.hm.lip.pizza.api.object.resource.Driver;
import edu.hm.lip.pizza.api.object.resource.activemq.MessageContainer;
import edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal;
import edu.hm.lip.pizza.internal.object.entity.EntityOrder;

/**
 * Interceptor Klasse pushed die eine ausgelieferte Bestellung des Fahrers in die ActiveMQ.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Interceptor
public class OrderDeliveredActiveMQInterceptor
{

	@EJB
	private IOrderDAOLocal orderDAO;

	private static final String ACTIVEMQ_BASE_URL = "http://localhost:8161/demo/message/pizzaapp?type=topic";

	/**
	 * Interceptor Methode führt die den eigentlichen Call zunächst aus. Der zuständige Fahrer wird als JSON-Object an
	 * die ActiveMQ gemeldet.
	 * 
	 * @param ctx
	 *            Interception Context
	 * @return proceed object for call chain
	 * @throws Exception
	 *             Exception
	 */
	@AroundInvoke
	public Object orderDelivered( InvocationContext ctx ) throws Exception
	{
		Object[] parameters = ctx.getParameters();
		Object ret = ctx.proceed();

		int orderId = (Integer) parameters[0];

		EntityOrder order = orderDAO.read( orderId );
		int driverId = order.getDriver().getId();

		Driver driver = new Driver();
		driver.setId( driverId );

		String input = "body=" + createRequestJSONObject( driver );

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
	 * Erzeugt ein Json String der Ressource Driver.
	 * 
	 * @param driver
	 *            Fahrer Ressource-Object
	 * @return JSON-String
	 * @throws IOException
	 */
	private String createRequestJSONObject( Driver driver ) throws IOException
	{

		ObjectMapper mapper = new ObjectMapper();
		MessageContainer message = new MessageContainer();
		message.setMessage( driver );
		message.setMessageType( MessageType.ORDER_DELIVERED );

		String jsonOrder = mapper.writeValueAsString( message );

		return jsonOrder;
	}

}
