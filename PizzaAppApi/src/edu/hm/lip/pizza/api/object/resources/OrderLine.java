package edu.hm.lip.pizza.api.object.resources;

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

	private Integer quantity;

	private String size;
	
	@XmlElement( name = "product_id" )
	@JsonProperty( "product_id" )
	private Integer productId;

	

	
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
	 * 			zu setzender Wert für das Attribut id
	 */
	public void setId( Integer id )
	{
		this.id = id;
	}

	
	/**
	 * Liefert das Attribut quantity.
	 *
	 * @return quantity
	 */
	public Integer getQuantity()
	{
		return quantity;
	}

	
	/**
	 * Setzt das Attribut quantity.
	 *
	 * @param quantity 
	 * 			zu setzender Wert für das Attribut quantity
	 */
	public void setQuantity( Integer quantity )
	{
		this.quantity = quantity;
	}

	
	/**
	 * Liefert das Attribut size.
	 *
	 * @return size
	 */
	public String getSize()
	{
		return size;
	}

	
	/**
	 * Setzt das Attribut size.
	 *
	 * @param size 
	 * 			zu setzender Wert für das Attribut size
	 */
	public void setSize( String size )
	{
		this.size = size;
	}

	
	/**
	 * Liefert das Attribut productId.
	 *
	 * @return productId
	 */
	public Integer getProductId()
	{
		return productId;
	}

	
	/**
	 * Setzt das Attribut productId.
	 *
	 * @param productId 
	 * 			zu setzender Wert für das Attribut productId
	 */
	public void setProductId( Integer productId )
	{
		this.productId = productId;
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
