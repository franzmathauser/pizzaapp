package edu.hm.lip.pizza.api.request;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import edu.hm.lip.pizza.api.object.ressources.Product;

/**
 * @author Franz Mathauser
 */
@Local
@Path( "/products" )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public interface IProductServiceLocal
{

	/**
	 * @return
	 */
	@Path( "" )
	@GET
	List<Product> findAll();
}
