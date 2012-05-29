package edu.hm.lip.pizza.internal.object.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import edu.hm.lip.pizza.internal.object.AbstractEntityObject;
import edu.hm.lip.pizza.internal.object.query.OrderQueryConstants;

/**
 * Entität für die Bestellungen. Es werden alle benötigten Bestellungs-Information in dieser Klasse gehalten, wie
 * beispielsweise der zugehörige Kunde, der Fahrer der die Bestellung ausfährt, die Bestelldetails (Einzelprodukte).
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Entity
@Table( name = "order_table" )
@NamedQueries( {
		@NamedQuery( name = OrderQueryConstants.GET_ALL_ORDERS, query = OrderQueryConstants.GET_ALL_ORDERS_QUERY ),
		@NamedQuery( name = OrderQueryConstants.ORDERS_BY_STAGE, query = OrderQueryConstants.ORDERS_BY_STAGE_QUERY ),
		@NamedQuery( name = OrderQueryConstants.ORDERS_BY_STAGE_2, query = OrderQueryConstants.ORDERS_BY_STAGE_QUERY_2 ),
		@NamedQuery(	name = OrderQueryConstants.ORDERS_BY_DRIVER_AND_STAGE,
						query = OrderQueryConstants.ORDERS_BY_DRIVER_AND_STAGE_QUERY ) } )
public class EntityOrder extends AbstractEntityObject
{

	private static final long serialVersionUID = -7930788547232091933L;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	private String note;

	@OneToMany( mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	private List<EntityOrderLine> orderLines;

	@ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	private EntityCustomer customer;

	@ManyToOne( fetch = FetchType.LAZY )
	private EntityDriver driver;

	@OneToMany( mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true )
	private List<EntityOrderStage> stages;

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
	public List<EntityOrderLine> getOrderLines()
	{
		return orderLines;
	}

	/**
	 * Setzt das Attribut orderLines.
	 * 
	 * @param orderLines
	 *            zu setzender Wert für das Attribut orderLines
	 */
	public void setOrderLines( List<EntityOrderLine> orderLines )
	{
		this.orderLines = orderLines;
	}

	/**
	 * Liefert das Attribut customer.
	 * 
	 * @return customer
	 */
	public EntityCustomer getCustomer()
	{
		return customer;
	}

	/**
	 * Setzt das Attribut customer.
	 * 
	 * @param customer
	 *            zu setzender Wert für das Attribut customer
	 */
	public void setCustomer( EntityCustomer customer )
	{
		this.customer = customer;
	}

	/**
	 * Liefert das Attribut driver.
	 * 
	 * @return driver
	 */
	public EntityDriver getDriver()
	{
		return driver;
	}

	/**
	 * Setzt das Attribut driver.
	 * 
	 * @param driver
	 *            zu setzender Wert für das Attribut driver
	 */
	public void setDriver( EntityDriver driver )
	{
		this.driver = driver;
	}

	/**
	 * Liefert das Attribut stages.
	 * 
	 * @return stages
	 */
	public List<EntityOrderStage> getStages()
	{
		return stages;
	}

	/**
	 * Setzt das Attribut stages.
	 * 
	 * @param stages
	 *            zu setzender Wert für das Attribut stages
	 */
	public void setStages( List<EntityOrderStage> stages )
	{
		this.stages = stages;
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
