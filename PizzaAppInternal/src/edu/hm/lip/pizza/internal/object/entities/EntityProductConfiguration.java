package edu.hm.lip.pizza.internal.object.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.hm.lip.pizza.api.object.enums.Size;

@Entity
@Table( name = "product_configuration" )
public class EntityProductConfiguration
{

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	@Enumerated( value = EnumType.ORDINAL )
	private Size size;

	private Double price;

	@ManyToOne( fetch = FetchType.LAZY )
	private EntityProduct product;
	
	@OneToMany( mappedBy = "productConfiguration" )
	private List<EntityOrderLine> orderLines;


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
	 * Liefert das Attribut size.
	 * 
	 * @return size
	 */
	public Size getSize()
	{
		return size;
	}

	/**
	 * Setzt das Attribut size.
	 * 
	 * @param size
	 *            zu setzender Wert für das Attribut size
	 */
	public void setSize( Size size )
	{
		this.size = size;
	}

	/**
	 * Liefert das Attribut price.
	 * 
	 * @return price
	 */
	public Double getPrice()
	{
		return price;
	}

	/**
	 * Setzt das Attribut price.
	 * 
	 * @param price
	 *            zu setzender Wert für das Attribut price
	 */
	public void setPrice( Double price )
	{
		this.price = price;
	}

	/**
	 * Liefert das Attribut product.
	 * 
	 * @return product
	 */
	public EntityProduct getProduct()
	{
		return product;
	}

	/**
	 * Setzt das Attribut product.
	 * 
	 * @param entityProduct
	 *            zu setzender Wert für das Attribut product
	 */
	public void setProduct( EntityProduct entityProduct )
	{
		this.product = entityProduct;
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
	 * 			zu setzender Wert für das Attribut orderLines
	 */
	public void setOrderLines( List<EntityOrderLine> entityOrderLines )
	{
		this.orderLines = entityOrderLines;
	}



}
