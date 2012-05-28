package edu.hm.lip.pizza.api.object.resource;

import java.util.Date;
import java.util.List;

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
import edu.hm.lip.pizza.api.object.enumeration.Stage;

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

	@XmlElement( name = "current_stage" )
	@JsonProperty( "current_stage" )
	private Stage currentStage;

	@XmlElement( name = "order_date" )
	@JsonProperty( "order_date" )
	private Date orderDate;

	private String price;

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
	 * Liefert das Attribut currentStage.
	 * 
	 * @return currentStage
	 */
	public Stage getCurrentStage()
	{
		return currentStage;
	}

	/**
	 * Setzt das Attribut currentStage.
	 * 
	 * @param currentStage
	 *            zu setzender Wert für das Attribut currentStage
	 */
	public void setCurrentStage( Stage currentStage )
	{
		this.currentStage = currentStage;
	}

	/**
	 * Liefert das Attribut orderDate.
	 * 
	 * @return orderDate
	 */
	public Date getOrderDate()
	{
		return orderDate;
	}

	/**
	 * Setzt das Attribut orderDate.
	 * 
	 * @param orderDate
	 *            zu setzender Wert für das Attribut orderDate
	 */
	public void setOrderDate( Date orderDate )
	{
		this.orderDate = orderDate;
	}

	/**
	 * Liefert das Attribut price.
	 * 
	 * @return price
	 */
	public String getPrice()
	{
		return price;
	}

	/**
	 * Setzt das Attribut price.
	 * 
	 * @param price
	 *            zu setzender Wert für das Attribut price
	 */
	public void setPrice( String price )
	{
		this.price = price;
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
