package edu.hm.lip.pizza.api.object.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import edu.hm.lip.pizza.api.object.AbstractRessourceObject;

/**
 * Resource für die Bestellungen. Es werden alle benötigten Bestellungs-Information in dieser Klasse gehalten, wie
 * beispielsweise der zugehörige Kunde, der Fahrer der die Bestellung ausfährt, die Bestelldetails (Einzelprodukte).
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@XmlRootElement( name = "Order" )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public class Order extends AbstractRessourceObject
{

	private static final long serialVersionUID = 4218276686425658940L;

	private Integer id;

	private String note;

	@XmlElement( name = "order_lines" )
	@JsonProperty( "order_lines" )
	private List<OrderLine> orderLines;

	private Customer customer;

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
	 * Liefert das Attribut note.
	 * 
	 * @return note
	 */
	public String getNote()
	{
		return note;
	}

	/**
	 * Setzt das Attribut note.
	 * 
	 * @param note
	 *            zu setzender Wert für das Attribut note
	 */
	public void setNote( String note )
	{
		this.note = note;
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
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Order [id=" + id + ", note=" + note + ", orderLines=" + orderLines + ", customer=" + customer + "]";
	}

}
