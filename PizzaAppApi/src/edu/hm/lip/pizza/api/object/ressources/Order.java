package edu.hm.lip.pizza.api.object.ressources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * @author Franz Mathauser
 *
 */
@XmlRootElement( name = "Order" )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public class Order
{

	private Integer id;

	private List<OrderLine> orderLines;

	private Customer customer;

	private Driver driver;

	private List<OrderStage> stages;

	/**
	 * Liefert das Attribut id.
	 * 
	 * @return id
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * Setzt das Attribut id.
	 * 
	 * @param id
	 *            zu setzender Wert für das Attribut id
	 */
	public void setId( Integer id )
	{
		this.id = id;
	}

	/**
	 * Liefert das Attribut customer.
	 * 
	 * @return customer
	 */
	public Customer getCustomer()
	{
		return customer;
	}

	/**
	 * Setzt das Attribut customer.
	 * 
	 * @param customer
	 *            zu setzender Wert für das Attribut customer
	 */
	public void setCustomer( Customer customer )
	{
		this.customer = customer;
	}

	/**
	 * Liefert das Attribut driver.
	 * 
	 * @return driver
	 */
	public Driver getDriver()
	{
		return driver;
	}

	/**
	 * Setzt das Attribut driver.
	 * 
	 * @param driver
	 *            zu setzender Wert für das Attribut driver
	 */
	public void setDriver( Driver driver )
	{
		this.driver = driver;
	}

	/**
	 * Liefert das Attribut stages.
	 * 
	 * @return stages
	 */
	public List<OrderStage> getStages()
	{
		return stages;
	}

	/**
	 * Setzt das Attribut stages.
	 * 
	 * @param stages
	 *            zu setzender Wert für das Attribut stages
	 */
	public void setStages( List<OrderStage> stages )
	{
		this.stages = stages;
	}

	/**
	 * Liefert das Attribut orderLines.
	 * 
	 * @return orderLines
	 */
	public List<OrderLine> getOrderLines()
	{
		return orderLines;
	}

	/**
	 * Setzt das Attribut orderLines.
	 * 
	 * @param orderLines
	 *            zu setzender Wert für das Attribut orderLines
	 */
	public void setOrderLines( List<OrderLine> orderLines )
	{
		this.orderLines = orderLines;
	}

}
