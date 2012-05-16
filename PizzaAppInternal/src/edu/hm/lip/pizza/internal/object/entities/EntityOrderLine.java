package edu.hm.lip.pizza.internal.object.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import edu.hm.lip.pizza.internal.object.AbstractEntityObject;

/**
 * Entität für eine Bestellungszeile. Es werden alle benötigten Bestellzeilen-Information in dieser Klasse gehalten, wie
 * beispielsweise das bestellte Produkt, die zugehörige Anzahl und die zugehörige Gesamtbestellung.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Entity
@Table( name = "order_line" )
public class EntityOrderLine extends AbstractEntityObject
{

	private static final long serialVersionUID = -5832679243021389694L;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	private Integer quantity;

	@ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	private EntityOrder order;

	@ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	private EntityProductConfiguration productConfiguration;

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
	 * Liefert das Attribut order.
	 * 
	 * @return order
	 */
	public EntityOrder getOrder()
	{
		return order;
	}

	/**
	 * Setzt das Attribut order.
	 * 
	 * @param order
	 *            zu setzender Wert für das Attribut order
	 */
	public void setOrder( EntityOrder order )
	{
		this.order = order;
	}

	/**
	 * Liefert das Attribut productConfiguration.
	 * 
	 * @return productConfiguration
	 */
	public EntityProductConfiguration getProductConfiguration()
	{
		return productConfiguration;
	}

	/**
	 * Setzt das Attribut productConfiguration.
	 * 
	 * @param productConfiguration
	 *            zu setzender Wert für das Attribut productConfiguration
	 */
	public void setProductConfiguration( EntityProductConfiguration productConfiguration )
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
		return HashCodeBuilder.reflectionHashCode( INITIAL_NON_ZERO_ODD_NUMBER, MULTIPLIER_NON_ZERO_ODD_NUMBER, this, true,
				this.getClass(), new String[] { "order" } );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, this.getClass(), new String[] { "order" } );
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
		rsb.setExcludeFieldNames( new String[] { "order" } );
		return rsb.toString();
	}

}
