package edu.hm.lip.pizza.api.object.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import edu.hm.lip.pizza.api.object.AbstractRessourceObject;

/**
 * Resource für eine Bestellungszeile. Es werden alle benötigten Bestellzeilen-Information in dieser Klasse gehalten,
 * wie beispielsweise das bestellte Produkt, die zugehörige Anzahl und die zugehörige Gesamtbestellung.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@XmlRootElement( name = "OrderLine" )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public class OrderLine extends AbstractRessourceObject
{

	private static final long serialVersionUID = -4220314995390716779L;

	private Integer id;

	private Integer count;

	private Order order;

	private ProductConfiguration productConfiguration;

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
	 * Liefert das Attribut count.
	 * 
	 * @return count
	 */
	public Integer getCount()
	{
		return count;
	}

	/**
	 * Setzt das Attribut count.
	 * 
	 * @param count
	 *            zu setzender Wert für das Attribut count
	 */
	public void setCount( Integer count )
	{
		this.count = count;
	}

	/**
	 * Liefert das Attribut order.
	 * 
	 * @return order
	 */
	public Order getOrder()
	{
		return order;
	}

	/**
	 * Setzt das Attribut order.
	 * 
	 * @param order
	 *            zu setzender Wert für das Attribut order
	 */
	public void setOrder( Order order )
	{
		this.order = order;
	}

	/**
	 * Liefert das Attribut productConfiguration.
	 * 
	 * @return productConfiguration
	 */
	public ProductConfiguration getProductConfiguration()
	{
		return productConfiguration;
	}

	/**
	 * Setzt das Attribut productConfiguration.
	 * 
	 * @param productConfiguration
	 *            zu setzender Wert für das Attribut productConfiguration
	 */
	public void setProductConfiguration( ProductConfiguration productConfiguration )
	{
		this.productConfiguration = productConfiguration;
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
	 * @see edu.hm.basic.object.AbstractBasicObject#toString()
	 */
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
