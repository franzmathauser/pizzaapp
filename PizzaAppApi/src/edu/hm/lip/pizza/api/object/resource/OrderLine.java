package edu.hm.lip.pizza.api.object.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
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
	 *            zu setzender Wert für das Attribut id
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
	 *            zu setzender Wert für das Attribut quantity
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
	 *            zu setzender Wert für das Attribut size
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
	 *            zu setzender Wert für das Attribut productId
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
		return HashCodeBuilder.reflectionHashCode( INITIAL_NON_ZERO_ODD_NUMBER, MULTIPLIER_NON_ZERO_ODD_NUMBER, this, true,
				this.getClass(), null );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, this.getClass(), null );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		ReflectionToStringBuilder rsb = new ReflectionToStringBuilder( this, ToStringStyle.SHORT_PREFIX_STYLE );
		rsb.setAppendStatics( false );
		rsb.setAppendTransients( true );
		rsb.setUpToClass( this.getClass() );
		rsb.setExcludeFieldNames( null );
		return rsb.toString();
	}

}
