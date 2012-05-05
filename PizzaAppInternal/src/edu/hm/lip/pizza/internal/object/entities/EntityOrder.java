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

@Entity
@Table( name = "order_table" )
public class EntityOrder
{

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	@OneToMany( mappedBy = "order" )
	private List<EntityOrderLine> orderLines;

	@ManyToOne( fetch = FetchType.LAZY )
	private EntityCustomer customer;

	@ManyToOne( fetch = FetchType.LAZY )
	private EntityDriver driver;

	@OneToMany( mappedBy = "order" )
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
	 * @param entityCustomer
	 *            zu setzender Wert für das Attribut customer
	 */
	public void setCustomer( EntityCustomer entityCustomer )
	{
		this.customer = entityCustomer;
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
	 * @param entityDriver
	 *            zu setzender Wert für das Attribut driver
	 */
	public void setDriver( EntityDriver entityDriver )
	{
		this.driver = entityDriver;
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
	 * @param entityOrderLines
	 *            zu setzender Wert für das Attribut orderLines
	 */
	public void setOrderLines( List<EntityOrderLine> entityOrderLines )
	{
		this.orderLines = entityOrderLines;
	}

}
