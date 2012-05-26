package edu.hm.lip.pizza.services.asynchronous;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import edu.hm.lip.pizza.api.object.resources.Customer;
import edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal;
import edu.hm.lip.pizza.internal.converter.CustomerConverter;
import edu.hm.lip.pizza.internal.object.entities.EntityCustomer;

/**
 * Asynchrone Methode um die Lat/Lon Koordinaten von Google zu laden und mit in den Customer zu speichern.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Stateless
public class AsyncGeoEncoder
{

	@EJB
	private ICustomerDAOLocal customerDAOBean;

	/**
	 * Fügt einem EntityCustomer die Geokoordinaten bzgl. seiner Adresse hinzu. Die Methode wird asynchron ausgeführt.
	 * 
	 * @param eCustomer
	 *            Datenbank-Objekt eines Kunden
	 */
	@Asynchronous
	public void doGeoEncoding( EntityCustomer eCustomer )
	{

		Customer customer = CustomerConverter.convertEntityToServiceCustomer( eCustomer );
		String address = customer.getAddressAsString();
		ClientRequest request = new ClientRequest( "http://maps.googleapis.com/maps/api/geocode/json?address=" + address
				+ "&sensor=false" );
		request.accept( MediaType.APPLICATION_JSON );

		ClientResponse<String> response;
		try
		{
			response = request.get( String.class );

			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readValue( response.getEntity(), JsonNode.class );
			JsonNode locationNode = rootNode.findValue( "location" );
			String latitude = locationNode.findValue( "lat" ).toString();
			String longitude = locationNode.findValue( "lng" ).toString();

			eCustomer.setLat( Double.parseDouble( latitude ) );
			eCustomer.setLon( Double.parseDouble( longitude ) );
			customerDAOBean.update( eCustomer );
		}
		catch (Exception e)
		{
			// lat und lon konnten nicht gefunden werden und werden nicht in die Datenbank geschrieben
		}

	}

}
