package edu.hm.lip.pizza.test.services.rest.pure;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;

import edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest;

/**
 * Abstrakte Basisklasse für alle REST Service Proxy Tests.
 * 
 * @author Stefan Wörner
 */
public abstract class AbstractRestServicePureTest extends AbstractRestServiceTest
{

	/**
	 * Liefert das ClientRequest-Objekt für den übergebenen Pfad.
	 * 
	 * @param path
	 *            URI Pfad
	 * @return ClientRequest Objekt
	 */
	protected static ClientRequest getClient( String path )
	{
		ClientRequest request = new ClientRequest( getRestUrl() + "/" + path );
		request.accept( MediaType.APPLICATION_JSON );

		return request;
	}

}
