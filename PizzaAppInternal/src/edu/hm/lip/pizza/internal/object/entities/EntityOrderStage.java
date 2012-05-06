package edu.hm.lip.pizza.internal.object.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.hm.lip.pizza.api.object.enums.Stage;
import edu.hm.lip.pizza.internal.object.AbstractEntityObject;

/**
 * Entität für den Bestellstatus. Es werden alle benötigten Status-Information in dieser Klasse gehalten, wie
 * beispielsweise der aktuelle Status und der zugehörige Zeitstempel.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Entity
@Table( name = "order_stage" )
public class EntityOrderStage extends AbstractEntityObject
{

	private static final long serialVersionUID = -4190184211827370924L;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	@Enumerated( EnumType.ORDINAL )
	private Stage stage;

	@Temporal( TemporalType.TIMESTAMP )
	private Date createDate;

	@ManyToOne( fetch = FetchType.LAZY )
	private EntityOrder order;

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
	 * Liefert das Attribut stage.
	 * 
	 * @return stage
	 */
	public Stage getStage()
	{
		return stage;
	}

	/**
	 * Setzt das Attribut stage.
	 * 
	 * @param stage
	 *            zu setzender Wert für das Attribut stage
	 */
	public void setStage( Stage stage )
	{
		this.stage = stage;
	}

	/**
	 * Liefert das Attribut createDate.
	 * 
	 * @return createDate
	 */
	public Date getCreateDate()
	{
		return createDate;
	}

	/**
	 * Setzt das Attribut createDate.
	 * 
	 * @param createDate
	 *            zu setzender Wert für das Attribut createDate
	 */
	public void setCreateDate( Date createDate )
	{
		this.createDate = createDate;
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
