package edu.hm.lip.pizza.api.object.resources;

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
 * Resource für die Produkte. Es werden alle benötigten Produkt-Information in dieser Klasse gehalten, wie
 * beispielsweise der Name, eine Beschreibung und das zugehörige Produktbild.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@XmlRootElement( name = "Product" )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public class Product extends AbstractRessourceObject
{

	private static final long serialVersionUID = 2050790583309430322L;

	private Integer id;

	private String name;

	@XmlElement( name = "price_l" )
	@JsonProperty( "price_l" )
	private String priceL;

	@XmlElement( name = "price_xl" )
	@JsonProperty( "price_xl" )
	private String priceXL;

	@XmlElement( name = "price_xxl" )
	@JsonProperty( "price_xxl" )
	private String priceXXL;

	private String description;

	@XmlElement( name = "image_url" )
	@JsonProperty( "image_url" )
	private String imageUrl;

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
	 * Liefert das Attribut name.
	 * 
	 * @return name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Setzt das Attribut name.
	 * 
	 * @param name
	 *            zu setzender Wert für das Attribut name
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * Liefert das Attribut priceL.
	 * 
	 * @return priceL
	 */
	public String getPriceL()
	{
		return priceL;
	}

	/**
	 * Setzt das Attribut priceL.
	 * 
	 * @param priceL
	 *            zu setzender Wert für das Attribut priceL
	 */
	public void setPriceL( String priceL )
	{
		this.priceL = priceL;
	}

	/**
	 * Liefert das Attribut priceXL.
	 * 
	 * @return priceXL
	 */
	public String getPriceXL()
	{
		return priceXL;
	}

	/**
	 * Setzt das Attribut priceXL.
	 * 
	 * @param priceXL
	 *            zu setzender Wert für das Attribut priceXL
	 */
	public void setPriceXL( String priceXL )
	{
		this.priceXL = priceXL;
	}

	/**
	 * Liefert das Attribut priceXXL.
	 * 
	 * @return priceXXL
	 */
	public String getPriceXXL()
	{
		return priceXXL;
	}

	/**
	 * Setzt das Attribut priceXXL.
	 * 
	 * @param priceXXL
	 *            zu setzender Wert für das Attribut priceXXL
	 */
	public void setPriceXXL( String priceXXL )
	{
		this.priceXXL = priceXXL;
	}

	/**
	 * Liefert das Attribut description.
	 * 
	 * @return description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Setzt das Attribut description.
	 * 
	 * @param description
	 *            zu setzender Wert für das Attribut description
	 */
	public void setDescription( String description )
	{
		this.description = description;
	}

	/**
	 * Liefert das Attribut imageUrl.
	 * 
	 * @return imageUrl
	 */
	public String getImageUrl()
	{
		return imageUrl;
	}

	/**
	 * Setzt das Attribut imageUrl.
	 * 
	 * @param imageUrl
	 *            zu setzender Wert für das Attribut imageUrl
	 */
	public void setImageUrl( String imageUrl )
	{
		this.imageUrl = imageUrl;
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
