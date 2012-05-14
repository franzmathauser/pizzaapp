package edu.hm.lip.pizza.internal.object.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import edu.hm.lip.pizza.internal.object.AbstractEntityObject;

/**
 * Entität für die Bestellungen. Es werden alle benötigten Bestellungs-Information in dieser Klasse gehalten, wie
 * beispielsweise der zugehörige Kunde, der Fahrer der die Bestellung ausfährt, die Bestelldetails (Einzelprodukte).
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Entity
@Table( name = "order_table" )
public class EntityOrder extends AbstractEntityObject
{

	private static final long serialVersionUID = -7930788547232091933L;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	private String note;

	@OneToMany( mappedBy = "order" )
	@Cascade( value = CascadeType.ALL )
	private List<EntityOrderLine> orderLines;

	@ManyToOne( fetch = FetchType.LAZY )
	@Cascade( value = CascadeType.ALL )
	private EntityCustomer customer;

	@ManyToOne( fetch = FetchType.LAZY )
	private EntityDriver driver;

	@OneToMany( mappedBy = "order" )
	@Cascade( value = CascadeType.ALL )
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
